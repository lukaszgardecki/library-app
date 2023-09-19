package com.example.libraryapp.web;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.checkout.CheckoutDto;
import com.example.libraryapp.domain.checkout.CheckoutToSaveDto;
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
public class CheckoutControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllCheckoutsIfAdminRequested() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/checkouts", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int allCheckoutsLength = documentContext.read("$._embedded.checkoutDtoList.length()");
        assertThat(allCheckoutsLength).isEqualTo(6);

        getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/checkouts?userId=1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        documentContext = JsonPath.parse(getResponse.getBody());
        int usersCheckoutsLength = documentContext.read("$._embedded.checkoutDtoList.length()");
        assertThat(usersCheckoutsLength).isEqualTo(3);
    }

    @Test
    void shouldReturnPageOf3CheckoutsIfAdminRequested() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/checkouts?page=1&size=3", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.checkoutDtoList.length()");
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
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/checkouts?userId=2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAllUsersCheckoutsIfUserRequestedAndDoesNotOwnThisData(Long userId) {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/checkouts?userId=" + userId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnAllUsersCheckoutsIfUserRequested() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/checkouts", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnAllUsersCheckoutsIfUserIdDoesNotExist() {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/checkouts?userId=99999999", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnAllCheckoutsIfUserIsNotAuthenticated() {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/checkouts", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        getResponse = restTemplate
                .getForEntity("/api/v1/checkouts?userId=1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 1",
            "2, 2, 2",
            "1, 3, 3",
            "4, 4, 4",
            "1, 19, 5",
            "6, 6, 6"
    })
    void shouldReturnAnExistingCheckoutIfAdminRequested(Long userId, Long bookId, Long checkoutId) {
        UserDto user = findUserById(userId);
        BookDto book = findBookById(bookId);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/checkouts/" + checkoutId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        CheckoutDto returnedCheckout = getCheckoutFromResponse(getResponse);
        assertThat(returnedCheckout.getId()).isNotNull();
        assertThat(returnedCheckout.getStartTime()).isNotNull();
        assertThat(returnedCheckout.getEndTime()).isNotNull();
        assertThat(returnedCheckout.getUser()).isEqualTo(user);
        assertThat(returnedCheckout.getBook()).isEqualTo(book);
    }

    @Test
    void shouldReturnAnExistingCheckoutIfUserRequestedAndDoesOwnThisData() {
        UserDto user = findUserById(2L);
        BookDto book = findBookById(2L);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/checkouts/2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        CheckoutDto returnedCheckout = getCheckoutFromResponse(getResponse);
        assertThat(returnedCheckout.getId()).isNotNull();
        assertThat(returnedCheckout.getStartTime()).isNotNull();
        assertThat(returnedCheckout.getEndTime()).isNotNull();
        assertThat(returnedCheckout.getUser()).isEqualTo(user);
        assertThat(returnedCheckout.getBook()).isEqualTo(book);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAnExistingCheckoutIfUserRequestedAndDoesNotOwnThisData(Long userId) {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getForEntity("/api/v1/checkouts/" + userId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "7", "10", "99999"
    })
    void shouldNotReturnCheckoutThatDoesNotExist(Long checkoutId) {
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/checkouts/" + checkoutId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotReturnAnExistingCheckoutIfUserIsNotAuthenticated(Long checkoutId) {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/checkouts/" + checkoutId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest
    @DirtiesContext
    @Transactional
    @CsvSource({
            "1, 5, 1",
            "2, 23, 2",
            "3, 12, 3",
            "4, 7, 4",
            "5, 25, 5",
            "6, 16, 6",
    })
    void shouldBorrowABookIfAdminRequestedAndUserHasReservedABookEarlier(Long userId, Long bookId, Long reservationId) {
        CheckoutToSaveDto checkoutToSave = createPostRequestBody(userId, bookId);
        UserDto user = findUserById(userId);
        BookDto book = findBookById(bookId);


        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .postForEntity("/api/v1/checkouts",checkoutToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI newlyCreatedCheckoutLocation = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity(newlyCreatedCheckoutLocation, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getReservationResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/reservations/" + reservationId, String.class);
        assertThat(getReservationResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        CheckoutDto returnedCheckout = getCheckoutFromResponse(getResponse);
        assertThat(returnedCheckout.getId()).isNotNull();
        assertThat(returnedCheckout.getStartTime()).isNotNull();
        assertThat(returnedCheckout.getEndTime()).isNotNull();
        assertThat(returnedCheckout.getUser()).isEqualTo(user);
        assertThat(returnedCheckout.getBook()).isEqualTo(book);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 10",
            "2, 24",
            "3, 51",
            "4, 123",
            "5, 245",
            "6, 483",
    })
    void shouldNotBorrowABookIfAdminRequestedButUserHasNotReservedABookEarlier(Long userId, Long bookId) {
        CheckoutToSaveDto checkoutToSave = createPostRequestBody(userId, bookId);
        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .postForEntity("/api/v1/checkouts",checkoutToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 3",
            "1, 19",
            "4, 4"
    })
    void shouldNotBorrowABookIfAdminRequestedAndUserHasNotReturnedOneYet(Long userId, Long bookId) {
        CheckoutToSaveDto checkoutToSave = createPostRequestBody(userId, bookId);
        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .postForEntity("/api/v1/checkouts",checkoutToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotBorrowABookIfAdminRequestedAndBookIdDoesNotExist() {
        CheckoutToSaveDto checkoutToSave = createPostRequestBody(3L, 99999999L);
        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .postForEntity("/api/v1/checkouts",checkoutToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotBorrowABookIfAdminRequestedAndUserIdDoesNotExist() {
        CheckoutToSaveDto checkoutToSave = createPostRequestBody(99999999L, 20L);
        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .postForEntity("/api/v1/checkouts",checkoutToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotBorrowABookIfUserRequested() {
        CheckoutToSaveDto checkoutToSave = createPostRequestBody(3L, 30L);
        ResponseEntity<String> createResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .postForEntity("/api/v1/checkouts",checkoutToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotBorrowABookIfUserIsNotAuthenticated() {
        CheckoutToSaveDto checkoutToSave = createPostRequestBody(3L, 30L);
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/checkouts",checkoutToSave, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest
    @DirtiesContext
    @Transactional
    @CsvSource({
            "3, 3",
            "4, 4",
            "19, 5"
    })
    void shouldAllowToReturnABookIfAdminRequestedAndBookIsBorrowedAlready(Long bookId, Long checkoutId) {
        ResponseEntity<String> getCheckoutResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/checkouts/" + checkoutId, String.class);
        assertThat(getCheckoutResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        CheckoutDto checkout = getCheckoutFromResponse(getCheckoutResponse);

        ResponseEntity<String> patchResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getRestTemplate()
                .exchange("/api/v1/checkouts/return?bookId=" + bookId, HttpMethod.PATCH, null, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getReturnedBookResponse = restTemplate.getForEntity("/api/v1/books/" + bookId, String.class);
        DocumentContext documentContext = JsonPath.parse(getReturnedBookResponse.getBody());
        assertThat(getReturnedBookResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat((boolean) documentContext.read("$.availability")).isEqualTo(true);

        CheckoutDto returnedCheckout = getCheckoutFromResponse(patchResponse);
        assertThat(returnedCheckout.getId()).isEqualTo(checkout.getId());
        assertThat(returnedCheckout.getStartTime()).isEqualTo(checkout.getStartTime());
        assertThat(returnedCheckout.getEndTime()).isEqualTo(checkout.getEndTime());
        assertThat(returnedCheckout.getUser()).isEqualTo(checkout.getUser());
        assertThat(returnedCheckout.getBook()).isEqualTo(checkout.getBook());
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "6", "100", "200", "250", "300", "400"
    })
    void shouldNotAllowToReturnABookIfAdminRequestedAndBokIsReturnedAlready(Long bookId) {
        ResponseEntity<String> patchResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getRestTemplate()
                .exchange("/api/v1/checkouts/return?bookId=" + bookId, HttpMethod.PATCH, null, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    void shouldNotAllowToReturnABookIfAdminRequestedAndBookIdIsDoesNotExist() {
        ResponseEntity<String> patchResponse = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getRestTemplate()
                .exchange("/api/v1/checkouts/return?bookId=99999999", HttpMethod.PATCH, null, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotAllowToReturnABookIfUserRequested() {
        ResponseEntity<String> patchResponse = restTemplate
                .withBasicAuth("user@example.com", "userpass")
                .getRestTemplate()
                .exchange("/api/v1/checkouts/return?bookId=3", HttpMethod.PATCH, null, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotAllowToReturnABookIfUserIsNotAuthenticated() {
        ResponseEntity<String> patchResponse = restTemplate
                .exchange("/api/v1/checkouts/return?bookId=3", HttpMethod.PATCH, null, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private CheckoutToSaveDto createPostRequestBody(Long userId, Long bookId) {
        CheckoutToSaveDto checkoutToSave = new CheckoutToSaveDto();
        checkoutToSave.setUserId(userId);
        checkoutToSave.setBookId(bookId);
        return checkoutToSave;
    }

    private CheckoutDto getCheckoutFromResponse(ResponseEntity<String> response) {
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        CheckoutDto checkout = new CheckoutDto();
        UserDto user = parseUserDto(documentContext);
        BookDto book = parseBookDto(documentContext);
        checkout.setId(((Number)documentContext.read("$.id")).longValue());
        checkout.setStartTime(LocalDateTime.parse(documentContext.read("$.startTime")) );
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

    private UserDto findUserById(Long userId) {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("admin@example.com", "adminpass")
                .getForEntity("/api/v1/users/" + userId, String.class);

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
                .withBasicAuth("admin@example.com", "adminpass")
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
