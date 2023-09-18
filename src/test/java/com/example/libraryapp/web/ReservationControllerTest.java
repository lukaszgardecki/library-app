package com.example.libraryapp.web;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.reservation.ReservationDto;
import com.example.libraryapp.domain.reservation.ReservationToSaveDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ReservationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllReservationsIfAdminRequested() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/reservations", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int allReservationsLength = documentContext.read("$._embedded.reservationDtoList.length()");
        assertThat(allReservationsLength).isEqualTo(6);

        getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/reservations?userId=5", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        documentContext = JsonPath.parse(getResponse.getBody());
        int usersReservationsLength = documentContext.read("$._embedded.reservationDtoList.length()");
        assertThat(usersReservationsLength).isEqualTo(1);
    }

    @Test
    void shouldReturnPageOf3ReservationsIfAdminRequested() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/reservations?page=1&size=3", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.reservationDtoList.length()");
        assertThat(bookListLength).isEqualTo(3);
        int sizeParam = documentContext.read("$.page.size");
        assertThat(sizeParam).isEqualTo(3);
        int totalElementsParam = documentContext.read("$.page.totalElements");
        assertThat(totalElementsParam).isEqualTo(6);
        int totalPagesParam = documentContext.read("$.page.totalPages");
        assertThat(totalPagesParam).isEqualTo(2);
        int numberParam = documentContext.read("$.page.number");
        assertThat(numberParam).isEqualTo(1);
    }

    @Test
    void shouldReturnAllUsersReservationsIfUserRequestedAndDoesOwnThisData() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/reservations?userId=2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAllUsersReservationsIfUserRequestedAndDoesNotOwnThisData(Long userId) {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/reservations?userId=" + userId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnAllUsersReservationsIfUserRequested() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/reservations", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnAllReservationsIfUserIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/reservations", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        getResponse = restTemplate
                .getForEntity("/api/v1/reservations?userId=1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 5, 1",
            "2, 23, 2",
            "3, 12, 3",
            "4, 7, 4",
            "5, 25, 5",
            "6, 16, 6"
    })
    void shouldReturnAnExistingReservationIfAdminRequested(Long userId, Long bookId, Long reservationId) {
        UserDto user = findUserById(userId);
        BookDto book = findBookById(bookId);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/reservations/" + reservationId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ReservationDto returnedReservation = getReservationFromResponse(getResponse);
        assertThat(returnedReservation.getId()).isNotNull();
        assertThat(returnedReservation.getStartTime()).isNotNull();
        assertThat(returnedReservation.getEndTime()).isNotNull();
        assertThat(returnedReservation.getUser()).isEqualTo(user);
        assertThat(returnedReservation.getBook()).isEqualTo(book);
    }

    @Test
    void shouldReturnAnExistingReservationIfUserRequestedAndDoesOwnThisData() {
        UserDto user = findUserById(2L);
        BookDto book = findBookById(23L);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/reservations/2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ReservationDto returnedReservation = getReservationFromResponse(getResponse);
        assertThat(returnedReservation.getId()).isNotNull();
        assertThat(returnedReservation.getStartTime()).isNotNull();
        assertThat(returnedReservation.getEndTime()).isNotNull();
        assertThat(returnedReservation.getUser()).isEqualTo(user);
        assertThat(returnedReservation.getBook()).isEqualTo(book);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAnExistingReservationIfUserRequestedAndDoesNotOwnThisData(Long reservationId) {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/reservations/" + reservationId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "7", "10", "99999"
    })
    void shouldNotReturnReservationThatDoesNotExist(Long reservationId) {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/reservations/" + reservationId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotReturnAnExistingReservationIfUserIsNotAuthenticated(Long reservationId) {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/reservations/" + reservationId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldMakeAReservationIfUserIsAuthenticated() {
        ReservationToSaveDto reservationToSave = createPostRequestBody(2L, 67L);
        UserDto user = findUserById(2L);
        BookDto book = findBookById(67L);

        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .postForEntity("/api/v1/reservations",reservationToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI newlyCreatedReservationLocation = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity(newlyCreatedReservationLocation, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ReservationDto returnedReservation = getReservationFromResponse(getResponse);
        assertThat(returnedReservation.getId()).isNotNull();
        assertThat(returnedReservation.getStartTime()).isNotNull();
        assertThat(returnedReservation.getEndTime()).isNotNull();
        assertThat(returnedReservation.getUser()).isEqualTo(user);
        assertThat(returnedReservation.getBook()).isEqualTo(book);
    }

    @Test
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndBookIdDoesNotExist() {
        ReservationToSaveDto reservationToSave = createPostRequestBody(2L, 99999999L);
        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .postForEntity("/api/v1/reservations",reservationToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndUserIdDoesNotExist() {
        ReservationToSaveDto reservationToSave = createPostRequestBody(99999999L, 20L);
        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .postForEntity("/api/v1/reservations",reservationToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotMakeAReservationIfUserIsAuthenticatedButUserIdIsNotTheir(Long userId) {
        ReservationToSaveDto reservationToSave = createPostRequestBody(userId, 20L);
        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .postForEntity("/api/v1/reservations",reservationToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @CsvSource({
            "3", "4", "5", "7", "12", "16", "19", "23", "25"
    })
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndBookIsNotAvailable(Long bookId) {
        ReservationToSaveDto reservationToSave = createPostRequestBody(2L, bookId);
        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .postForEntity("/api/v1/reservations",reservationToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    void shouldNotMakeAReservationIfUserIsNotAuthenticated() {
        ReservationToSaveDto reservationToSave = createPostRequestBody(2L, 1L);
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",reservationToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest
    @DirtiesContext
    @Transactional
    @CsvSource({
            "1, 5",
            "2, 23",
            "3, 12",
            "4, 7",
            "5, 25",
            "6, 16"
    })
    void shouldDeleteAReservationIfAdminRequested(Long reservationId, Long bookId) {
        BookDto bookBefore = findBookById(bookId);
        assertThat(bookBefore.getAvailability()).isEqualTo(false);

        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/reservations/" + reservationId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        BookDto bookAfter = findBookById(bookId);
        assertThat(bookAfter.getAvailability()).isEqualTo(true);
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldDeleteAReservationIfUserRequestedAndReservationIdBelongsToTheir() {
        BookDto bookBefore = findBookById(23L);
        assertThat(bookBefore.getAvailability()).isEqualTo(false);

        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .exchange("/api/v1/reservations/2", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/reservations/2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        BookDto bookAfter = findBookById(23L);
        assertThat(bookAfter.getAvailability()).isEqualTo(true);
    }

    @Test
    void shouldNotDeleteAReservationIfUserRequestedAndReservationIdIsNotTheir() {
        ResponseEntity<String> getResponseBeforeDeleting = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/reservations/1", String.class);
        assertThat(getResponseBeforeDeleting.getStatusCode()).isEqualTo(HttpStatus.OK);
        ReservationDto reservationBeforeDeleting = getReservationFromResponse(getResponseBeforeDeleting);

        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .exchange("/api/v1/reservations/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        
        BookDto bookAfter = findBookById(5L);
        assertThat(bookAfter.getAvailability()).isEqualTo(false);
        
        ResponseEntity<String> getResponseAfterDeleting = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/reservations/1", String.class);
        assertThat(getResponseAfterDeleting.getStatusCode()).isEqualTo(HttpStatus.OK);
        ReservationDto reservationAfterDeleting = getReservationFromResponse(getResponseAfterDeleting);

        assertThat(reservationBeforeDeleting.getId()).isEqualTo(reservationAfterDeleting.getId());
        assertThat(reservationBeforeDeleting.getStartTime()).isEqualTo(reservationAfterDeleting.getStartTime());
        assertThat(reservationBeforeDeleting.getEndTime()).isEqualTo(reservationAfterDeleting.getEndTime());
        assertThat(reservationBeforeDeleting.getUser()).isEqualTo(reservationAfterDeleting.getUser());
        assertThat(reservationBeforeDeleting.getBook()).isEqualTo(reservationAfterDeleting.getBook());
    }

    @Test
    void shouldNotDeleteAReservationIfUserIsNotAuthenticated() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldNotDeleteAReservationThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .exchange("/api/v1/reservations/99999999", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private ReservationToSaveDto createPostRequestBody(Long userId, Long bookId) {
        ReservationToSaveDto dto = new ReservationToSaveDto();
        dto.setUserId(userId);
        dto.setBookId(bookId);
        return dto;
    }

    private ReservationDto getReservationFromResponse(ResponseEntity<String> response) {
        ReservationDto dto = new ReservationDto();
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        dto.setId(((Number)documentContext.read("$.id")).longValue());
        dto.setStartTime(LocalDateTime.parse(documentContext.read("$.startTime")) );
        dto.setEndTime(LocalDateTime.parse(documentContext.read("$.endTime")));

        LibraryCard card = new LibraryCard();
        card.setId(((Number) documentContext.read("$.user.card.id")).longValue());
        card.setBarcode(documentContext.read("$.user.card.barcode"));
        card.setIssuedAt(LocalDateTime.parse(documentContext.read("$.user.card.issuedAt")));
        card.setActive(documentContext.read("$.user.card.active"));

        UserDto user = new UserDto();
        user.setId(((Number)documentContext.read("$.user.id")).longValue());
        user.setFirstName(documentContext.read("$.user.firstName"));
        user.setLastName(documentContext.read("$.user.lastName"));
        user.setEmail(documentContext.read("$.user.email"));
        user.setCard(card);

        dto.setUser(user);

        BookDto book = new BookDto();

//        dto.setUserCard(card);
        book.setId(((Number)documentContext.read("$.book.id")).longValue());
        book.setTitle(documentContext.read("$.book.title"));
        book.setAuthor(documentContext.read("$.book.author"));
        book.setPublisher(documentContext.read("$.book.publisher"));
        book.setRelease_year(documentContext.read("$.book.release_year"));
        book.setPages(documentContext.read("$.book.pages"));
        book.setIsbn(documentContext.read("$.book.isbn"));

        dto.setBook(book);

        return dto;
    }

    private UserDto findUserById(Long userId) {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/users/" + userId, String.class);

        UserDto dto = new UserDto();
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

    private BookDto findBookById(Long bookId) {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/api/v1/books/" + bookId, String.class);

        BookDto dto = new BookDto();
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        dto.setId(((Number) documentContext.read("$.id")).longValue());
        dto.setTitle(documentContext.read("$.title"));
        dto.setAuthor(documentContext.read("$.author"));
        dto.setPublisher(documentContext.read("$.publisher"));
        dto.setRelease_year(documentContext.read("$.release_year"));
        dto.setPages(documentContext.read("$.pages"));
        dto.setIsbn(documentContext.read("$.isbn"));
        dto.setAvailability(documentContext.read("$.availability"));
        return dto;
    }

}
