package com.example.libraryapp.OLDweb;

import com.example.libraryapp.domain.user.dto.RegisterUserDto;
import com.example.libraryapp.domain.auth.dto.LoginRequest;
import com.example.libraryapp.domain.auth.dto.LoginResponse;
import com.example.libraryapp.domain.person.model.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationControllerTest extends BaseTest {

//
//    @Nested
//    @DisplayName("Tests for /authenticate endpoint")
//    class AuthenticateTests {
//        @Test
//        @DisplayName("Should authenticate a user if credentials are correct.")
//        void shouldAuthenticateAUserIfCredentialsAreCorrect() {
//            LoginRequest userCredentials = getCorrectCredentials();
//            LoginResponse responseBody = client.testRequest(POST, "/authenticate", userCredentials, OK)
//                    .expectAll(resp -> {
//                        resp.expectCookie().httpOnly(TokenUtils.FINGERPRINT_COOKIE_NAME, true);
//                        resp.expectCookie().secure(TokenUtils.FINGERPRINT_COOKIE_NAME, TokenUtils.isFgpCookieSecured());
//                    })
//                    .expectBody(LoginResponse.class)
//                    .returnResult().getResponseBody();
//
//            validateReturnedTokens(responseBody);
//        }
//
//        @Test
//        @DisplayName("Should not authenticate a user if password isn't correct.")
//        void shouldNotAuthenticateAUserIfPasswordIsNotCorrect() {
//            LoginRequest userCredentials = getCredentialsWithBadPassword();
//            ErrorMessage respBody1 = client.testRequest(POST, "/authenticate", userCredentials, UNAUTHORIZED)
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(respBody1.getMessage()).isEqualTo(Message.VALIDATION_BAD_CREDENTIALS.getMessage());
//        }
//
//        @Test
//        @DisplayName("Should not authenticate a user if email isn't correct.")
//        void shouldNotAuthenticateAUserIfEmailIsNotCorrect() {
//            LoginRequest userCredentials = getCredentialsWithBadEmail();
//            ErrorMessage respBody2 = client.testRequest(POST, "/authenticate", userCredentials, UNAUTHORIZED)
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(respBody2.getMessage()).isEqualTo(Message.VALIDATION_BAD_CREDENTIALS.getMessage());
//        }
//
//        @Test
//        @DisplayName("Should not authenticate a user if their account is SUSPENDED.")
//        void shouldNotAuthenticateAUserIfTheirAccountIsSuspended() {
//            LoginRequest userCredentials = new LoginRequest();
//            userCredentials.setUsername("a.kleks@gmail.com");
//            userCredentials.setPassword("userpass2");
//            ErrorMessage respBody2 = client.testRequest(POST, "/authenticate", userCredentials, FORBIDDEN)
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(respBody2.getMessage()).isEqualTo("User account is locked");
//        }
//
//        @Test
//        @DisplayName("Should not authenticate a user if their account is CLOSED.")
//        void shouldNotAuthenticateAUserIfTheirAccountIsClosed() {
//            LoginRequest userCredentials = new LoginRequest();
//            userCredentials.setUsername("a.mickiewicz@gmail.com");
//            userCredentials.setPassword("userpass1");
//            ErrorMessage respBody2 = client.testRequest(POST, "/authenticate", userCredentials, FORBIDDEN)
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(respBody2.getMessage()).isEqualTo("User is disabled");
//        }
//
//        @Test
//        @DisplayName("Should not authenticate a user if their account is INACTIVE.")
//        void shouldNotAuthenticateAUserIfTheirAccountIsInactive() {
//            LoginRequest userCredentials = new LoginRequest();
//            userCredentials.setUsername("o.mateusz@gmail.com");
//            userCredentials.setPassword("userpass6");
//            ErrorMessage respBody2 = client.testRequest(POST, "/authenticate", userCredentials, FORBIDDEN)
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(respBody2.getMessage()).isEqualTo("User is disabled");
//        }
//
//        @Test
//        @DisplayName("Should not authenticate a user if a request body is missing.")
//        void shouldNotAuthenticateAUserIfRequestBodyIsEmpty() {
//            ErrorMessage responseBody = client.testRequest(POST, "/authenticate", BAD_REQUEST)
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(responseBody.getMessage()).isEqualTo(Message.BODY_MISSING.getMessage());
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests for /register endpoint")
//    class RegisterTests {
//        @Test
//        @DisplayName("Should create a new user if email is unique.")
//        void shouldCreateANewUserIfEmailIsUnique() {
//            RegisterRequest userToSave = getUserRegistrationDtoWithUniqueEmail();
//            client.testRequest(POST, "/register", userToSave, OK)
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody().isEmpty();
//        }
//
//        @Test
//        @DisplayName("Should not create a new user if email does already exist.")
//        void shouldNotCreateANewUserIfEmailAlreadyExists() {
//            RegisterRequest userToSave = getUserRegistrationDtoWithAlreadyExistedEmail();
//            ErrorMessage responseBody = client.testRequest(POST, "/register", userToSave, UNAUTHORIZED)
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(responseBody.getMessage()).isEqualTo(Message.VALIDATION_EMAIL_UNIQUE.getMessage());
//        }
//
//        @Test
//        @DisplayName("Should not create a new user if a request body is missing.")
//        void shouldNotCreateAUserIfRequestBodyIsEmpty() {
//            ErrorMessage responseBody = client.testRequest(POST, "/register", BAD_REQUEST)
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(responseBody.getMessage()).isEqualTo(Message.BODY_MISSING.getMessage());
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests for /refresh-token endpoint")
//    class RefreshTokenTests {
//        @Test
//        @DisplayName("Should refresh a token.")
//        void shouldRefreshToken() {
//            LoginResponse responseBody = testClient.post()
//                    .uri("/api/v1/refresh-token")
//                    .header(HttpHeaders.AUTHORIZATION, user.getRefreshToken())
//                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
//                    .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
//                    .exchange()
//                    .expectStatus().isOk()
//                    .expectAll(resp -> {
//                        resp.expectCookie().httpOnly(TokenUtils.FINGERPRINT_COOKIE_NAME, true);
//                        resp.expectCookie().secure(TokenUtils.FINGERPRINT_COOKIE_NAME, TokenUtils.isFgpCookieSecured());
//                    })
//                    .expectBody(LoginResponse.class)
//                    .returnResult()
//                    .getResponseBody();
//
//            validateReturnedTokens(responseBody);
//        }
//
//        @Test
//        @DisplayName("Should not refresh a token if sent an access token.")
//        void shouldNotRefreshTokenIfSentAnAccessToken() {
//            ErrorMessage responseBody = testClient.post()
//                    .uri("/api/v1/refresh-token")
//                    .header(HttpHeaders.AUTHORIZATION, user.getAccessToken())
//                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
//                    .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
//                    .exchange()
//                    .expectStatus().isUnauthorized()
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
//        }
//        @Test
//        @DisplayName("Should not refresh a token if token expired.")
//        void shouldNotRefreshTokenIfTokenExpired() {
//            String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTcxNjQxMzcwMCwiZXhwIjoxNzE2NDEzNzY1fQ.c49f5LVDNqXsZlK1BSHZCyavTbr20J0aaQGnY6NgHEM" ;
//            testClient.post()
//                    .uri("/api/v1/refresh-token")
//                    .header(HttpHeaders.AUTHORIZATION, token)
//                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
//                    .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
//                    .exchange()
//                    .expectStatus().isUnauthorized()
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class);
//        }
//
//        @Test
//        @DisplayName("Should not refresh a token if sent token is bad.")
//        void shouldNotRefreshTokenIfTokenIsBad() {
//            String token = "Bearer TEST_TOKEN" ;
//            testClient.post()
//                    .uri("/api/v1/refresh-token")
//                    .header(HttpHeaders.AUTHORIZATION, token)
//                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
//                    .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
//                    .exchange()
//                    .expectStatus().isUnauthorized()
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class);
//        }
//
//        @Test
//        @DisplayName("Should not refresh a token if sent token has no userID claim.")
//        void shouldNotRefreshTokenIfTokenHasNoUserIdClaim() {
//            String tokenWithoutUserIdClaim = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRmluZ2VycHJpbnQiOiI4RkJCQzczNkNCNTZEQzQwMzc4QjQ0MzJENzU2MDRCNTA0QTYwNjkwNDU4RjJGQkUxRTMzOTgyRDY5NDQ2NDVCIiwic3ViIjoiYWRtaW5AZXhhbXBsZS5jb20iLCJuYmYiOjE3MTcyNzA4MzIsImV4cCI6MTcxNzg3NTYzMiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2FwaS92MSIsImlhdCI6MTcxNzI3MDgzMn0.NMAeWRHkvsKaJkEuXfJftjrM92t-rHk1xJaBk6NOqKA" ;
//            String cookieValue = "d#IhdRl5FF8Sel8n/E%zaeR/N#Vg6Pxe8cJQynh0Y44euRP5z$";
//            testClient.post()
//                    .uri("/api/v1/refresh-token")
//                    .header(HttpHeaders.AUTHORIZATION, tokenWithoutUserIdClaim)
//                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
//                    .cookie(user.getFgpCookie().getName(), cookieValue)
//                    .exchange()
//                    .expectStatus().isUnauthorized()
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class);
//        }
//
//        @Test
//        @DisplayName("Should not refresh a token if did not send a refresh token.")
//        void shouldNotRefreshTokenIfTokenIsMissing() {
//            ErrorMessage responseBody = testClient.post()
//                    .uri("/api/v1/refresh-token")
//                    .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
//                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
//                    .exchange()
//                    .expectStatus().isUnauthorized()
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
//        }
//
//        @Test
//        @DisplayName("Should not refresh a token if cookie is missing.")
//        void shouldNotRefreshTokenIfCookieIsMissing() {
//            ErrorMessage responseBody = testClient.post()
//                    .uri("/api/v1/refresh-token")
//                    .header(HttpHeaders.AUTHORIZATION, user.getRefreshToken())
//                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
//                    .exchange()
//                    .expectStatus().isUnauthorized()
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
//        }
//
//        @Test
//        @DisplayName("Should not refresh a token if cookie name is bad.")
//        void shouldNotRefreshTokenIfCookieNameIsBad() {
//            ErrorMessage responseBody = testClient.post()
//                    .uri("/api/v1/refresh-token")
//                    .header(HttpHeaders.AUTHORIZATION, user.getRefreshToken())
//                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
//                    .cookie("BAD_COOKIE_NAME", user.getFgpCookie().getValue())
//                    .exchange()
//                    .expectStatus().isUnauthorized()
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
//        }
//
//        @Test
//        @DisplayName("Should not refresh a token if cookie value is bad.")
//        void shouldNotRefreshTokenIfCookieValueIsBad() {
//            ErrorMessage respBody = testClient.post()
//                    .uri("/api/v1/refresh-token")
//                    .header(HttpHeaders.AUTHORIZATION, user.getRefreshToken())
//                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
//                    .cookie(user.getFgpCookie().getName(), "BAD_COOKIE_VALUE")
//                    .exchange()
//                    .expectStatus().isUnauthorized()
//                    .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME))
//                    .expectBody(ErrorMessage.class)
//                    .returnResult().getResponseBody();
//            assertThat(respBody.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
//        }
//    }
//
//    @Test
//    @DisplayName("Should logout")
//    void shouldLogout() {
//        testClient.post()
//                .uri("/api/v1/authenticate/logout")
//                .header(HttpHeaders.AUTHORIZATION, user.getRefreshToken())
//                .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
//                .cookie(user.getFgpCookie().getName(), user.getFgpCookie().getValue())
//                .exchange()
//                .expectStatus().isOk()
//                .expectAll(resp -> resp.expectCookie().doesNotExist(TokenUtils.FINGERPRINT_COOKIE_NAME));
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        assertThat(authentication).isNull();
//    }

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

                // Notification Controller
                "/notifications, GET",
                "/notifications?memberId=5, GET",
                "/notifications/1, GET",
                "/notifications/1, POST",
                "/notifications/1, DELETE",
                "/notifications, DELETE",

                // Warehouse Controller
                "/warehouse/reservations/pending, GET",
                "/warehouse/reservations/13/ready, POST"
        })
        @DisplayName("Should not return data if there is no access token in the request.")
        void shouldNotReturnDataIfThereIsNoAccessTokenInRequest(String path, String method) {
            ErrorMessage responseBody1 = testClient.method(HttpMethod.valueOf(method))
                    .uri(API_PREFIX + path)
                    .cookie(admin.getFgpCookie().getName(), admin.getFgpCookie().getValue())
                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody1.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            ErrorMessage responseBody2 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, admin.getRefreshToken())
                    .cookie(admin.getFgpCookie().getName(), admin.getFgpCookie().getValue())
                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody2.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            ErrorMessage responseBody3 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, admin.getRefreshToken())
                    .cookie(admin.getFgpCookie().getName(), admin.getFgpCookie().getValue())
                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody3.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            ErrorMessage responseBody4 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, admin.getAccessToken())
                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody4.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            ErrorMessage responseBody5 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, admin.getAccessToken())
                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                    .cookie("TESTbadNAME", admin.getFgpCookie().getValue())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody5.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            ErrorMessage responseBody6 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, admin.getAccessToken())
                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                    .cookie(admin.getFgpCookie().getName(), "TESTbadVALUE")
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody6.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            ErrorMessage responseBody7 = testClient.method(HttpMethod.valueOf(method))
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer TESTbadTOKEN")
                    .header(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage())
                    .cookie(admin.getFgpCookie().getName(), admin.getFgpCookie().getValue())
                    .body(BodyInserters.fromValue(getBodyPlaceholder(path, method)))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody7.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
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

    private RegisterUserDto getUserRegistrationDtoWithUniqueEmail() {
        RegisterUserDto user = getUserRegistrationDto();
        user.setEmail("xxxxxx@xxx.com");
        return user;
    }

    private RegisterUserDto getUserRegistrationDtoWithAlreadyExistedEmail() {
        RegisterUserDto user = getUserRegistrationDto();
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

    private RegisterUserDto getUserRegistrationDto() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setFirstName("Adam");
        dto.setLastName("Lubnie");
        dto.setPassword("password");

        dto.setPesel("90051912345");
        dto.setDateOfBirth(LocalDate.of(1990, 5, 19));
        dto.setGender(Gender.MALE);
        dto.setNationality("Polish");
        dto.setMothersName("Apolonia");
        dto.setFathersName("Kazimierz");

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
        if (path.contains("/notifications") && method.equals("DELETE")) return new ArrayList<>();
        else return new PaymentRequest();
    }
}