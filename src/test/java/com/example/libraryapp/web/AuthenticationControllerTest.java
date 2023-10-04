package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationRequest;
import com.example.libraryapp.domain.auth.AuthenticationResponse;
import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.auth.RegisterRequest;
import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void shouldAuthenticateAUserIfCredentialsAreCorrect() {
        AuthenticationRequest userCredentials = getCorrectCredentials();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/authenticate", userCredentials, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldNotAuthenticateAUserIfCredentialsAreNotCorrect() {
        AuthenticationRequest userCredentials = getCredentialsWithBadPassword();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/authenticate", userCredentials, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        userCredentials = getCredentialsWithBadEmail();
        createResponse = restTemplate.postForEntity("/api/v1/login", userCredentials, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewUserIfEmailIsUnique() {
        RegisterRequest userToSave = getUserRegistrationDtoWithUniqueEmail();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/register", userToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewUserData = createResponse.getHeaders().getLocation();
        HttpEntity<Object> request = createAdminRequest();
        ResponseEntity<String> getResponse = restTemplate
                .exchange(locationOfNewUserData, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserDto returnedUser = getUserDtoFromResponse(getResponse);
        assertThat(returnedUser.getId()).isNotNull();
        assertThat(returnedUser.getCard()).isNotNull();
        assertThat(returnedUser.getEmail()).isEqualTo(userToSave.getEmail());
        assertThat(returnedUser.getFirstName()).isEqualTo(userToSave.getFirstName());
        assertThat(returnedUser.getLastName()).isEqualTo(userToSave.getLastName());
    }

    @Test
    void shouldNotCreateANewUserIfEmailAlreadyExists() {
        RegisterRequest userToSave = getUserRegistrationDtoWithAlreadyExistedEmail();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/register", userToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
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

    private RegisterRequest getUserRegistrationDto() {
        RegisterRequest dto = new RegisterRequest();
        dto.setPassword("pass");
        dto.setFirstName("First Name");
        dto.setLastName("Last Name");
        return dto;
    }

    private UserDto getUserDtoFromResponse(ResponseEntity<String> response) {
        UserDto dto = new UserDto();
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        dto.setId(((Number) documentContext.read("$.id")).longValue());
        dto.setEmail(documentContext.read("$.email"));
        dto.setFirstName(documentContext.read("$.firstName"));
        dto.setLastName(documentContext.read("$.lastName"));

        LibraryCard card = new LibraryCard();
        card.setId(((Number) documentContext.read("$.card.id")).longValue());
        card.setBarcode(documentContext.read("$.card.barcode"));
        card.setIssuedAt(LocalDateTime.parse(documentContext.read("$.card.issuedAt")));
        card.setActive(documentContext.read("$.card.active"));

        dto.setCard(card);
        return dto;
    }

    private AuthenticationRequest getCorrectCredentials() {
        AuthenticationRequest dto = new AuthenticationRequest();
        dto.setUsername("user@example.com");
        dto.setPassword("userpass");
        return dto;
    }

    private AuthenticationRequest getCredentialsWithBadPassword() {
        AuthenticationRequest dto = new AuthenticationRequest();
        dto.setUsername("user@example.com");
        dto.setPassword("INCORRECTuserpass");
        return dto;
    }

    private AuthenticationRequest getCredentialsWithBadEmail() {
        AuthenticationRequest dto = new AuthenticationRequest();
        dto.setUsername("userINCORRECT@example.com");
        dto.setPassword("userpass");
        return dto;
    }

    private HttpEntity<Object> createAdminRequest() {
        AuthenticationRequest admin = new AuthenticationRequest();
        admin.setUsername("admin@example.com");
        admin.setPassword("adminpass");

        HttpHeaders headers = createHeaderWithTokenFor(admin);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Object> createAdminRequest(Object requestBody) {
        AuthenticationRequest admin = new AuthenticationRequest();
        admin.setUsername("admin@example.com");
        admin.setPassword("adminpass");

        HttpHeaders headers = createHeaderWithTokenFor(admin);
        return new HttpEntity<>(requestBody, headers);
    }

    private HttpEntity<Object> createUserRequest() {
        AuthenticationRequest user = new AuthenticationRequest();
        user.setUsername("user@example.com");
        user.setPassword("userpass");

        HttpHeaders headers = createHeaderWithTokenFor(user);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Object> createUserRequest(Object requestBody) {
        AuthenticationRequest user = new AuthenticationRequest();
        user.setUsername("user@example.com");
        user.setPassword("userpass");

        HttpHeaders headers = createHeaderWithTokenFor(user);
        return new HttpEntity<>(requestBody, headers);
    }

    private HttpHeaders createHeaderWithTokenFor(AuthenticationRequest user) {
        AuthenticationResponse response = authenticationService.authenticate(user);
        String token = response.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }
}