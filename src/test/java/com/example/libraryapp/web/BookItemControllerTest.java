package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.auth.LoginRequest;
import com.example.libraryapp.domain.auth.LoginResponse;
import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.mapper.BookMapper;
import com.example.libraryapp.domain.bookItem.BookItemFormat;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.bookItem.dto.BookItemToSaveDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BookItemControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void shouldReturnPageOf20BookItemsWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/book-items", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.bookItemDtoList.length()");
        assertThat(bookListLength).isEqualTo(7);
    }

    @Test
    void shouldReturnPageOf3BookItemsWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/book-items?page=0&size=3", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.bookItemDtoList.length()");
        assertThat(bookListLength).isEqualTo(3);
        int sizeParam = documentContext.read("$.page.size");
        assertThat(sizeParam).isEqualTo(3);
        int totalElementsParam = documentContext.read("$.page.totalElements");
        assertThat(totalElementsParam).isEqualTo(7);
        int totalPagesParam = documentContext.read("$.page.totalPages");
        assertThat(totalPagesParam).isEqualTo(3);
        int numberParam = documentContext.read("$.page.number");
        assertThat(numberParam).isEqualTo(0);
    }

    @Test
    void shouldReturnAnExistingBookItem() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/api/v1/book-items/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookItemDto returnedBook = getBookItemDtoFromResponse(response);
        assertThat(returnedBook.getId()).isEqualTo(1);
        assertThat(returnedBook.getBarcode()).isEqualTo("540200000001");
        assertThat(returnedBook.getIsReferenceOnly()).isEqualTo(true);
        assertThat(returnedBook.getBorrowed()).isEqualTo("2023-05-21");
        assertThat(returnedBook.getDueDate()).isEqualTo("2023-06-10");
        assertThat(returnedBook.getPrice()).isEqualTo(BigDecimal.valueOf(12.34));
        assertThat(returnedBook.getFormat()).isEqualTo(BookItemFormat.MAGAZINE);
        assertThat(returnedBook.getStatus()).isEqualTo(BookItemStatus.AVAILABLE);
        assertThat(returnedBook.getDateOfPurchase()).isEqualTo("2023-01-28");
        assertThat(returnedBook.getPublicationDate()).isEqualTo("2023-02-28");
        assertThat(returnedBook.getBook().getId()).isEqualTo(1);
        assertThat(returnedBook.getBook().getTitle()).isEqualTo("Araya");
        assertThat(returnedBook.getBook().getSubject()).isEqualTo("White Plains");
        assertThat(returnedBook.getBook().getPublisher()).isEqualTo("Xena Hallut");
        assertThat(returnedBook.getBook().getLanguage()).isEqualTo("Hungarian");
        assertThat(returnedBook.getBook().getPages()).isEqualTo(195);
        assertThat(returnedBook.getBook().getISBN()).isEqualTo("460302346-4");
    }

    @Test
    void shouldNotReturnABookItemThatDoesNotExist() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/book-items/99999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewBookItemIfAdminRequested() {
        BookItemToSaveDto bookToSaveDto = getBookToSaveDto();
        HttpEntity<?> request = createAdminRequest(bookToSaveDto);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/book-items", request, String.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewBook = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity(locationOfNewBook, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookItemDto returnedBook = getBookItemDtoFromResponse(getResponse);
        assertThat(returnedBook.getId()).isNotNull();
        assertThat(returnedBook.getBarcode()).isNotNull();
        assertThat(returnedBook.getStatus()).isNotNull();
        assertThat(returnedBook.getIsReferenceOnly()).isEqualTo(bookToSaveDto.getIsReferenceOnly());
        assertThat(returnedBook.getPrice()).isEqualTo(bookToSaveDto.getPrice());
        assertThat(returnedBook.getFormat()).isEqualTo(bookToSaveDto.getFormat());
        assertThat(returnedBook.getDateOfPurchase()).isEqualTo(bookToSaveDto.getDateOfPurchase());
        assertThat(returnedBook.getPublicationDate()).isEqualTo(bookToSaveDto.getPublicationDate());
        assertThat(returnedBook.getBook().getId()).isEqualTo(1);
        assertThat(returnedBook.getBook().getTitle()).isEqualTo("Araya");
        assertThat(returnedBook.getBook().getSubject()).isEqualTo("White Plains");
        assertThat(returnedBook.getBook().getPublisher()).isEqualTo("Xena Hallut");
        assertThat(returnedBook.getBook().getLanguage()).isEqualTo("Hungarian");
        assertThat(returnedBook.getBook().getPages()).isEqualTo(195);
        assertThat(returnedBook.getBook().getISBN()).isEqualTo("460302346-4");
    }

    @Test
    void shouldNotCreateANewBookItemIfUserRequested() {
        BookItemToSaveDto bookToSaveDto = getBookToSaveDto();
        HttpEntity<?> request = createUserRequest(bookToSaveDto);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/book-items", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotCreateANewBookItemIfUnauthenticatedUserRequested() {
        BookItemToSaveDto bookToSaveDto = getBookToSaveDto();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/book-items", bookToSaveDto, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingBookItemIfAdminRequested() {
        BookItemToSaveDto bookItemToReplace = getBookToSaveDto();
        HttpEntity<?> request = createAdminRequest(bookItemToReplace);

        ResponseEntity<String> responseBefore = restTemplate
                .exchange("/api/v1/book-items/3", HttpMethod.GET, request, String.class);
        assertThat(responseBefore.getStatusCode()).isEqualTo(HttpStatus.OK);
        BookItemDto bookItemBefore = getBookItemDtoFromResponse(responseBefore);

        ResponseEntity<String> responseAfter = restTemplate
                .exchange("/api/v1/book-items/3", HttpMethod.PUT, request, String.class);
        assertThat(responseAfter.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookItemDto bookItemAfter = getBookItemDtoFromResponse(responseAfter);
        assertThat(bookItemAfter.getId()).isEqualTo(bookItemBefore.getId());
        assertThat(bookItemAfter.getBarcode()).isEqualTo(bookItemBefore.getBarcode());
        assertThat(bookItemAfter.getStatus()).isEqualTo(bookItemBefore.getStatus());

        assertThat(bookItemAfter.getIsReferenceOnly()).isEqualTo(bookItemToReplace.getIsReferenceOnly());
        assertThat(bookItemAfter.getPrice()).isEqualTo(bookItemToReplace.getPrice());
        assertThat(bookItemAfter.getFormat()).isEqualTo(bookItemToReplace.getFormat());
        assertThat(bookItemAfter.getDateOfPurchase()).isEqualTo(bookItemToReplace.getDateOfPurchase());
        assertThat(bookItemAfter.getPublicationDate()).isEqualTo(bookItemToReplace.getPublicationDate());
        assertThat(bookItemAfter.getBook().getId()).isEqualTo(1);
        assertThat(bookItemAfter.getBook().getTitle()).isEqualTo("Araya");
        assertThat(bookItemAfter.getBook().getSubject()).isEqualTo("White Plains");
        assertThat(bookItemAfter.getBook().getPublisher()).isEqualTo("Xena Hallut");
        assertThat(bookItemAfter.getBook().getLanguage()).isEqualTo("Hungarian");
        assertThat(bookItemAfter.getBook().getPages()).isEqualTo(195);
        assertThat(bookItemAfter.getBook().getISBN()).isEqualTo("460302346-4");
    }

    @Test
    void shouldNotUpdateAnExistingBookItemIfUserRequested() {
        BookItemToSaveDto bookToUpdate = getBookToSaveDto();
        HttpEntity<?> request = createUserRequest(bookToUpdate);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/book-items/3", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotUpdateAnExistingBookItemIfUnauthenticatedUserRequested() {
        BookItemToSaveDto bookToUpdate = getBookToSaveDto();
        HttpEntity<BookItemToSaveDto> request = new HttpEntity<>(bookToUpdate);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/book-items/3", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldNotUpdateABookItemThatDoesNotExist() {
        BookItemToSaveDto bookToUpdate = getBookToSaveDto();
        HttpEntity<?> request = createAdminRequest(bookToUpdate);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/book-items/999999999", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldPartiallyUpdateAnExistingBookItemIfAdminRequested(Long bookItemId) {
        ResponseEntity<String> getResponseBeforeUpdate = restTemplate
                .getForEntity("/api/v1/book-items/" + bookItemId, String.class);
        assertThat(getResponseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        BookItemDto bookBeforeUpdate = getBookItemDtoFromResponse(getResponseBeforeUpdate);

        BookItemDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<Object> request = createAdminRequest(bookFieldsToUpdate);

        ResponseEntity<String> patchResponse = restTemplate
                .exchange("/api/v1/book-items/" + bookItemId, HttpMethod.PATCH, request, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/book-items/" + bookItemId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookItemDto bookAfterUpdate = getBookItemDtoFromResponse(getResponse);
        assertThat(bookAfterUpdate.getPrice()).isEqualTo(bookFieldsToUpdate.getPrice());
        assertThat(bookAfterUpdate.getFormat()).isEqualTo(bookFieldsToUpdate.getFormat());
        assertThat(bookAfterUpdate.getDateOfPurchase()).isEqualTo(bookFieldsToUpdate.getDateOfPurchase());

        assertThat(bookAfterUpdate.getId()).isEqualTo(bookBeforeUpdate.getId());
        assertThat(bookAfterUpdate.getBarcode()).isEqualTo(bookBeforeUpdate.getBarcode());
        assertThat(bookAfterUpdate.getStatus()).isEqualTo(bookBeforeUpdate.getStatus());
        assertThat(bookAfterUpdate.getIsReferenceOnly()).isEqualTo(bookBeforeUpdate.getIsReferenceOnly());
        assertThat(bookAfterUpdate.getPublicationDate()).isEqualTo(bookBeforeUpdate.getPublicationDate());
        assertThat(bookAfterUpdate.getBook().getId()).isEqualTo(bookBeforeUpdate.getBook().getId());
        assertThat(bookAfterUpdate.getBook().getTitle()).isEqualTo(bookBeforeUpdate.getBook().getTitle());
        assertThat(bookAfterUpdate.getBook().getSubject()).isEqualTo(bookBeforeUpdate.getBook().getSubject());
        assertThat(bookAfterUpdate.getBook().getPublisher()).isEqualTo(bookBeforeUpdate.getBook().getPublisher());
        assertThat(bookAfterUpdate.getBook().getLanguage()).isEqualTo(bookBeforeUpdate.getBook().getLanguage());
        assertThat(bookAfterUpdate.getBook().getPages()).isEqualTo(bookBeforeUpdate.getBook().getPages());
        assertThat(bookAfterUpdate.getBook().getISBN()).isEqualTo(bookBeforeUpdate.getBook().getISBN());
    }

    @ParameterizedTest
    @DirtiesContext
    @Transactional
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotPartiallyUpdateAnExistingBookItemIfUserRequested(Long bookItemId) {
        BookItemDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<?> request = createUserRequest(bookFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/book-items/" + bookItemId, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotPartiallyUpdateAnExistingBookItemIfUnauthorizedUserRequested(Long bookItemId) {
        BookItemDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<BookItemDto> request = new HttpEntity<>(bookFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/book-items/" + bookItemId, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldNotPartiallyUpdateABookItemThatDoesNotExist() {
        BookItemDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<?> request = createAdminRequest(bookFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/book-items/99999", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldDeleteAnExistingBookItemIfAdminRequested() {
        HttpEntity<?> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/book-items/1", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/book-items/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotDeleteAnExistingBookItemIfUserRequested(Long bookItemId) {
        HttpEntity<?> request = createUserRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/book-items/" + bookItemId, HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "3", "4", "5", "7"
    })
    void shouldNotDeleteAnExistingBookItemIfBookItemIsAlreadyReservedOrNotReturned(Long bookItemId) {
        HttpEntity<?> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/book-items/" + bookItemId, HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @DirtiesContext
    void shouldNotDeleteBookItemThatDoesNotExist() {
        HttpEntity<?> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/book-items/999999", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotDeleteBookItemIfUnauthenticatedUserRequested() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/book-items/4", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
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

    private HttpHeaders createHeaderWithTokenFor(LoginRequest user) {
        LoginResponse response = authenticationService.authenticate(user);
        String token = response.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


    private BookItemToSaveDto getBookToSaveDto() {
        BookItemToSaveDto bookToSaveDto = new BookItemToSaveDto();
        bookToSaveDto.setIsReferenceOnly(false);
        bookToSaveDto.setPrice(BigDecimal.valueOf(12.34));
        bookToSaveDto.setFormat(BookItemFormat.JOURNAL);
        bookToSaveDto.setDateOfPurchase(LocalDate.parse("05-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        bookToSaveDto.setPublicationDate(LocalDate.parse("13-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        bookToSaveDto.setBookId(1L);
        return bookToSaveDto;
    }

    private BookItemDto getBookItemDtoToPartialUpdate() {
        BookItemDto bookDto = new BookItemDto();
        bookDto.setPrice(BigDecimal.valueOf(34.45));
        bookDto.setFormat(BookItemFormat.AUDIO_BOOK);
        bookDto.setDateOfPurchase(LocalDate.parse("01-10-1993", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return bookDto;
    }

    private BookItemDto getBookItemDtoFromResponse(ResponseEntity<String> response) {
        BookItemDto dto = new BookItemDto();
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        dto.setId(((Number) documentContext.read("$.id")).longValue());
        dto.setBarcode(documentContext.read("$.barcode"));
        dto.setIsReferenceOnly(documentContext.read("$.isReferenceOnly"));
        String borrowed = documentContext.read("$.borrowed");
        dto.setBorrowed(borrowed != null ? LocalDate.parse(borrowed, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null);
        String dueDate = documentContext.read("$.dueDate");
        dto.setDueDate(dueDate != null ? LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null);
        dto.setPrice(BigDecimal.valueOf(((Number) documentContext.read("$.price")).doubleValue()));

        dto.setFormat(BookItemFormat.valueOf(documentContext.read("$.format")));
        dto.setStatus(BookItemStatus.valueOf(documentContext.read("$.status")));
        dto.setDateOfPurchase(LocalDate.parse(documentContext.read("$.dateOfPurchase"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setPublicationDate(LocalDate.parse(documentContext.read("$.publicationDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Book book = new Book();
        book.setId(((Number) documentContext.read("$.book.id")).longValue());
        book.setTitle(documentContext.read("$.book.title"));
        book.setSubject(documentContext.read("$.book.subject"));
        book.setPublisher(documentContext.read("$.book.publisher"));
        book.setLanguage(documentContext.read("$.book.language"));
        book.setPages(documentContext.read("$.book.pages"));
        book.setISBN(documentContext.read("$.book.isbn"));

        dto.setBook(BookMapper.map(book));
        return dto;
    }
}
