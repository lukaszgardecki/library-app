package com.example.libraryapp.web;


import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserRegistrationDto;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RegisterControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DirtiesContext
    @Transactional
    void shouldCreateANewUserIfEmailIsUnique() {
        UserRegistrationDto userToSave = getUserRegistrationDtoWithUniqueEmail();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/register", userToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewUserData = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity(locationOfNewUserData, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserDto returnedUser = getUserDtoFromResponse(getResponse);
        assertThat(returnedUser.getId()).isNotNull();
        assertThat(returnedUser.getCardNumber()).isNotNull();
        assertThat(returnedUser.getEmail()).isEqualTo(userToSave.getEmail());
        assertThat(returnedUser.getFirstName()).isEqualTo(userToSave.getFirstName());
        assertThat(returnedUser.getLastName()).isEqualTo(userToSave.getLastName());
    }

    @Test
    void shouldNotCreateANewUserIfEmailAlreadyExists() {
        UserRegistrationDto userToSave = getUserRegistrationDtoWithAlreadyExistedEmail();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/register", userToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    private UserRegistrationDto getUserRegistrationDtoWithUniqueEmail() {
        UserRegistrationDto user = getUserRegistrationDto();
        user.setEmail("xxxxxx@xxx.com");
        return user;
    }

    private UserRegistrationDto getUserRegistrationDtoWithAlreadyExistedEmail() {
        UserRegistrationDto user = getUserRegistrationDto();
        user.setEmail("user@example.com");
        return user;
    }

    private UserRegistrationDto getUserRegistrationDto() {
        UserRegistrationDto dto = new UserRegistrationDto();
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
        dto.setCardNumber(documentContext.read("$.cardNumber"));
        return dto;
    }
}