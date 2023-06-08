package com.example.libraryapp.web;

import com.example.libraryapp.domain.book.dto.BookDto;
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
        assertThat(returnedCheckout.getUserId()).isEqualTo(user.getId());
        assertThat(returnedCheckout.getUserFirstName()).isEqualTo(user.getFirstName());
        assertThat(returnedCheckout.getUserLastName()).isEqualTo(user.getLastName());
        assertThat(returnedCheckout.getUserEmail()).isEqualTo(user.getEmail());
        assertThat(returnedCheckout.getUserCardNumber()).isEqualTo(user.getCardNumber());
        assertThat(returnedCheckout.getBookId()).isEqualTo(book.getId());
        assertThat(returnedCheckout.getBookTitle()).isEqualTo(book.getTitle());
        assertThat(returnedCheckout.getBookAuthor()).isEqualTo(book.getAuthor());
        assertThat(returnedCheckout.getBookPublisher()).isEqualTo(book.getPublisher());
        assertThat(returnedCheckout.getBookReleaseYear()).isEqualTo(book.getRelease_year());
        assertThat(returnedCheckout.getBookPages()).isEqualTo(book.getPages());
        assertThat(returnedCheckout.getBookIsbn()).isEqualTo(book.getIsbn());
        assertThat(returnedCheckout.getIsReturned()).isEqualTo(book.getAvailability());
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
        assertThat(returnedCheckout.getUserId()).isEqualTo(user.getId());
        assertThat(returnedCheckout.getUserFirstName()).isEqualTo(user.getFirstName());
        assertThat(returnedCheckout.getUserLastName()).isEqualTo(user.getLastName());
        assertThat(returnedCheckout.getUserEmail()).isEqualTo(user.getEmail());
        assertThat(returnedCheckout.getUserCardNumber()).isEqualTo(user.getCardNumber());
        assertThat(returnedCheckout.getBookId()).isEqualTo(book.getId());
        assertThat(returnedCheckout.getBookTitle()).isEqualTo(book.getTitle());
        assertThat(returnedCheckout.getBookAuthor()).isEqualTo(book.getAuthor());
        assertThat(returnedCheckout.getBookPublisher()).isEqualTo(book.getPublisher());
        assertThat(returnedCheckout.getBookReleaseYear()).isEqualTo(book.getRelease_year());
        assertThat(returnedCheckout.getBookPages()).isEqualTo(book.getPages());
        assertThat(returnedCheckout.getBookIsbn()).isEqualTo(book.getIsbn());
        assertThat(returnedCheckout.getIsReturned()).isEqualTo(true);
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
        assertThat(returnedCheckout.getUserId()).isEqualTo(user.getId());
        assertThat(returnedCheckout.getUserFirstName()).isEqualTo(user.getFirstName());
        assertThat(returnedCheckout.getUserLastName()).isEqualTo(user.getLastName());
        assertThat(returnedCheckout.getUserEmail()).isEqualTo(user.getEmail());
        assertThat(returnedCheckout.getUserCardNumber()).isEqualTo(user.getCardNumber());
        assertThat(returnedCheckout.getBookId()).isEqualTo(book.getId());
        assertThat(returnedCheckout.getBookTitle()).isEqualTo(book.getTitle());
        assertThat(returnedCheckout.getBookAuthor()).isEqualTo(book.getAuthor());
        assertThat(returnedCheckout.getBookPublisher()).isEqualTo(book.getPublisher());
        assertThat(returnedCheckout.getBookReleaseYear()).isEqualTo(book.getRelease_year());
        assertThat(returnedCheckout.getBookPages()).isEqualTo(book.getPages());
        assertThat(returnedCheckout.getBookIsbn()).isEqualTo(book.getIsbn());
        assertThat(returnedCheckout.getIsReturned()).isEqualTo(false);
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
        assertThat(returnedCheckout.getUserId()).isEqualTo(checkout.getUserId());
        assertThat(returnedCheckout.getUserFirstName()).isEqualTo(checkout.getUserFirstName());
        assertThat(returnedCheckout.getUserLastName()).isEqualTo(checkout.getUserLastName());
        assertThat(returnedCheckout.getUserEmail()).isEqualTo(checkout.getUserEmail());
        assertThat(returnedCheckout.getUserCardNumber()).isEqualTo(checkout.getUserCardNumber());
        assertThat(returnedCheckout.getBookId()).isEqualTo(checkout.getBookId());
        assertThat(returnedCheckout.getBookTitle()).isEqualTo(checkout.getBookTitle());
        assertThat(returnedCheckout.getBookAuthor()).isEqualTo(checkout.getBookAuthor());
        assertThat(returnedCheckout.getBookPublisher()).isEqualTo(checkout.getBookPublisher());
        assertThat(returnedCheckout.getBookReleaseYear()).isEqualTo(checkout.getBookReleaseYear());
        assertThat(returnedCheckout.getBookPages()).isEqualTo(checkout.getBookPages());
        assertThat(returnedCheckout.getBookIsbn()).isEqualTo(checkout.getBookIsbn());
        assertThat(returnedCheckout.getIsReturned()).isEqualTo(true);
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
        CheckoutToSaveDto dto = new CheckoutToSaveDto();
        dto.setUserId(userId);
        dto.setBookId(bookId);
        return dto;
    }

    private CheckoutDto getCheckoutFromResponse(ResponseEntity<String> response) {
        CheckoutDto dto = new CheckoutDto();
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        dto.setId(((Number)documentContext.read("$.id")).longValue());
        dto.setStartTime(LocalDateTime.parse(documentContext.read("$.startTime")) );
        dto.setEndTime(LocalDateTime.parse(documentContext.read("$.endTime")));
        dto.setUserId(((Number)documentContext.read("$.userId")).longValue());
        dto.setUserFirstName(documentContext.read("$.userFirstName"));
        dto.setUserLastName(documentContext.read("$.userLastName"));
        dto.setUserEmail(documentContext.read("$.userEmail"));
        dto.setUserCardNumber(documentContext.read("$.userCardNumber"));
        dto.setBookId(((Number)documentContext.read("$.bookId")).longValue());
        dto.setBookTitle(documentContext.read("$.bookTitle"));
        dto.setBookAuthor(documentContext.read("$.bookAuthor"));
        dto.setBookPublisher(documentContext.read("$.bookPublisher"));
        dto.setBookReleaseYear(documentContext.read("$.bookReleaseYear"));
        dto.setBookPages(documentContext.read("$.bookPages"));
        dto.setBookIsbn(documentContext.read("$.bookIsbn"));
        dto.setIsReturned(documentContext.read("$.isReturned"));
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
        dto.setCardNumber(documentContext.read("$.cardNumber"));
        return dto;
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
