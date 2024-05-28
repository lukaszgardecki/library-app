package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.LoginRequest;
import com.example.libraryapp.domain.auth.LoginResponse;
import com.example.libraryapp.domain.auth.RegisterRequest;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.domain.member.dto.MemberUpdateDto;
import com.example.libraryapp.domain.payment.dto.PaymentRequest;
import com.example.libraryapp.domain.rack.RackToSaveDto;
import com.example.libraryapp.domain.rack.RackToUpdateDto;
import com.example.libraryapp.management.ActionRequest;
import com.example.libraryapp.management.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;

public class AuthenticationControllerTest extends BaseTest {


    @Nested
    @DisplayName("Tests for /authenticate endpoint")
    class AuthenticateTests {
        @Test
        @DisplayName("Should authenticate a user if credentials are correct.")
        void shouldAuthenticateAUserIfCredentialsAreCorrect() {
            LoginRequest userCredentials = getCorrectCredentials();
            LoginResponse responseBody = client.testRequest(POST, "/authenticate", userCredentials, OK)
                    .expectAll(resp -> {
                        resp.expectCookie().httpOnly("__Secure-Fgp", true);
                        resp.expectCookie().secure("__Secure-Fgp", true);
                    })
                    .expectBody(LoginResponse.class)
                    .returnResult().getResponseBody();

            validateReturnedTokens(responseBody);
        }

        @Test
        @DisplayName("Should not authenticate a user if password isn't correct.")
        void shouldNotAuthenticateAUserIfPasswordIsNotCorrect() {
            LoginRequest userCredentials = getCredentialsWithBadPassword();
            ErrorMessage respBody1 = client.testRequest(POST, "/authenticate", userCredentials, UNAUTHORIZED)
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(respBody1.getMessage()).isEqualTo(Message.BAD_CREDENTIALS);
        }

        @Test
        @DisplayName("Should not authenticate a user if email isn't correct.")
        void shouldNotAuthenticateAUserIfEmailIsNotCorrect() {
            LoginRequest userCredentials = getCredentialsWithBadEmail();
            ErrorMessage respBody2 = client.testRequest(POST, "/authenticate", userCredentials, UNAUTHORIZED)
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(respBody2.getMessage()).isEqualTo(Message.BAD_CREDENTIALS);
        }

        @Test
        @DisplayName("Should not authenticate a user if a request body is missing.")
        void shouldNotAuthenticateAUserIfRequestBodyIsEmpty() {
            ErrorMessage responseBody = client.testRequest(POST, "/authenticate", BAD_REQUEST)
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.BODY_MISSING);
        }
    }

    @Nested
    @DisplayName("Tests for /register endpoint")
    class RegisterTests {
        @Test
        @DisplayName("Should create a new user if email is unique.")
        void shouldCreateANewUserIfEmailIsUnique() {
            RegisterRequest userToSave = getUserRegistrationDtoWithUniqueEmail();
            client.testRequest(POST, "/register", userToSave, OK)
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody().isEmpty();
        }

        @Test
        @DisplayName("Should not create a new user if email does already exist.")
        void shouldNotCreateANewUserIfEmailAlreadyExists() {
            RegisterRequest userToSave = getUserRegistrationDtoWithAlreadyExistedEmail();
            ErrorMessage responseBody = client.testRequest(POST, "/register", userToSave, UNAUTHORIZED)
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.BAD_EMAIL);
        }

        @Test
        @DisplayName("Should not create a new user if a request body is missing.")
        void shouldNotCreateAUserIfRequestBodyIsEmpty() {
            ErrorMessage responseBody = client.testRequest(POST, "/register", BAD_REQUEST)
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.BODY_MISSING);
        }
    }

    @Nested
    @DisplayName("Tests for /refresh-token endpoint")
    class RefreshTokenTests {
        @Test
        @DisplayName("Should refresh a token.")
        void shouldRefreshToken() {
            LoginResponse responseBody = testClient.post()
                    .uri("/api/v1/refresh-token")
                    .header(HttpHeaders.AUTHORIZATION, user.getRefreshToken())
                    .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
                    .exchange()
                    .expectStatus().isOk()
                    .expectAll(resp -> {
                        resp.expectCookie().httpOnly("__Secure-Fgp", true);
                        resp.expectCookie().secure("__Secure-Fgp", true);
                    })
                    .expectBody(LoginResponse.class)
                    .returnResult()
                    .getResponseBody();

            validateReturnedTokens(responseBody);
        }

        @Test
        @DisplayName("Should not refresh a token if sent an access token.")
        void shouldNotRefreshTokenIfSentAnAccessToken() {
            ErrorMessage responseBody = testClient.post()
                    .uri("/api/v1/refresh-token")
                    .header(HttpHeaders.AUTHORIZATION, user.getAccessToken())
                    .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }
        @Test
        @DisplayName("Should not refresh a token if token expired.")
        void shouldNotRefreshTokenIfTokenExpired() {
            String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTcxNjQxMzcwMCwiZXhwIjoxNzE2NDEzNzY1fQ.c49f5LVDNqXsZlK1BSHZCyavTbr20J0aaQGnY6NgHEM" ;
            testClient.post()
                    .uri("/api/v1/refresh-token")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class);
        }

        @Test
        @DisplayName("Should not refresh a token if sent token is bad.")
        void shouldNotRefreshTokenIfTokenIsBad() {
            String token = "Bearer TEST_TOKEN" ;
            testClient.post()
                    .uri("/api/v1/refresh-token")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class);
        }

        @Test
        @DisplayName("Should not refresh a token if did not send a refresh token.")
        void shouldNotRefreshTokenIfTokenIsMissing() {
            ErrorMessage responseBody = testClient.post()
                    .uri("/api/v1/refresh-token")
                    .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }

        @Test
        @DisplayName("Should not refresh a token if cookie is missing.")
        void shouldNotRefreshTokenIfCookieIsMissing() {
            ErrorMessage responseBody = testClient.post()
                    .uri("/api/v1/refresh-token")
                    .header(HttpHeaders.AUTHORIZATION, user.getRefreshToken())
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }

        @Test
        @DisplayName("Should not refresh a token if cookie name is bad.")
        void shouldNotRefreshTokenIfCookieNameIsBad() {
            ErrorMessage responseBody = testClient.post()
                    .uri("/api/v1/refresh-token")
                    .header(HttpHeaders.AUTHORIZATION, user.getRefreshToken())
                    .cookie("BAD_COOKIE_NAME", user.getFgpCookie().getValue())
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }

        @Test
        @DisplayName("Should not refresh a token if cookie value is bad.")
        void shouldNotRefreshTokenIfCookieValueIsBad() {
            ErrorMessage respBody = testClient.post()
                    .uri("/api/v1/refresh-token")
                    .header(HttpHeaders.AUTHORIZATION, user.getRefreshToken())
                    .cookie(user.getFgpCookie().getName(), "BAD_COOKIE_VALUE")
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"))
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(respBody.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }
    }

    @Test
    @DisplayName("Should logout")
    void shouldLogout() {
        testClient.post()
                .uri("/api/v1/authenticate/logout")
                .header(HttpHeaders.AUTHORIZATION, user.getRefreshToken())
                .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
                .exchange()
                .expectStatus().isOk()
                .expectAll(resp -> resp.expectCookie().doesNotExist("__Secure-Fgp"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNull();
    }

    @Nested
    @DisplayName("Tests for request token and fingerprint")
    class RequestTests {
        @ParameterizedTest
        @CsvSource({
                // Member Controller
                "/members, GET",
                "/members/2, GET",
                "/members/2, PATCH",
                "/members/2, DELETE",

                // Book Controller
                "/books, POST",
                "/books/2, PUT",
                "/books/2, PATCH",
                "/books/2, DELETE",

                // Book-items Controller
//                "/book-items, POST",
//                "/book-items/2, PUT",
//                "/book-items/2, PATCH",
//                "/book-items/2, DELETE",

                // Lending Controller
                "/lendings, GET",
                "/lendings/2, GET",
                "/lendings, POST",
                "/lendings/renew?bookBarcode=TEST, POST",
                "/lendings/return?bookBarcode=TEST, PATCH",
                "/lendings/2/lost, POST",

                // Reservation Controller
                "/reservations, GET",
                "/reservations/2, GET",
                "/reservations, POST",
                "/reservations, DELETE",

                // Rack Controller
                "/racks, GET",
                "/racks/2, GET",
                "/racks/2/book-items, GET",
                "/racks/2, PUT",
                "/racks/2, PATCH",
                "/racks/search?q=TEST, GET",
                "/racks, POST",
                "/racks/2, DELETE",

                // Card Controller
                "/cards, GET",
                "/cards/2, GET",
                "/cards/2/activate, POST",
                "/cards/2/deactivate, POST",

                // Payment Controller
                "/payments, GET",
                "/payments/2, GET",
                "/payments/pay-fine, POST",
        })
        @DisplayName("Should not return data if there is no access token in the request.")
        void shouldNotReturnDataIfThereIsNoAccessTokenInRequest(String path, String method) {
            ErrorMessage responseBody1 = testClient.method(HttpMethod.valueOf(method))
                    .uri(API_PREFIX + path)
                    .cookie(admin.getFgpCookie().getName(), admin.getFgpCookie().getValue())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody1.getMessage()).isEqualTo(Message.ACCESS_DENIED);

            ErrorMessage responseBody2 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, admin.getRefreshToken())
                    .cookie(admin.getFgpCookie().getName(), admin.getFgpCookie().getValue())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody2.getMessage()).isEqualTo(Message.ACCESS_DENIED);

            ErrorMessage responseBody3 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, admin.getRefreshToken())
                    .cookie(admin.getFgpCookie().getName(), admin.getFgpCookie().getValue())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody3.getMessage()).isEqualTo(Message.ACCESS_DENIED);

            ErrorMessage responseBody4 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, admin.getAccessToken())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody4.getMessage()).isEqualTo(Message.ACCESS_DENIED);

            ErrorMessage responseBody5 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, admin.getAccessToken())
                    .cookie("TESTbadNAME", admin.getFgpCookie().getValue())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody5.getMessage()).isEqualTo(Message.ACCESS_DENIED);

            ErrorMessage responseBody6 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, admin.getAccessToken())
                    .cookie(admin.getFgpCookie().getName(), "TESTbadVALUE")
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody6.getMessage()).isEqualTo(Message.ACCESS_DENIED);

            ErrorMessage responseBody7 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer TESTbadTOKEN")
                    .cookie(admin.getFgpCookie().getName(), admin.getFgpCookie().getValue())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody7.getMessage()).isEqualTo(Message.ACCESS_DENIED);
        }
    }

    private void validateReturnedTokens(LoginResponse response) {
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isNotNull();
        assertThat(response.getAccessToken()).isNotEmpty();
        assertThat(response.getAccessToken()).isNotBlank();
        assertThat(response.getAccessToken().split("\\.")[0]).isBase64();
        assertThat(response.getAccessToken().split("\\.")[1]).isBase64();

        assertThat(response.getRefreshToken()).isNotNull();
        assertThat(response.getRefreshToken()).isNotEmpty();
        assertThat(response.getRefreshToken()).isNotBlank();
        assertThat(response.getRefreshToken().split("\\.")[0]).isBase64();
        assertThat(response.getRefreshToken().split("\\.")[1]).isBase64();
    }

    private RegisterRequest getUserRegistrationDtoWithUniqueEmail() {
        RegisterRequest user = getUserRegistrationDto();
        user.setEmail("xxxxxx@xxx.com");
        return user;
    }

    private RegisterRequest getUserRegistrationDtoWithAlreadyExistedEmail() {
        RegisterRequest user = getUserRegistrationDto();
        user.setEmail("user@example.com");
        return user;
    }

    private LoginRequest getCorrectCredentials() {
        LoginRequest dto = new LoginRequest();
        dto.setUsername("user@example.com");
        dto.setPassword("userpass");
        return dto;
    }

    private LoginRequest getCredentialsWithBadPassword() {
        LoginRequest dto = new LoginRequest();
        dto.setUsername("user@example.com");
        dto.setPassword("INCORRECTuserpass");
        return dto;
    }

    private LoginRequest getCredentialsWithBadEmail() {
        LoginRequest dto = new LoginRequest();
        dto.setUsername("userINCORRECT@example.com");
        dto.setPassword("userpass");
        return dto;
    }

    private RegisterRequest getUserRegistrationDto() {
        RegisterRequest dto = new RegisterRequest();
        dto.setPassword("password");
        dto.setFirstName("Adam");
        dto.setLastName("Lubnie");
        dto.setStreetAddress("ul. Konopacka 1a/23");
        dto.setZipCode("00-000");
        dto.setCity("Warszawa");
        dto.setState("Mazowieckie");
        dto.setCountry("Polska");
        dto.setPhone("000000000");
        return dto;
    }

    private Object getBodyPlaceholder(String path, String method) {
        if (path.contains("/members")) return new MemberUpdateDto();
        if (path.contains("/books") && method.equals("POST")) return new BookToSaveDto();
        if (path.contains("/books")) return new BookDto();
        if (path.contains("/lendings") && method.equals("POST")) return new ActionRequest();
        if (path.contains("/reservations")) return new ActionRequest();
        if (path.contains("/racks") && method.equals("POST")) return new RackToSaveDto();
        if (path.contains("/racks")) return new RackToUpdateDto();
        else return new PaymentRequest();
    }
}