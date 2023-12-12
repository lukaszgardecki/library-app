package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.auth.LoginRequest;
import com.example.libraryapp.domain.auth.LoginResponse;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.member.dto.MemberUpdateDto;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import com.example.libraryapp.management.Constants;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class MemberControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    @DirtiesContext
    void shouldReturnAllUsersWhenAdminRequested() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/users", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int userListLength = documentContext.read("$._embedded.memberDtoList.length()");
        assertThat(userListLength).isEqualTo(8);

        int sizeParam = documentContext.read("$.page.size");
        assertThat(sizeParam).isEqualTo(20);
        int totalElementsParam = documentContext.read("$.page.totalElements");
        assertThat(totalElementsParam).isEqualTo(8);
        int totalPagesParam = documentContext.read("$.page.totalPages");
        assertThat(totalPagesParam).isEqualTo(1);
        int numberParam = documentContext.read("$.page.number");
        assertThat(numberParam).isEqualTo(0);
    }

    @Test
    @DirtiesContext
    void shouldNotReturnAllUsersWhenUserRequested() {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/users", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotReturnAllUsersWhenRequestIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/users", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldReturnAnExistingUserDataIfAdminRequested() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/users/3", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberDto returnedUser = getUserDtoFromResponse(getResponse);
        assertThat(returnedUser.getId()).isEqualTo(3);
        assertThat(returnedUser.getFirstName()).isEqualTo("Adam");
        assertThat(returnedUser.getLastName()).isEqualTo("Mickiewicz");
        assertThat(returnedUser.getEmail()).isEqualTo("a.mickiewicz@gmail.com");
        assertThat(returnedUser.getCard().getBarcode()).isEqualTo(Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "00000003");
    }

    @Test
    @DirtiesContext
    void shouldReturnAnExistingUserDataIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberDto returnedUser = getUserDtoFromResponse(getResponse);
        assertThat(returnedUser.getId()).isEqualTo(2);
        assertThat(returnedUser.getFirstName()).isEqualTo("Kamil");
        assertThat(returnedUser.getLastName()).isEqualTo("Nielubi");
        assertThat(returnedUser.getEmail()).isEqualTo("user@example.com");
        assertThat(returnedUser.getCard().getBarcode()).isEqualTo(Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "00000002");
    }

    @Test
    void shouldNotReturnAnExistingUserDataIfUserRequestedAndDoesNotOwnThisData() {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/users/1", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotReturnUserDataThatDoesNotExist() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/users/999999", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnUserDataIfRequestIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/v1/users/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        getResponse = restTemplate.getForEntity("/api/v1/users/9999999", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldPartiallyUpdateAnExistingUserDataIfAdminRequested() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        HttpEntity<Object> request = createAdminRequest(userFieldsToUpdate);

        ResponseEntity<String> getResponseBeforeUpdate = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.GET, request, String.class);
        assertThat(getResponseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        MemberDto userBeforeUpdate = getUserDtoFromResponse(getResponseBeforeUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponseAfterUpdate = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.GET, request, String.class);
        assertThat(getResponseAfterUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberDto userAfterUpdate = getUserDtoFromResponse(getResponseAfterUpdate);
        assertThat(userAfterUpdate.getId()).isNotNull();
        assertThat(userAfterUpdate.getId()).isEqualTo(userBeforeUpdate.getId());
        assertThat(userAfterUpdate.getFirstName()).isNotEqualTo(userBeforeUpdate.getFirstName());
        assertThat(userAfterUpdate.getLastName()).isNotEqualTo(userBeforeUpdate.getLastName());
        assertThat(userAfterUpdate.getEmail()).isNotEqualTo(userBeforeUpdate.getEmail());
    }

    @Test
    @DirtiesContext
    void shouldNotPartiallyUpdateAnExistingUserDataIfUserRequestedAndDoesNotOwnThisData() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        HttpEntity<Object> request = createUserRequest(userFieldsToUpdate);
        ResponseEntity<Void> patchResponse = restTemplate
                .exchange("/api/v1/users/3", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldPartiallyUpdateAnExistingUserDataIfUserRequestedAndDoesOwnThisData() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        HttpEntity<Object> request = createUserRequest(userFieldsToUpdate);

        ResponseEntity<String> getResponseBeforeUpdate = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.GET, request, String.class);
        assertThat(getResponseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        MemberDto userBeforeUpdate = getUserDtoFromResponse(getResponseBeforeUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        HttpEntity<Object> userAfterPartiallyUpdateRequest = createUserAfterPartiallyUpdateRequest();
        ResponseEntity<String> getResponseAfterUpdate = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.GET, userAfterPartiallyUpdateRequest, String.class);
        assertThat(getResponseAfterUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberDto userAfterUpdate = getUserDtoFromResponse(getResponseAfterUpdate);
        assertThat(userAfterUpdate.getId()).isNotNull();
        assertThat(userAfterUpdate.getId()).isEqualTo(userBeforeUpdate.getId());
        assertThat(userAfterUpdate.getFirstName()).isNotEqualTo(userBeforeUpdate.getFirstName());
        assertThat(userAfterUpdate.getLastName()).isNotEqualTo(userBeforeUpdate.getLastName());
        assertThat(userAfterUpdate.getEmail()).isNotEqualTo(userBeforeUpdate.getEmail());
    }

    @Test
    @DirtiesContext
    void shouldNotPartiallyUpdateUserDataThatDoesNotExist() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        HttpEntity<Object> request = createAdminRequest(userFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .exchange("/api/v1/users/999999", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotPartiallyUpdateUserDataIfRequestIsNotAuthenticated() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        HttpEntity<MemberUpdateDto> request = new HttpEntity<>(userFieldsToUpdate);
        ResponseEntity<Void> patchResponse = restTemplate
                .exchange("/api/v1/users/999999", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
//    @Transactional
    void shouldDeleteAnExistingUserIfAdminRequested() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> reservationResponse = restTemplate
                .exchange("/api/v1/reservations/8", HttpMethod.GET, request, String.class);
        DocumentContext documentContext = JsonPath.parse(reservationResponse.getBody());
        ReservationStatus status = ReservationStatus.valueOf(documentContext.read("$.status"));
        assertThat(status).isEqualTo(ReservationStatus.CANCELED);

        ResponseEntity<String> bookItem1 = restTemplate
                .exchange("/api/v1/book-items/5", HttpMethod.GET, request, String.class);
        DocumentContext bookItem1JSON = JsonPath.parse(bookItem1.getBody());
        BookItemStatus bookItem1Status = BookItemStatus.valueOf(bookItem1JSON.read("$.status"));
        assertThat(bookItem1Status).isEqualTo(BookItemStatus.AVAILABLE);

        ResponseEntity<String> bookItem2 = restTemplate
                .exchange("/api/v1/book-items/4", HttpMethod.GET, request, String.class);
        DocumentContext bookItem2JSON = JsonPath.parse(bookItem2.getBody());
        BookItemStatus bookItem2Status = BookItemStatus.valueOf(bookItem2JSON.read("$.status"));
        assertThat(bookItem2Status).isEqualTo(BookItemStatus.LOANED);

    }

    @Test
    @DirtiesContext
    void shouldNotDeleteAnExistingUserWhichHasNotReturnedTheBooks() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/users/1", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        deleteResponse = restTemplate
                .exchange("/api/v1/users/4", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @DirtiesContext
    void shouldNotDeleteAnExistingUserIfTheyHaveUnsettledFine() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/users/8", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @DirtiesContext
    void shouldNotDeleteAnExistingUserIfUserRequestedAndDoesNotOwnThisData() {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/users/1", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldDeleteAnExistingUserIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        request = createAdminRequest();
        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/users/2", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotDeleteUserThatDoesNotExist() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/users/999999", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotDeleteUserIfRequestIsNotAuthenticated() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/users/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private MemberDto getUserDtoFromResponse(ResponseEntity<String> response) {
        MemberDto dto = new MemberDto();
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        dto.setId(((Number) documentContext.read("$.id")).longValue());
        dto.setFirstName(documentContext.read("$.firstName"));
        dto.setLastName(documentContext.read("$.lastName"));
        dto.setEmail(documentContext.read("$.email"));

        LibraryCard card = new LibraryCard();
        card.setId(((Number) documentContext.read("$.card.id")).longValue());
        card.setBarcode(documentContext.read("$.card.barcode"));
        card.setIssuedAt(LocalDateTime.parse(documentContext.read("$.card.issuedAt")));
        card.setActive(documentContext.read("$.card.active"));

        dto.setCard(card);
        return dto;
    }

    private MemberUpdateDto getMemberDtoToPartialUpdate() {
        MemberUpdateDto dto = new MemberUpdateDto();
        dto.setFirstName("Kunegunda");
        dto.setLastName("Niewiadomska");
        dto.setEmail("xxxxxxxx@xxxxx.com");
        dto.setPassword("passss");
        return dto;
    }

    private HttpEntity<Object> createAdminRequest() {
        LoginRequest admin = new LoginRequest();
        admin.setUsername("admin@example.com");
        admin.setPassword("adminpass");

        HttpHeaders headers = createHeaderWithTokenFor(admin);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Object> createAdminRequest(Object requestBody) {
        LoginRequest admin = new LoginRequest();
        admin.setUsername("admin@example.com");
        admin.setPassword("adminpass");

        HttpHeaders headers = createHeaderWithTokenFor(admin);
        return new HttpEntity<>(requestBody, headers);
    }

    private HttpEntity<Object> createUserRequest() {
        LoginRequest user = new LoginRequest();
        user.setUsername("user@example.com");
        user.setPassword("userpass");

        HttpHeaders headers = createHeaderWithTokenFor(user);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Object> createUserRequest(Object requestBody) {
        LoginRequest user = new LoginRequest();
        user.setUsername("user@example.com");
        user.setPassword("userpass");

        HttpHeaders headers = createHeaderWithTokenFor(user);
        return new HttpEntity<>(requestBody, headers);
    }

    private HttpEntity<Object> createUserAfterPartiallyUpdateRequest() {
        LoginRequest user = new LoginRequest();
        user.setUsername("xxxxxxxx@xxxxx.com");
        user.setPassword("passss");

        HttpHeaders headers = createHeaderWithTokenFor(user);
        return new HttpEntity<>(headers);
    }

    private HttpHeaders createHeaderWithTokenFor(LoginRequest user) {
        LoginResponse response = authenticationService.authenticate(user);
        String token = response.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }
}
