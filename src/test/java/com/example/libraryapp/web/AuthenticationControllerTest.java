package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.LoginRequest;
import com.example.libraryapp.domain.auth.LoginResponse;
import com.example.libraryapp.domain.auth.RegisterRequest;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance( TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldAuthenticateAUserIfCredentialsAreCorrect() {
        LoginRequest userCredentials = getCorrectCredentials();
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/authenticate", userCredentials, String.class);
        LoginResponse token = getTokenFromResponse(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(token).isNotNull();
        assertThat(token.getToken()).isNotNull();
        assertThat(token.getToken()).isNotEmpty();
        assertThat(token.getToken()).isNotBlank();
    }

    @Test
    void shouldNotAuthenticateAUserIfCredentialsAreNotCorrect() {
        LoginRequest userCredentials = getCredentialsWithBadPassword();
        ResponseEntity<String> authResponse = restTemplate
                .postForEntity("/api/v1/authenticate", userCredentials, String.class);
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        userCredentials = getCredentialsWithBadEmail();
        authResponse = restTemplate.postForEntity("/api/v1/authenticate", userCredentials, String.class);
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotAuthenticateAUserIfRequestBodyIsEmpty() {
        ResponseEntity<String> authResponse = restTemplate
                .postForEntity("/api/v1/authenticate", ResponseEntity.EMPTY, String.class);
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewUserIfEmailIsUnique() {
        RegisterRequest userToSave = getUserRegistrationDtoWithUniqueEmail();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/register", userToSave, String.class);
        LoginResponse token = getTokenFromResponse(createResponse);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResponse.getBody()).isNotNull();
        assertThat(token).isNotNull();
        assertThat(token.getToken()).isNotNull();
        assertThat(token.getToken()).isNotEmpty();
        assertThat(token.getToken()).isNotBlank();
    }

    @Test
    void shouldNotCreateANewUserIfEmailAlreadyExists() {
        RegisterRequest userToSave = getUserRegistrationDtoWithAlreadyExistedEmail();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/register", userToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotCreateAUserIfRequestBodyIsEmpty() {
        ResponseEntity<String> authResponse = restTemplate
                .postForEntity("/api/v1/register", ResponseEntity.EMPTY, String.class);
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
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

    private LoginResponse getTokenFromResponse(ResponseEntity<String> response) {
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String token = documentContext.read("$.token");
        return new LoginResponse(token);
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