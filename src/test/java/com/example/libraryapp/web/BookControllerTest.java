package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.auth.LoginRequest;
import com.example.libraryapp.domain.auth.LoginResponse;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
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

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BookControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void shouldReturnPageOf20BooksWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/books", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.bookDtoList.length()");
        assertThat(bookListLength).isEqualTo(20);
    }

    @Test
    void shouldReturnPageOf3BooksWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/books?page=0&size=3", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.bookDtoList.length()");
        assertThat(bookListLength).isEqualTo(3);
        int sizeParam = documentContext.read("$.page.size");
        assertThat(sizeParam).isEqualTo(3);
        int totalElementsParam = documentContext.read("$.page.totalElements");
        assertThat(totalElementsParam).isEqualTo(492);
        int totalPagesParam = documentContext.read("$.page.totalPages");
        assertThat(totalPagesParam).isEqualTo(164);
        int numberParam = documentContext.read("$.page.number");
        assertThat(numberParam).isEqualTo(0);
    }

    @Test
    void shouldReturnAnExistingBook() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/api/v1/books/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookDto returnedBook = getBookDtoFromResponse(response);
        assertThat(returnedBook.getId()).isEqualTo(1);
        assertThat(returnedBook.getTitle()).isEqualTo("Araya");
        assertThat(returnedBook.getSubject()).isEqualTo("White Plains");
        assertThat(returnedBook.getPublisher()).isEqualTo("Xena Hallut");
        assertThat(returnedBook.getLanguage()).isEqualTo("Hungarian");
        assertThat(returnedBook.getPages()).isEqualTo(195);
        assertThat(returnedBook.getISBN()).isEqualTo("460302346-4");
    }

    @Test
    void shouldNotReturnABookThatDoesNotExist() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/books/9999999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewBookIfAdminRequested() {
        BookToSaveDto bookToSaveDto = getBookToSaveDto();
        HttpEntity<?> request = createAdminRequest(bookToSaveDto);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/books", request, String.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewBook = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity(locationOfNewBook, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookDto returnedBook = getBookDtoFromResponse(getResponse);
        assertThat(returnedBook.getId()).isNotNull();
        assertThat(returnedBook.getTitle()).isEqualTo("Test title");
        assertThat(returnedBook.getSubject()).isEqualTo("Test subject");
        assertThat(returnedBook.getPublisher()).isEqualTo("Test publisher");
        assertThat(returnedBook.getLanguage()).isEqualTo("Test language");
        assertThat(returnedBook.getPages()).isEqualTo(123);
        assertThat(returnedBook.getISBN()).isEqualTo("Test isbn");
    }

    @Test
    void shouldNotCreateANewBookIfUserRequested() {
        BookToSaveDto bookToSaveDto = getBookToSaveDto();
        HttpEntity<?> request = createUserRequest(bookToSaveDto);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/books", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotCreateANewBookIfUnauthenticatedUserRequested() {
        BookToSaveDto bookToSaveDto = getBookToSaveDto();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/books", bookToSaveDto, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingBookIfAdminRequested() {
        BookToSaveDto bookToReplace = getBookToSaveDto();
        HttpEntity<?> request = createAdminRequest(bookToReplace);

        ResponseEntity<String> responseBefore = restTemplate
                .exchange("/api/v1/books/3", HttpMethod.GET, request, String.class);
        assertThat(responseBefore.getStatusCode()).isEqualTo(HttpStatus.OK);
        BookDto bookItemBefore = getBookDtoFromResponse(responseBefore);

        ResponseEntity<String> responseAfter = restTemplate
                .exchange("/api/v1/books/3", HttpMethod.PUT, request, String.class);
        assertThat(responseAfter.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookDto bookItemAfter = getBookDtoFromResponse(responseAfter);
        assertThat(bookItemAfter.getId()).isEqualTo(bookItemBefore.getId());
        assertThat(bookItemAfter.getTitle()).isEqualTo("Test title");
        assertThat(bookItemAfter.getSubject()).isEqualTo("Test subject");
        assertThat(bookItemAfter.getPublisher()).isEqualTo("Test publisher");
        assertThat(bookItemAfter.getLanguage()).isEqualTo("Test language");
        assertThat(bookItemAfter.getPages()).isEqualTo(123);
        assertThat(bookItemAfter.getISBN()).isEqualTo("Test isbn");
    }

    @Test
    void shouldNotUpdateAnExistingBookIfUserRequested() {
        BookToSaveDto bookToUpdate = getBookToSaveDto();
        HttpEntity<?> request = createUserRequest(bookToUpdate);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/books/3", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotUpdateAnExistingBookIfUnauthenticatedUserRequested() {
        BookToSaveDto bookToUpdate = getBookToSaveDto();
        HttpEntity<BookToSaveDto> request = new HttpEntity<>(bookToUpdate);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/books/3", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldNotUpdateABookThatDoesNotExist() {
        BookToSaveDto bookToUpdate = getBookToSaveDto();
        HttpEntity<?> request = createAdminRequest(bookToUpdate);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/books/999999999", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldPartiallyUpdateAnExistingBookIfAdminRequested(Long bookId) {
        ResponseEntity<String> getResponseBeforeUpdate = restTemplate
                .getForEntity("/api/v1/books/" + bookId, String.class);
        assertThat(getResponseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        BookDto bookBeforeUpdate = getBookDtoFromResponse(getResponseBeforeUpdate);

        BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<Object> request = createAdminRequest(bookFieldsToUpdate);

        ResponseEntity<String> patchResponse = restTemplate
                .exchange("/api/v1/books/" + bookId, HttpMethod.PATCH, request, String.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/books/" + bookId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookDto bookAfterUpdate = getBookDtoFromResponse(getResponse);
        assertThat(bookAfterUpdate.getTitle()).isEqualTo(bookFieldsToUpdate.getTitle());
        assertThat(bookAfterUpdate.getPages()).isEqualTo(bookFieldsToUpdate.getPages());
        assertThat(bookAfterUpdate.getPublisher()).isEqualTo(bookFieldsToUpdate.getPublisher());

        assertThat(bookAfterUpdate.getId()).isEqualTo(bookBeforeUpdate.getId());
        assertThat(bookAfterUpdate.getSubject()).isEqualTo(bookBeforeUpdate.getSubject());
        assertThat(bookAfterUpdate.getLanguage()).isEqualTo(bookBeforeUpdate.getLanguage());
        assertThat(bookAfterUpdate.getISBN()).isEqualTo(bookBeforeUpdate.getISBN());
    }

    @ParameterizedTest
    @DirtiesContext
    @Transactional
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotPartiallyUpdateAnExistingBookIfUserRequested(Long bookId) {
        BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<?> request = createUserRequest(bookFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/books/" + bookId, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotPartiallyUpdateAnExistingBookIfUnauthorizedUserRequested(Long bookId) {
        BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<BookDto> request = new HttpEntity<>(bookFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/books/" + bookId, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldNotPartiallyUpdateABookThatDoesNotExist() {
        BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<?> request = createAdminRequest(bookFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/books/99999", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldDeleteAnExistingBookIfAdminRequested() {
        HttpEntity<?> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/books/1", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/books/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotDeleteAnExistingBookIfUserRequested(Long bookId) {
        HttpEntity<?> request = createUserRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/books/" + bookId, HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldNotDeleteBookThatDoesNotExist() {
        HttpEntity<?> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/books/999999", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotDeleteBookIfUnauthenticatedUserRequested() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/books/4", HttpMethod.DELETE, null, Void.class);
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


    private BookToSaveDto getBookToSaveDto() {
        BookToSaveDto bookToSaveDto = new BookToSaveDto();
        bookToSaveDto.setTitle("Test title");
        bookToSaveDto.setSubject("Test subject");
        bookToSaveDto.setPublisher("Test publisher");
        bookToSaveDto.setLanguage("Test language");
        bookToSaveDto.setPages(123);
        bookToSaveDto.setISBN("Test isbn");
        return bookToSaveDto;
    }

    private BookDto getBookItemDtoToPartialUpdate() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test title");
        bookDto.setPages(10);
        bookDto.setPublisher("Test publisher");
        return bookDto;
    }

    private BookDto getBookDtoFromResponse(ResponseEntity<String> response) {
        BookDto dto = new BookDto();
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        dto.setId(((Number) documentContext.read("$.id")).longValue());
        dto.setTitle(documentContext.read("$.title"));
        dto.setSubject(documentContext.read("$.subject"));
        dto.setPublisher(documentContext.read("$.publisher"));
        dto.setLanguage(documentContext.read("$.language"));
        dto.setPages(documentContext.read("$.pages"));
        dto.setISBN(documentContext.read("$.isbn"));
        return dto;
    }
}
