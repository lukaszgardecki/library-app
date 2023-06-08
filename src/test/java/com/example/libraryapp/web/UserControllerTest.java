package com.example.libraryapp.web;

import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllUsersWhenAdminRequested() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/users", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int userListLength = documentContext.read("$._embedded.userDtoList.length()");
        assertThat(userListLength).isEqualTo(8);
    }

    @Test
    void shouldNotReturnAllUsersWhenUserRequested() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/users", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldReturnAnExistingUserDataIfAdminRequested() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/users/3", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserDto returnedUser = getUserDtoFromResponse(getResponse);
        assertThat(returnedUser.getId()).isEqualTo(3);
        assertThat(returnedUser.getFirstName()).isEqualTo("Adam");
        assertThat(returnedUser.getLastName()).isEqualTo("Mickiewicz");
        assertThat(returnedUser.getEmail()).isEqualTo("a.mickiewicz@gmail.com");
        assertThat(returnedUser.getCardNumber()).isEqualTo("00000003");
    }

    @Test
    void shouldReturnAnExistingUserDataIfUserRequestedAndDoesOwnThisData() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/users/2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserDto returnedUser = getUserDtoFromResponse(getResponse);
        assertThat(returnedUser.getId()).isEqualTo(2);
        assertThat(returnedUser.getFirstName()).isEqualTo("Kamil");
        assertThat(returnedUser.getLastName()).isEqualTo("Nielubi");
        assertThat(returnedUser.getEmail()).isEqualTo("user@example.com");
        assertThat(returnedUser.getCardNumber()).isEqualTo("00000002");
    }

    @Test
    void shouldNotReturnAnExistingUserDataIfUserRequestedAndDoesNotOwnThisData() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/users/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnUserDataThatDoesNotExist() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/users/999999", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/users/999999", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnUserDataIfRequestIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/v1/users/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        getResponse = restTemplate.getForEntity("/api/v1/users/9999999", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        getResponse = restTemplate.getForEntity("/api/v1/users", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldPartiallyUpdateAnExistingUserDataIfAdminRequested() {
        ResponseEntity<String> getResponseBeforeUpdate = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/users/2", String.class);
        assertThat(getResponseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserDto userBeforeUpdate = getUserDtoFromResponse(getResponseBeforeUpdate);

        UserUpdateDto userFieldsToUpdate = getUserDtoToPartialUpdate();
        HttpEntity<UserUpdateDto> request = new HttpEntity<>(userFieldsToUpdate);
        ResponseEntity<Void> patchResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getRestTemplate()
                .exchange("/api/v1/users/2", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponseAfterUpdate = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/users/2", String.class);
        assertThat(getResponseAfterUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserDto userAfterUpdate = getUserDtoFromResponse(getResponseAfterUpdate);
        assertThat(userAfterUpdate.getId()).isNotNull();
        assertThat(userAfterUpdate.getId()).isEqualTo(userBeforeUpdate.getId());
        assertThat(userAfterUpdate.getFirstName()).isNotEqualTo(userBeforeUpdate.getFirstName());
        assertThat(userAfterUpdate.getLastName()).isNotEqualTo(userBeforeUpdate.getLastName());
        assertThat(userAfterUpdate.getEmail()).isNotEqualTo(userBeforeUpdate.getEmail());
        assertThat(userAfterUpdate.getCardNumber()).isNotEqualTo(userBeforeUpdate.getCardNumber());
    }

    @Test
    void shouldNotPartiallyUpdateAnExistingUserDataIfUserRequestedAndDoesNotOwnThisData() {
        UserUpdateDto userFieldsToUpdate = getUserDtoToPartialUpdate();
        HttpEntity<UserUpdateDto> request = new HttpEntity<>(userFieldsToUpdate);
        ResponseEntity<Void> patchResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getRestTemplate()
                .exchange("/api/v1/users/3", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldPartiallyUpdateAnExistingUserDataIfUserRequestedAndDoesOwnThisData() {
        ResponseEntity<String> getResponseBeforeUpdate = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/users/2", String.class);
        assertThat(getResponseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserDto userBeforeUpdate = getUserDtoFromResponse(getResponseBeforeUpdate);

        UserUpdateDto userFieldsToUpdate = getUserDtoToPartialUpdate();
        HttpEntity<UserUpdateDto> request = new HttpEntity<>(userFieldsToUpdate);
        ResponseEntity<Void> patchResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getRestTemplate()
                .exchange("/api/v1/users/2", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponseAfterUpdate = restTemplate
                .withBasicAuth("xxxxxxxx@xxxxx.com", "passss")
                .getForEntity("/api/v1/users/2", String.class);
        assertThat(getResponseAfterUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserDto userAfterUpdate = getUserDtoFromResponse(getResponseAfterUpdate);
        assertThat(userAfterUpdate.getId()).isNotNull();
        assertThat(userAfterUpdate.getId()).isEqualTo(userBeforeUpdate.getId());
        assertThat(userAfterUpdate.getFirstName()).isNotEqualTo(userBeforeUpdate.getFirstName());
        assertThat(userAfterUpdate.getLastName()).isNotEqualTo(userBeforeUpdate.getLastName());
        assertThat(userAfterUpdate.getEmail()).isNotEqualTo(userBeforeUpdate.getEmail());
        assertThat(userAfterUpdate.getCardNumber()).isEqualTo(userBeforeUpdate.getCardNumber());
    }

    @Test
    void shouldNotPartiallyUpdateUserDataThatDoesNotExist() {
        UserUpdateDto userFieldsToUpdate = getUserDtoToPartialUpdate();
        HttpEntity<UserUpdateDto> request = new HttpEntity<>(userFieldsToUpdate);
        ResponseEntity<Void> patchResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getRestTemplate()
                .exchange("/api/v1/users/999999", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotPartiallyUpdateUserDataIfRequestIsNotAuthenticated() {
        UserUpdateDto userFieldsToUpdate = getUserDtoToPartialUpdate();
        HttpEntity<UserUpdateDto> request = new HttpEntity<>(userFieldsToUpdate);
        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/users/999999", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldDeleteAnExistingUserIfAdminRequested() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .exchange("/api/v1/users/2", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/users/2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> getUsersReservationsResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/reservations?userId=2", String.class);
        assertThat(getUsersReservationsResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> getUsersCheckoutsResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/checkouts?userId=2", String.class);
        assertThat(getUsersCheckoutsResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> getUsersReservedBooksResponse= restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/books/23", String.class);
        assertThat(getUsersReservedBooksResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getUsersReservedBooksResponse.getBody());
        boolean booksAvailability = documentContext.read("$.availability");
        assertThat(booksAvailability).isEqualTo(true);
    }

    @Test
    void shouldNotDeleteAnExistingUserWhichHasNotReturnedTheBooks() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .exchange("/api/v1/users/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);

        deleteResponse = restTemplate
                .withBasicAuth("a.kleks@gmail.com", "userpass2")
                .exchange("/api/v1/users/4", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    void shouldNotDeleteAnExistingUserIfUserRequestedAndDoesNotOwnThisData() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .exchange("/api/v1/users/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldDeleteAnExistingUserIfUserRequestedAndDoesOwnThisData() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .exchange("/api/v1/users/2", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/users/2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotDeleteUserThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .exchange("/api/v1/users/999999", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotDeleteUserIfRequestIsNotAuthenticated() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/users/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }


    private UserDto getUserDtoFromResponse(ResponseEntity<String> response) {
        UserDto dto = new UserDto();
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        dto.setId(((Number) documentContext.read("$.id")).longValue());
        dto.setFirstName(documentContext.read("$.firstName"));
        dto.setLastName(documentContext.read("$.lastName"));
        dto.setEmail(documentContext.read("$.email"));
        dto.setCardNumber(documentContext.read("$.cardNumber"));
        return dto;
    }

    private UserUpdateDto getUserDtoToPartialUpdate() {
        UserUpdateDto dto = new UserUpdateDto();
        dto.setFirstName("Kunegunda");
        dto.setLastName("Niewiadomska");
        dto.setEmail("xxxxxxxx@xxxxx.com");
        dto.setPassword("passss");
        dto.setCardNumber("99999999");
        dto.setRole("ADMIN");
        return dto;
    }
}
