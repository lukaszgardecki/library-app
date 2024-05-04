package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.LoginRequest;
import com.example.libraryapp.domain.auth.RegisterRequest;
import com.example.libraryapp.domain.token.Token;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationControllerTest extends BaseTest {

    @Test
    void shouldAuthenticateAUserIfCredentialsAreCorrect() {
        LoginRequest userCredentials = getCorrectCredentials();
        Token token = client.post()
                .uri("/api/v1/authenticate")
                .body(BodyInserters.fromValue(userCredentials))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Token.class)
                .returnResult().getResponseBody();

        assertThat(token).isNotNull();
        assertThat(token.getToken()).isNotNull();
        assertThat(token.getToken()).isNotEmpty();
        assertThat(token.getToken()).isNotBlank();
    }

    @Test
    void shouldNotAuthenticateAUserIfCredentialsAreNotCorrect() {
        LoginRequest userCredentials = getCredentialsWithBadPassword();
        client.post()
                .uri("/api/v1/authenticate")
                .body(BodyInserters.fromValue(userCredentials))
                .exchange()
                .expectStatus().isForbidden();

        userCredentials = getCredentialsWithBadEmail();
        client.post()
                .uri("/api/v1/authenticate")
                .body(BodyInserters.fromValue(userCredentials))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void shouldNotAuthenticateAUserIfRequestBodyIsEmpty() {
        client.post()
                .uri("/api/v1/authenticate")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void shouldCreateANewUserIfEmailIsUnique() {
        RegisterRequest userToSave = getUserRegistrationDtoWithUniqueEmail();
        Token token = client.post()
                .uri("/api/v1/register")
                .body(BodyInserters.fromValue(userToSave))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Token.class)
                .returnResult().getResponseBody();

        assertThat(token).isNotNull();
        assertThat(token.getToken()).isNotNull();
        assertThat(token.getToken()).isNotEmpty();
        assertThat(token.getToken()).isNotBlank();
    }

    @Test
    void shouldNotCreateANewUserIfEmailAlreadyExists() {
        RegisterRequest userToSave = getUserRegistrationDtoWithAlreadyExistedEmail();
        client.post()
                .uri("/api/v1/register")
                .body(BodyInserters.fromValue(userToSave))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void shouldNotCreateAUserIfRequestBodyIsEmpty() {
        client.post()
                .uri("/api/v1/register")
                .exchange()
                .expectStatus().isBadRequest();
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
}