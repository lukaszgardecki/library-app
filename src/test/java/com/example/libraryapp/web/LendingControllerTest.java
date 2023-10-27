package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationRequest;
import com.example.libraryapp.domain.auth.AuthenticationResponse;
import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.lending.LendingDto;
import com.example.libraryapp.domain.lending.LendingToSaveDto;
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
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LendingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    @DirtiesContext
    void shouldReturnAllCheckoutsIfAdminRequested() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int allCheckoutsLength = documentContext.read("$._embedded.lendingDtoList.length()");
        assertThat(allCheckoutsLength).isEqualTo(6);

        getResponse = restTemplate
                .exchange("/api/v1/lendings?userId=1", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        documentContext = JsonPath.parse(getResponse.getBody());
        int usersCheckoutsLength = documentContext.read("$._embedded.lendingDtoList.length()");
        assertThat(usersCheckoutsLength).isEqualTo(3);
    }

    @Test
    @DirtiesContext
    void shouldReturnPageOf3CheckoutsIfAdminRequested() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> response = restTemplate
                .exchange("/api/v1/lendings?page=1&size=3", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.lendingDtoList.length()");
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
    void shouldReturnAllUsersCheckoutsIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings?userId=2", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAllUsersCheckoutsIfUserRequestedAndDoesNotOwnThisData(Long userId) {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings?userId=" + userId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotReturnAllUsersCheckoutsIfUserRequested() {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotReturnAllUsersCheckoutsIfUserIdDoesNotExist() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings?userId=99999999", HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnAllCheckoutsIfUserIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/lendings", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        getResponse = restTemplate
                .getForEntity("/api/v1/lendings?userId=1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1, 1, 1",
            "2, 2, 2",
            "1, 3, 3",
            "4, 4, 4",
            "1, 19, 5",
            "6, 6, 6"
    })
    void shouldReturnAnExistingCheckoutIfAdminRequested(Long userId, Long bookId, Long checkoutId) {
        HttpEntity<Object> request = createAdminRequest();
        UserDto user = findUserById(userId, request);
        BookDto book = findBookById(bookId, request);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings/" + checkoutId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        LendingDto returnedCheckout = getCheckoutFromResponse(getResponse);
        assertThat(returnedCheckout.getId()).isNotNull();
        assertThat(returnedCheckout.getStartTime()).isNotNull();
        assertThat(returnedCheckout.getEndTime()).isNotNull();
        assertThat(returnedCheckout.getUser()).isEqualTo(user);
        assertThat(returnedCheckout.getBook()).isEqualTo(book);
    }

    @Test
    @DirtiesContext
    void shouldReturnAnExistingCheckoutIfUserRequestedAndDoesOwnThisData() {
        HttpEntity<Object> userRequest = createUserRequest();
        HttpEntity<Object> adminRequest = createAdminRequest();
        UserDto user = findUserById(2L, adminRequest);
        BookDto book = findBookById(2L, adminRequest);

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings/2", HttpMethod.GET, userRequest, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        LendingDto returnedCheckout = getCheckoutFromResponse(getResponse);
        assertThat(returnedCheckout.getId()).isNotNull();
        assertThat(returnedCheckout.getStartTime()).isNotNull();
        assertThat(returnedCheckout.getEndTime()).isNotNull();
        assertThat(returnedCheckout.getUser()).isEqualTo(user);
        assertThat(returnedCheckout.getBook()).isEqualTo(book);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAnExistingCheckoutIfUserRequestedAndDoesNotOwnThisData(Long userId) {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings?userId=" + userId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "7", "10", "99999"
    })
    void shouldNotReturnCheckoutThatDoesNotExist(Long checkoutId) {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> getResponse = restTemplate
                .exchange("/api/v1/lendings/" + checkoutId, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotReturnAnExistingCheckoutIfUserIsNotAuthenticated(Long checkoutId) {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/lendings/" + checkoutId, String.class);
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
            "6, 16, 6",
    })
    void shouldBorrowABookIfAdminRequestedAndUserHasReservedABookEarlier(Long userId, Long bookId, Long reservationId) {
        LendingToSaveDto checkoutToSave = createPostRequestBody(userId, bookId);
        HttpEntity<Object> request = createAdminRequest(checkoutToSave);
        UserDto user = findUserById(userId, request);
        BookDto book = findBookById(bookId, request);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI newlyCreatedCheckoutLocation = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .exchange(newlyCreatedCheckoutLocation, HttpMethod.GET, request, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getReservationResponse = restTemplate
                .exchange("/api/v1/reservations/" + reservationId, HttpMethod.GET, request, String.class);
        assertThat(getReservationResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        LendingDto returnedCheckout = getCheckoutFromResponse(getResponse);
        assertThat(returnedCheckout.getId()).isNotNull();
        assertThat(returnedCheckout.getStartTime()).isNotNull();
        assertThat(returnedCheckout.getEndTime()).isNotNull();
        assertThat(returnedCheckout.getUser()).isEqualTo(user);
        assertThat(returnedCheckout.getBook()).isEqualTo(book);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1, 10",
            "2, 24",
            "3, 51",
            "4, 123",
            "5, 245",
            "6, 483",
    })
    void shouldNotBorrowABookIfAdminRequestedButUserHasNotReservedABookEarlier(Long userId, Long bookId) {
        LendingToSaveDto checkoutToSave = createPostRequestBody(userId, bookId);
        HttpEntity<?> request = createAdminRequest(checkoutToSave);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1, 3",
            "1, 19",
            "4, 4"
    })
    void shouldNotBorrowABookIfAdminRequestedAndUserHasNotReturnedOneYet(Long userId, Long bookId) {
        LendingToSaveDto checkoutToSave = createPostRequestBody(userId, bookId);
        HttpEntity<?> request = createAdminRequest(checkoutToSave);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotBorrowABookIfAdminRequestedAndBookIdDoesNotExist() {
        LendingToSaveDto checkoutToSave = createPostRequestBody(3L, 99999999L);
        HttpEntity<?> request = createAdminRequest(checkoutToSave);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotBorrowABookIfAdminRequestedAndUserIdDoesNotExist() {
        LendingToSaveDto checkoutToSave = createPostRequestBody(99999999L, 20L);
        HttpEntity<?> request = createAdminRequest(checkoutToSave);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotBorrowABookIfUserRequested() {
        LendingToSaveDto checkoutToSave = createPostRequestBody(3L, 30L);
        HttpEntity<?> request = createUserRequest(checkoutToSave);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldNotBorrowABookIfUserIsNotAuthenticated() {
        LendingToSaveDto checkoutToSave = createPostRequestBody(3L, 30L);
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/lendings", checkoutToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "3, 3",
            "4, 4",
            "19, 5"
    })
    void shouldAllowToReturnABookIfAdminRequestedAndBookIsBorrowedAlready(Long bookId, Long checkoutId) {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> getCheckoutResponse = restTemplate
                .exchange("/api/v1/lendings/" + checkoutId, HttpMethod.GET, request, String.class);
        assertThat(getCheckoutResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LendingDto checkout = getCheckoutFromResponse(getCheckoutResponse);

        ResponseEntity<String> patchResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookId=" + bookId, HttpMethod.PATCH, request, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getReturnedBookResponse = restTemplate.getForEntity("/api/v1/books/" + bookId, String.class);
        DocumentContext documentContext = JsonPath.parse(getReturnedBookResponse.getBody());
        assertThat(getReturnedBookResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat((boolean) documentContext.read("$.availability")).isEqualTo(true);

        LendingDto returnedCheckout = getCheckoutFromResponse(patchResponse);
        assertThat(returnedCheckout.getId()).isEqualTo(checkout.getId());
        assertThat(returnedCheckout.getStartTime()).isEqualTo(checkout.getStartTime());
        assertThat(returnedCheckout.getEndTime()).isEqualTo(checkout.getEndTime());
        assertThat(returnedCheckout.getUser()).isEqualTo(checkout.getUser());
        assertThat(returnedCheckout.getBook()).isEqualTo(checkout.getBook());
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "2", "6", "100", "200", "250", "300", "400"
    })
    void shouldNotAllowToReturnABookIfAdminRequestedAndBokIsReturnedAlready(Long bookId) {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> patchResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookId=" + bookId, HttpMethod.PATCH, request, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    @DirtiesContext
    void shouldNotAllowToReturnABookIfAdminRequestedAndBookIdIsDoesNotExist() {
        HttpEntity<Object> request = createAdminRequest();

        ResponseEntity<String> patchResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookId=99999999", HttpMethod.PATCH, request, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldNotAllowToReturnABookIfUserRequested() {
        HttpEntity<Object> request = createUserRequest();

        ResponseEntity<String> patchResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookId=3", HttpMethod.PATCH, request, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldNotAllowToReturnABookIfUserIsNotAuthenticated() {
        ResponseEntity<String> patchResponse = restTemplate
                .exchange("/api/v1/lendings/return?bookId=3", HttpMethod.PATCH, null, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private LendingToSaveDto createPostRequestBody(Long userId, Long bookId) {
        LendingToSaveDto checkoutToSave = new LendingToSaveDto();
        checkoutToSave.setUserId(userId);
        checkoutToSave.setBookId(bookId);
        return checkoutToSave;
    }

    private LendingDto getCheckoutFromResponse(ResponseEntity<String> response) {
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        LendingDto checkout = new LendingDto();
        UserDto user = parseUserDto(documentContext);
        BookDto book = parseBookDto(documentContext);
        checkout.setId(((Number) documentContext.read("$.id")).longValue());
        checkout.setStartTime(LocalDateTime.parse(documentContext.read("$.startTime")));
        checkout.setEndTime(LocalDateTime.parse(documentContext.read("$.endTime")));
        checkout.setUser(user);
        checkout.setBook(book);
        return checkout;
    }

    private UserDto parseUserDto(DocumentContext documentContext) {
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

    private BookDto parseBookDto(DocumentContext documentContext) {
        BookDto book = new BookDto();
        book.setId(((Number) documentContext.read("$.book.id")).longValue());
        book.setTitle(documentContext.read("$.book.title"));
        book.setAuthor(documentContext.read("$.book.author"));
        book.setPublisher(documentContext.read("$.book.publisher"));
        book.setRelease_year(documentContext.read("$.book.release_year"));
        book.setPages(documentContext.read("$.book.pages"));
        book.setIsbn(documentContext.read("$.book.isbn"));
        book.setAvailability(documentContext.read("$.book.availability"));
        return book;
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

    private BookDto findBookById(Long bookId, HttpEntity<Object> request) {
        ResponseEntity<String> response = restTemplate
                .exchange("/api/v1/books/" + bookId, HttpMethod.GET, request, String.class);

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
