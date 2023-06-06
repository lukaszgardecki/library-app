package com.example.libraryapp.web;

import com.example.libraryapp.domain.user.dto.UserLoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldLogInAUserIfCredentialsAreCorrect() {
        UserLoginDto userCredentials = getCorrectCredentials();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/login", userCredentials, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotLogInAUserIfCredentialsAreNotCorrect() {
        UserLoginDto userCredentials = getCredentialsWithBadPassword();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/login", userCredentials, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        userCredentials = getCredentialsWithBadEmail();
        createResponse = restTemplate.postForEntity("/api/v1/login", userCredentials, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private UserLoginDto getCorrectCredentials() {
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername("user@example.com");
        dto.setPassword("userpass");
        return dto;
    }

    private UserLoginDto getCredentialsWithBadPassword() {
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername("user@example.com");
        dto.setPassword("INCORRECTuserpass");
        return dto;
    }

    private UserLoginDto getCredentialsWithBadEmail() {
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername("userINCORRECT@example.com");
        dto.setPassword("userpass");
        return dto;
    }
}