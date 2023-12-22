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
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance( TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class MemberControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthenticationService authenticationService;
    private HttpHeaders adminHeader;
    private HttpHeaders userHeader;

    @BeforeAll
    void authenticate() {
        LoginRequest admin = new LoginRequest();
        admin.setUsername("admin@example.com");
        admin.setPassword("adminpass");
        LoginResponse adminLoginResponse = authenticationService.authenticate(admin);
        String adminToken = adminLoginResponse.getToken();

        HttpHeaders adminHeader = new HttpHeaders();
        adminHeader.setBearerAuth(adminToken);
        adminHeader.setContentType(MediaType.APPLICATION_JSON);
        this.adminHeader = adminHeader;

        LoginRequest user = new LoginRequest();
        user.setUsername("user@example.com");
        user.setPassword("userpass");
        LoginResponse userLoginResponse = authenticationService.authenticate(user);
        String userToken = userLoginResponse.getToken();

        HttpHeaders userHeader = new HttpHeaders();
        userHeader.setBearerAuth(userToken);
        userHeader.setContentType(MediaType.APPLICATION_JSON);
        this.userHeader = userHeader;
    }


    @Test
    @Order(1)
    void shouldReturnAllUsersWhenAdminRequested() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/members", HttpMethod.GET, request, String.class);
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
    @Order(2)
    void shouldNotReturnAllUsersWhenUserRequested() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/members", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(3)
    void shouldNotReturnAllUsersWhenRequestIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/members", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(4)
    void shouldReturnAnExistingUserDataIfAdminRequested() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/members/3", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberDto returnedUser = getUserDtoFromResponse(getResponse);
        assertThat(returnedUser.getId()).isEqualTo(3);
        assertThat(returnedUser.getFirstName()).isEqualTo("Adam");
        assertThat(returnedUser.getLastName()).isEqualTo("Mickiewicz");
        assertThat(returnedUser.getEmail()).isEqualTo("a.mickiewicz@gmail.com");
        assertThat(returnedUser.getDateOfMembership()).isEqualTo("2023-05-23");
        assertThat(returnedUser.getTotalBooksBorrowed()).isEqualTo(0);
        assertThat(returnedUser.getTotalBooksReserved()).isEqualTo(2);
        assertThat(returnedUser.getCharge()).isEqualTo(BigDecimal.valueOf(0.0));
        assertThat(returnedUser.getCard().getBarcode()).isEqualTo(Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "00000003");
    }

    @Test
    @Order(5)
    void shouldReturnAnExistingUserDataIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/members/2", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberDto returnedUser = getUserDtoFromResponse(getResponse);
        assertThat(returnedUser.getId()).isEqualTo(2);
        assertThat(returnedUser.getFirstName()).isEqualTo("Kamil");
        assertThat(returnedUser.getLastName()).isEqualTo("Nielubi");
        assertThat(returnedUser.getEmail()).isEqualTo("user@example.com");
        assertThat(returnedUser.getDateOfMembership()).isEqualTo("2023-05-22");
        assertThat(returnedUser.getTotalBooksBorrowed()).isEqualTo(0);
        assertThat(returnedUser.getTotalBooksReserved()).isEqualTo(2);
        assertThat(returnedUser.getCharge()).isEqualTo(BigDecimal.valueOf(0.0));
        assertThat(returnedUser.getCard().getBarcode()).isEqualTo(Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "00000002");
    }

    @Test
    @Order(6)
    void shouldNotReturnAnExistingUserDataIfUserRequestedAndDoesNotOwnThisData() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/members/1", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(7)
    void shouldNotReturnUserDataThatDoesNotExist() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/members/999999", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(8)
    void shouldNotReturnUserDataIfRequestIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/v1/members/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        getResponse = restTemplate.getForEntity("/api/v1/members/9999999", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(17)
    void shouldPartiallyUpdateAnExistingUserDataIfAdminRequested() {
        long memberIdToUpdate = 2L;
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        HttpEntity<Object> request = new HttpEntity<>(userFieldsToUpdate, adminHeader);

        ResponseEntity<String> getResponseBeforeUpdate = restTemplate
                .exchange("/api/v1/members/" + memberIdToUpdate, HttpMethod.GET, request, String.class);
        assertThat(getResponseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        MemberDto userBeforeUpdate = getUserDtoFromResponse(getResponseBeforeUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .exchange("/api/v1/members/" + memberIdToUpdate, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponseAfterUpdate = restTemplate
                .exchange("/api/v1/members/" + memberIdToUpdate, HttpMethod.GET, request, String.class);
        assertThat(getResponseAfterUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberDto userAfterUpdate = getUserDtoFromResponse(getResponseAfterUpdate);
        assertThat(userAfterUpdate.getId()).isNotNull();
        assertThat(userAfterUpdate.getId()).isEqualTo(userBeforeUpdate.getId());
        assertThat(userAfterUpdate.getFirstName()).isNotEqualTo(userBeforeUpdate.getFirstName());
        assertThat(userAfterUpdate.getLastName()).isNotEqualTo(userBeforeUpdate.getLastName());
        assertThat(userAfterUpdate.getEmail()).isEqualTo(userBeforeUpdate.getEmail());
        assertThat(userAfterUpdate.getDateOfMembership()).isEqualTo(userBeforeUpdate.getDateOfMembership());
        assertThat(userAfterUpdate.getTotalBooksBorrowed()).isEqualTo(userBeforeUpdate.getTotalBooksBorrowed());
        assertThat(userAfterUpdate.getTotalBooksReserved()).isEqualTo(userBeforeUpdate.getTotalBooksReserved());
        assertThat(userAfterUpdate.getCharge()).isEqualTo(userBeforeUpdate.getCharge());

        assertThat(userAfterUpdate.getCard().getId()).isEqualTo(userBeforeUpdate.getCard().getId());
        assertThat(userAfterUpdate.getCard().getIssuedAt()).isEqualTo(userBeforeUpdate.getCard().getIssuedAt());
        assertThat(userAfterUpdate.getCard().getBarcode()).isEqualTo(userBeforeUpdate.getCard().getBarcode());
    }

    @Test
    @Order(9)
    void shouldNotPartiallyUpdateAnExistingUserDataIfUserRequestedAndDoesNotOwnThisData() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        HttpEntity<Object> request = new HttpEntity<>(userFieldsToUpdate, userHeader);
        ResponseEntity<Void> patchResponse = restTemplate
                .exchange("/api/v1/members/3", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(18)
    void shouldPartiallyUpdateAnExistingUserDataIfUserRequestedAndDoesOwnThisData() {
        long memberIdToUpdate = 2L;
        MemberUpdateDto userFieldsToUpdate = new MemberUpdateDto();
        userFieldsToUpdate.setFirstName("Testimie");
        userFieldsToUpdate.setLastName("Testnazwisko");

        HttpEntity<Object> request = new HttpEntity<>(userFieldsToUpdate, userHeader);

        ResponseEntity<String> getResponseBeforeUpdate = restTemplate
                .exchange("/api/v1/members/" + memberIdToUpdate, HttpMethod.GET, request, String.class);
        assertThat(getResponseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        MemberDto userBeforeUpdate = getUserDtoFromResponse(getResponseBeforeUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .exchange("/api/v1/members/" + memberIdToUpdate, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        HttpEntity<Object> userAfterPartiallyUpdateRequest = new HttpEntity<>(userHeader);
        ResponseEntity<String> getResponseAfterUpdate = restTemplate
                .exchange("/api/v1/members/" + memberIdToUpdate, HttpMethod.GET, userAfterPartiallyUpdateRequest, String.class);
        assertThat(getResponseAfterUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);

        MemberDto userAfterUpdate = getUserDtoFromResponse(getResponseAfterUpdate);
        assertThat(userAfterUpdate.getFirstName()).isNotEqualTo(userBeforeUpdate.getFirstName());
        assertThat(userAfterUpdate.getLastName()).isNotEqualTo(userBeforeUpdate.getLastName());

        assertThat(userAfterUpdate.getId()).isEqualTo(userBeforeUpdate.getId());
        assertThat(userAfterUpdate.getEmail()).isEqualTo(userBeforeUpdate.getEmail());
        assertThat(userAfterUpdate.getDateOfMembership()).isEqualTo(userBeforeUpdate.getDateOfMembership());
        assertThat(userAfterUpdate.getTotalBooksBorrowed()).isEqualTo(userBeforeUpdate.getTotalBooksBorrowed());
        assertThat(userAfterUpdate.getTotalBooksReserved()).isEqualTo(userBeforeUpdate.getTotalBooksReserved());
        assertThat(userAfterUpdate.getCharge()).isEqualTo(userBeforeUpdate.getCharge());

        assertThat(userAfterUpdate.getCard().getId()).isEqualTo(userBeforeUpdate.getCard().getId());
        assertThat(userAfterUpdate.getCard().getIssuedAt()).isEqualTo(userBeforeUpdate.getCard().getIssuedAt());
        assertThat(userAfterUpdate.getCard().getBarcode()).isEqualTo(userBeforeUpdate.getCard().getBarcode());
    }

    @Test
    @Order(10)
    void shouldNotPartiallyUpdateUserDataThatDoesNotExist() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        HttpEntity<Object> request = new HttpEntity<>(userFieldsToUpdate, adminHeader);

        ResponseEntity<Void> patchResponse = restTemplate
                .exchange("/api/v1/members/999999", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(11)
    void shouldNotPartiallyUpdateUserDataIfRequestIsNotAuthenticated() {
        MemberUpdateDto userFieldsToUpdate = getMemberDtoToPartialUpdate();
        HttpEntity<MemberUpdateDto> request = new HttpEntity<>(userFieldsToUpdate);
        ResponseEntity<Void> patchResponse = restTemplate
                .exchange("/api/v1/members/999999", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(21)
    void shouldNotPartiallyUpdateUserDataIfRequestBodyIsEmpty() {
        HttpEntity<?> request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> response = restTemplate
                .exchange("/api/v1/members/2", HttpMethod.PATCH, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(19)
    void shouldDeleteAnExistingUserIfAdminRequested() {
        long memberIdToDelete = 3L;
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/members/" + memberIdToDelete, HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/members/" + memberIdToDelete, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> reservation1Response = restTemplate
                .exchange("/api/v1/reservations/10", HttpMethod.GET, request, String.class);
        DocumentContext document1Context = JsonPath.parse(reservation1Response.getBody());
        ReservationStatus reservation1Status = ReservationStatus.valueOf(document1Context.read("$.status"));
        assertThat(reservation1Status).isEqualTo(ReservationStatus.CANCELED);

        ResponseEntity<String> reservation2Response = restTemplate
                .exchange("/api/v1/reservations/11", HttpMethod.GET, request, String.class);
        DocumentContext document2Context = JsonPath.parse(reservation2Response.getBody());
        ReservationStatus reservation2Status = ReservationStatus.valueOf(document2Context.read("$.status"));
        assertThat(reservation2Status).isEqualTo(ReservationStatus.CANCELED);

        ResponseEntity<String> bookItem1 = restTemplate
                .exchange("/api/v1/book-items/7", HttpMethod.GET, request, String.class);
        DocumentContext bookItem1JSON = JsonPath.parse(bookItem1.getBody());
        BookItemStatus bookItem1Status = BookItemStatus.valueOf(bookItem1JSON.read("$.status"));
        assertThat(bookItem1Status).isEqualTo(BookItemStatus.LOANED);

        ResponseEntity<String> bookItem2 = restTemplate
                .exchange("/api/v1/book-items/4", HttpMethod.GET, request, String.class);
        DocumentContext bookItem2JSON = JsonPath.parse(bookItem2.getBody());
        BookItemStatus bookItem2Status = BookItemStatus.valueOf(bookItem2JSON.read("$.status"));
        assertThat(bookItem2Status).isEqualTo(BookItemStatus.LOANED);
    }

    @Test
    @Order(12)
    void shouldNotDeleteAnExistingUserWhichHasNotReturnedTheBooks() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/members/1", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        deleteResponse = restTemplate
                .exchange("/api/v1/members/4", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(13)
    void shouldNotDeleteAnExistingUserIfTheyHaveUnsettledFine() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/members/8", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(14)
    void shouldNotDeleteAnExistingUserIfUserRequestedAndDoesNotOwnThisData() {
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/members/1", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(20)
    void shouldDeleteAnExistingUserIfUserRequestedAndDoesOwnThisData() {
        long memberIdToDelete = 2L;
        HttpEntity<Object> request = new HttpEntity<>(userHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/members/" + memberIdToDelete, HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        request = new HttpEntity<>(adminHeader);
        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/members/" + memberIdToDelete, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> reservation1Response = restTemplate
                .exchange("/api/v1/reservations/8", HttpMethod.GET, request, String.class);
        DocumentContext document1Context = JsonPath.parse(reservation1Response.getBody());
        ReservationStatus reservation1Status = ReservationStatus.valueOf(document1Context.read("$.status"));
        assertThat(reservation1Status).isEqualTo(ReservationStatus.CANCELED);

        ResponseEntity<String> reservation2Response = restTemplate
                .exchange("/api/v1/reservations/9", HttpMethod.GET, request, String.class);
        DocumentContext document2Context = JsonPath.parse(reservation2Response.getBody());
        ReservationStatus reservation2Status = ReservationStatus.valueOf(document2Context.read("$.status"));
        assertThat(reservation2Status).isEqualTo(ReservationStatus.CANCELED);

        ResponseEntity<String> bookItem1 = restTemplate
                .exchange("/api/v1/book-items/3", HttpMethod.GET, request, String.class);
        DocumentContext bookItem1JSON = JsonPath.parse(bookItem1.getBody());
        BookItemStatus bookItem1Status = BookItemStatus.valueOf(bookItem1JSON.read("$.status"));
        assertThat(bookItem1Status).isEqualTo(BookItemStatus.LOANED);

        ResponseEntity<String> bookItem2 = restTemplate
                .exchange("/api/v1/book-items/4", HttpMethod.GET, request, String.class);
        DocumentContext bookItem2JSON = JsonPath.parse(bookItem2.getBody());
        BookItemStatus bookItem2Status = BookItemStatus.valueOf(bookItem2JSON.read("$.status"));
        assertThat(bookItem2Status).isEqualTo(BookItemStatus.LOANED);
    }

    @Test
    @Order(15)
    void shouldNotDeleteUserThatDoesNotExist() {
        HttpEntity<Object> request = new HttpEntity<>(adminHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/members/999999", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(16)
    void shouldNotDeleteUserIfRequestIsNotAuthenticated() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/members/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private MemberDto getUserDtoFromResponse(ResponseEntity<String> response) {
        MemberDto dto = new MemberDto();
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        dto.setId(((Number) documentContext.read("$.id")).longValue());
        dto.setFirstName(documentContext.read("$.firstName"));
        dto.setLastName(documentContext.read("$.lastName"));
        dto.setEmail(documentContext.read("$.email"));
        dto.setDateOfMembership(LocalDate.parse(documentContext.read("$.dateOfMembership"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setTotalBooksBorrowed(documentContext.read("$.totalBooksBorrowed"));
        dto.setTotalBooksReserved(documentContext.read("$.totalBooksReserved"));
        dto.setCharge(BigDecimal.valueOf(((Number) documentContext.read("$.charge")).doubleValue()));

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
        dto.setPassword("passss");
        return dto;
    }
}
