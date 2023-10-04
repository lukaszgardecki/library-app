package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationRequest;
import com.example.libraryapp.domain.auth.AuthenticationResponse;
import com.example.libraryapp.domain.auth.AuthenticationService;
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
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ReservationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    @DirtiesContext
    void shouldReturnAllReservationsIfAdminRequested() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int allReservationsLength = documentContext.read("$._embedded.reservationDtoList.length()");
        assertThat(allReservationsLength).isEqualTo(6);

        getResponse = restTemplate
                .exchange("/api/v1/reservations?userId=5", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        documentContext = JsonPath.parse(getResponse.getBody());
        int usersReservationsLength = documentContext.read("$._embedded.reservationDtoList.length()");
        assertThat(usersReservationsLength).isEqualTo(1);
    }

    @Test
    @DirtiesContext
    void shouldReturnPageOf3ReservationsIfAdminRequested() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> response = restTemplate
                .exchange("/api/v1/reservations?page=1&size=3", HttpMethod.GET, request, String.class);
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
    @DirtiesContext
    void shouldReturnAllUsersReservationsIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations?userId=2", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAllUsersReservationsIfUserRequestedAndDoesNotOwnThisData(Long userId) {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations?userId=" + userId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotReturnAllUsersReservationsIfUserRequested() {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnAllReservationsIfUserIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/reservations", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        getResponse = restTemplate
                .getForEntity("/api/v1/reservations?userId=1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1, 5, 1",
            "2, 23, 2",
            "3, 12, 3",
            "4, 7, 4",
            "5, 25, 5",
            "6, 16, 6"
    })
    void shouldReturnAnExistingReservationIfAdminRequested(Long userId, Long bookId, Long reservationId) {
        HttpEntity<Object> request = createAdminRequest();

        UserDto user = findUserById(userId, request);
        BookDto book = findBookById(bookId);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ReservationDto returnedReservation = getReservationFromResponse(getResponse);
        assertThat(returnedReservation.getId()).isNotNull();
        assertThat(returnedReservation.getStartTime()).isNotNull();
        assertThat(returnedReservation.getEndTime()).isNotNull();
        assertThat(returnedReservation.getUser()).isEqualTo(user);
        assertThat(returnedReservation.getBook()).isEqualTo(book);
    }

    @Test
    @DirtiesContext
    void shouldReturnAnExistingReservationIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> adminRequest = createAdminRequest();
        HttpEntity<Object> userRequest = createUserRequest();
        UserDto user = findUserById(2L, adminRequest);
        BookDto book = findBookById(23L);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/2", HttpMethod.GET, userRequest, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ReservationDto returnedReservation = getReservationFromResponse(getResponse);
        assertThat(returnedReservation.getId()).isNotNull();
        assertThat(returnedReservation.getStartTime()).isNotNull();
        assertThat(returnedReservation.getEndTime()).isNotNull();
        assertThat(returnedReservation.getUser()).isEqualTo(user);
        assertThat(returnedReservation.getBook()).isEqualTo(book);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAnExistingReservationIfUserRequestedAndDoesNotOwnThisData(Long reservationId) {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "7", "10", "99999"
    })
    void shouldNotReturnReservationThatDoesNotExist(Long reservationId) {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotReturnAnExistingReservationIfUserIsNotAuthenticated(Long reservationId) {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/reservations/" + reservationId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldMakeAReservationIfUserIsAuthenticated() {
        ReservationToSaveDto reservationToSave = createPostRequestBody(2L, 67L);
        HttpEntity<Object> adminRequest = createAdminRequest();
        HttpEntity<Object> userRequest = createUserRequest(reservationToSave);
        UserDto user = findUserById(2L, adminRequest);
        BookDto book = findBookById(67L);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",userRequest, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI newlyCreatedReservationLocation = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .exchange(newlyCreatedReservationLocation, HttpMethod.GET, userRequest, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ReservationDto returnedReservation = getReservationFromResponse(getResponse);
        assertThat(returnedReservation.getId()).isNotNull();
        assertThat(returnedReservation.getStartTime()).isNotNull();
        assertThat(returnedReservation.getEndTime()).isNotNull();
        assertThat(returnedReservation.getUser()).isEqualTo(user);
        assertThat(returnedReservation.getBook()).isEqualTo(book);
    }

    @Test
    @DirtiesContext
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndBookIdDoesNotExist() {
        ReservationToSaveDto reservationToSave = createPostRequestBody(2L, 99999999L);
        HttpEntity<Object> request = createUserRequest(reservationToSave);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndUserIdDoesNotExist() {
        ReservationToSaveDto reservationToSave = createPostRequestBody(99999999L, 20L);
        HttpEntity<Object> request = createUserRequest(reservationToSave);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotMakeAReservationIfUserIsAuthenticatedButUserIdIsNotTheir(Long userId) {
        ReservationToSaveDto reservationToSave = createPostRequestBody(userId, 20L);
        HttpEntity<Object> request = createUserRequest(reservationToSave);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "3", "4", "5", "7", "12", "16", "19", "23", "25"
    })
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndBookIsNotAvailable(Long bookId) {
        ReservationToSaveDto reservationToSave = createPostRequestBody(2L, bookId);
        HttpEntity<Object> request = createUserRequest(reservationToSave);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    @DirtiesContext
    void shouldNotMakeAReservationIfUserIsNotAuthenticated() {
        ReservationToSaveDto reservationToSave = createPostRequestBody(2L, 1L);
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/reservations",reservationToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1, 5",
            "2, 23",
            "3, 12",
            "4, 7",
            "5, 25",
            "6, 16"
    })
    void shouldDeleteAReservationIfAdminRequested(Long reservationId, Long bookId) {
        HttpEntity<Object> request = createAdminRequest();

        BookDto bookBefore = findBookById(bookId);
        assertThat(bookBefore.getAvailability()).isEqualTo(false);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        BookDto bookAfter = findBookById(bookId);
        assertThat(bookAfter.getAvailability()).isEqualTo(true);
    }

    @Test
    @DirtiesContext
    void shouldDeleteAReservationIfUserRequestedAndReservationIdBelongsToTheir() {
        HttpEntity<Object> request = createUserRequest();

        BookDto bookBefore = findBookById(23L);
        assertThat(bookBefore.getAvailability()).isEqualTo(false);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations/2", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/reservations/2", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        BookDto bookAfter = findBookById(23L);
        assertThat(bookAfter.getAvailability()).isEqualTo(true);
    }

    @Test
    @DirtiesContext
    void shouldNotDeleteAReservationIfUserRequestedAndReservationIdIsNotTheir() {
        HttpEntity<Object> adminRequest = createAdminRequest();
        HttpEntity<Object> userRequest = createUserRequest();

        ResponseEntity<String> getResponseBeforeDeleting = restTemplate
                .exchange("/api/v1/reservations/1", HttpMethod.GET, adminRequest, String.class);
        assertThat(getResponseBeforeDeleting.getStatusCode()).isEqualTo(HttpStatus.OK);
        ReservationDto reservationBeforeDeleting = getReservationFromResponse(getResponseBeforeDeleting);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations/1", HttpMethod.DELETE, userRequest, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        
        BookDto bookAfter = findBookById(5L);
        assertThat(bookAfter.getAvailability()).isEqualTo(false);
        
        ResponseEntity<String> getResponseAfterDeleting = restTemplate
                .exchange("/api/v1/reservations/1", HttpMethod.GET, adminRequest, String.class);
        assertThat(getResponseAfterDeleting.getStatusCode()).isEqualTo(HttpStatus.OK);
        ReservationDto reservationAfterDeleting = getReservationFromResponse(getResponseAfterDeleting);

        assertThat(reservationBeforeDeleting.getId()).isEqualTo(reservationAfterDeleting.getId());
        assertThat(reservationBeforeDeleting.getStartTime()).isEqualTo(reservationAfterDeleting.getStartTime());
        assertThat(reservationBeforeDeleting.getEndTime()).isEqualTo(reservationAfterDeleting.getEndTime());
        assertThat(reservationBeforeDeleting.getUser()).isEqualTo(reservationAfterDeleting.getUser());
        assertThat(reservationBeforeDeleting.getBook()).isEqualTo(reservationAfterDeleting.getBook());
    }

    @Test
    @DirtiesContext
    void shouldNotDeleteAReservationIfUserIsNotAuthenticated() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldNotDeleteAReservationThatDoesNotExist() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/reservations/99999999", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private ReservationToSaveDto createPostRequestBody(Long userId, Long bookId) {
        ReservationToSaveDto reservationToSave = new ReservationToSaveDto();
        reservationToSave.setUserId(userId);
        reservationToSave.setBookId(bookId);
        return reservationToSave;
    }

    private ReservationDto getReservationFromResponse(ResponseEntity<String> response) {
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        ReservationDto reservation = new ReservationDto();
        UserDto user = parseUserDto(documentContext);
        BookDto book = parseBookDto(documentContext);
        reservation.setId(((Number)documentContext.read("$.id")).longValue());
        reservation.setStartTime(LocalDateTime.parse(documentContext.read("$.startTime")) );
        reservation.setEndTime(LocalDateTime.parse(documentContext.read("$.endTime")));
        reservation.setUser(user);
        reservation.setBook(book);
        return reservation;
    }

    private static BookDto parseBookDto(DocumentContext documentContext) {
        BookDto book = new BookDto();
        book.setId(((Number) documentContext.read("$.book.id")).longValue());
        book.setTitle(documentContext.read("$.book.title"));
        book.setAuthor(documentContext.read("$.book.author"));
        book.setPublisher(documentContext.read("$.book.publisher"));
        book.setRelease_year(documentContext.read("$.book.release_year"));
        book.setPages(documentContext.read("$.book.pages"));
        book.setIsbn(documentContext.read("$.book.isbn"));
        return book;
    }

    private static UserDto parseUserDto(DocumentContext documentContext) {
        LibraryCard card = new LibraryCard();
        card.setId(((Number) documentContext.read("$.user.card.id")).longValue());
        card.setBarcode(documentContext.read("$.user.card.barcode"));
        card.setIssuedAt(LocalDateTime.parse(documentContext.read("$.user.card.issuedAt")));
        card.setActive(documentContext.read("$.user.card.active"));

        UserDto user = new UserDto();
        user.setId(((Number) documentContext.read("$.user.id")).longValue());
        user.setFirstName(documentContext.read("$.user.firstName"));
        user.setLastName(documentContext.read("$.user.lastName"));
        user.setEmail(documentContext.read("$.user.email"));
        user.setCard(card);
        return user;
    }

    private UserDto findUserById(Long userId, HttpEntity<Object> request) {
        ResponseEntity<String> response = restTemplate
                .exchange("/api/v1/users/" + userId, HttpMethod.GET, request, String.class);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        UserDto user = new UserDto();
        user.setId(((Number) documentContext.read("$.id")).longValue());
        user.setFirstName(documentContext.read("$.firstName"));
        user.setLastName(documentContext.read("$.lastName"));
        user.setEmail(documentContext.read("$.email"));

        LibraryCard card = new LibraryCard();
        card.setId(((Number) documentContext.read("$.card.id")).longValue());
        card.setBarcode(documentContext.read("$.card.barcode"));
        card.setIssuedAt(LocalDateTime.parse(documentContext.read("$.card.issuedAt")));
        card.setActive(documentContext.read("$.card.active"));

        user.setCard(card);
        return user;
    }

    private BookDto findBookById(Long bookId) {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/api/v1/books/" + bookId, String.class);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        BookDto dto = new BookDto();
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

    private HttpEntity<Object> createAdminRequest() {
        AuthenticationRequest admin = new AuthenticationRequest();
        admin.setUsername("admin@example.com");
        admin.setPassword("adminpass");

        HttpHeaders headers = createHeaderWithTokenFor(admin);
        return new HttpEntity<>(headers);
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
