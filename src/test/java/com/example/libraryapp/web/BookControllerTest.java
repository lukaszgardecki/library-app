package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationRequest;
import com.example.libraryapp.domain.auth.AuthenticationResponse;
import com.example.libraryapp.domain.auth.AuthenticationService;
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
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/books?page=5&size=3", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.bookDtoList.length()");
        assertThat(bookListLength).isEqualTo(3);
        int sizeParam = documentContext.read("$.page.size");
        assertThat(sizeParam).isEqualTo(3);
        int totalElementsParam = documentContext.read("$.page.totalElements");
        assertThat(totalElementsParam).isEqualTo(500);
        int totalPagesParam = documentContext.read("$.page.totalPages");
        assertThat(totalPagesParam).isEqualTo(167);
        int numberParam = documentContext.read("$.page.number");
        assertThat(numberParam).isEqualTo(5);
    }

    @Test
    void shouldReturnAnExistingBook() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/api/v1/books/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookDto returnedBook = getBookDtoFromResponse(response);
        assertThat(returnedBook.getId()).isEqualTo(1);
        assertThat(returnedBook.getTitle()).isEqualTo("Born Rich");
        assertThat(returnedBook.getAuthor()).isEqualTo("Olga Cannavan");
        assertThat(returnedBook.getPublisher()).isEqualTo("Sharla Tetley");
        assertThat(returnedBook.getRelease_year()).isEqualTo(2004);
        assertThat(returnedBook.getPages()).isEqualTo(555);
        assertThat(returnedBook.getIsbn()).isEqualTo("428112213-3");
        assertThat(returnedBook.getAvailability()).isEqualTo(true);
    }

    @Test
    void shouldNotReturnABookThatDoesNotExist() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/books/99999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();

        response = restTemplate.getForEntity("/api/v1/books/99999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
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
        assertThat(returnedBook.getTitle()).isEqualTo(bookToSaveDto.getTitle());
        assertThat(returnedBook.getAuthor()).isEqualTo(bookToSaveDto.getAuthor());
        assertThat(returnedBook.getPublisher()).isEqualTo(bookToSaveDto.getPublisher());
        assertThat(returnedBook.getRelease_year()).isEqualTo(bookToSaveDto.getRelease_year());
        assertThat(returnedBook.getPages()).isEqualTo(bookToSaveDto.getPages());
        assertThat(returnedBook.getIsbn()).isEqualTo(bookToSaveDto.getIsbn());
        assertThat(returnedBook.getAvailability()).isEqualTo(true);
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
        BookDto bookToUpdate = getBookDto();
        HttpEntity<?> request = createAdminRequest(bookToUpdate);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/books", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/v1/books/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookDto returnedBook = getBookDtoFromResponse(getResponse);
        assertThat(returnedBook.getId()).isNotNull();
        assertThat(returnedBook.getId()).isEqualTo(bookToUpdate.getId());
        assertThat(returnedBook.getTitle()).isEqualTo(bookToUpdate.getTitle());
        assertThat(returnedBook.getAuthor()).isEqualTo(bookToUpdate.getAuthor());
        assertThat(returnedBook.getPublisher()).isEqualTo(bookToUpdate.getPublisher());
        assertThat(returnedBook.getRelease_year()).isEqualTo(bookToUpdate.getRelease_year());
        assertThat(returnedBook.getPages()).isEqualTo(bookToUpdate.getPages());
        assertThat(returnedBook.getIsbn()).isEqualTo(bookToUpdate.getIsbn());
        assertThat(returnedBook.getAvailability()).isEqualTo(bookToUpdate.getAvailability());
    }

    @Test
    void shouldNotUpdateAnExistingBookIfUserRequested() {
        BookDto bookToUpdate = getBookDto();
        HttpEntity<?> request = createUserRequest(bookToUpdate);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/books", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotUpdateAnExistingBookIfUnauthenticatedUserRequested() {
        BookDto bookToUpdate = getBookDto();
        HttpEntity<BookDto> request = new HttpEntity<>(bookToUpdate);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/books", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldNotUpdateABookThatDoesNotExist() {
        BookDto bookToUpdate = getBookDto();
        bookToUpdate.setId(999999L);
        HttpEntity<?> request = createAdminRequest(bookToUpdate);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/books", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "2", "5", "10", "60", "123", "234", "345", "456"
    })
    void shouldPartiallyUpdateAnExistingBookIfAdminRequested(Long bookId) {
        ResponseEntity<String> getResponseBeforeUpdate = restTemplate
                .getForEntity("/api/v1/books/" + bookId, String.class);
        assertThat(getResponseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        BookDto bookBeforeUpdate = getBookDtoFromResponse(getResponseBeforeUpdate);

        BookDto bookFieldsToUpdate = getBookDtoToPartialUpdate();
        HttpEntity<?> request = createAdminRequest(bookFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/books/" + bookId, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/books/" + bookId, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookDto bookAfterUpdate = getBookDtoFromResponse(getResponse);
        assertThat(bookAfterUpdate.getId()).isNotNull();
        assertThat(bookAfterUpdate.getId()).isEqualTo(bookBeforeUpdate.getId());
        assertThat(bookAfterUpdate.getTitle()).isNotEqualTo(bookBeforeUpdate.getTitle());
        assertThat(bookAfterUpdate.getAuthor()).isEqualTo(bookBeforeUpdate.getAuthor());
        assertThat(bookAfterUpdate.getPublisher()).isNotEqualTo(bookBeforeUpdate.getPublisher());
        assertThat(bookAfterUpdate.getRelease_year()).isNotEqualTo(bookBeforeUpdate.getRelease_year());
        assertThat(bookAfterUpdate.getPages()).isEqualTo(bookBeforeUpdate.getPages());
        assertThat(bookAfterUpdate.getIsbn()).isEqualTo(bookBeforeUpdate.getIsbn());
        assertThat(bookAfterUpdate.getAvailability()).isEqualTo(bookBeforeUpdate.getAvailability());
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "2", "5", "10", "60", "123", "234", "345", "456"
    })
    void shouldNotPartiallyUpdateAnExistingBookIfUserRequested(Long bookId) {
        BookDto bookFieldsToUpdate = getBookDtoToPartialUpdate();
        HttpEntity<?> request = createUserRequest(bookFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/books/" + bookId, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "1", "2", "5", "10", "60", "123", "234", "345", "456"
    })
    void shouldNotPartiallyUpdateAnExistingBookIfUnauthorizedUserRequested(Long bookId) {
        BookDto bookFieldsToUpdate = getBookDtoToPartialUpdate();
        HttpEntity<BookDto> request = new HttpEntity<>(bookFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/books/" + bookId, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DirtiesContext
    void shouldNotPartiallyUpdateABookThatDoesNotExist() {
        BookDto bookFieldsToUpdate = getBookDtoToPartialUpdate();
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
            "1", "2", "5", "10", "60", "123", "234", "345", "456"
    })
    void shouldNotDeleteAnExistingBookIfUserRequested(Long bookId) {
        HttpEntity<?> request = createUserRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/books/" + bookId, HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @DirtiesContext
    @CsvSource({
            "3", "4", "5", "7", "12", "16", "19", "23", "25"
    })
    void shouldNotDeleteAnExistingBookIfBookIsAlreadyReservedOrNotReturned(Long bookId) {
        HttpEntity<?> request = createAdminRequest();

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/books/" + bookId, HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
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


    private BookToSaveDto getBookToSaveDto() {
        BookToSaveDto bookToSaveDto = new BookToSaveDto();
        bookToSaveDto.setTitle("Test Title");
        bookToSaveDto.setAuthor("Test Author");
        bookToSaveDto.setPublisher("Test Publisher");
        bookToSaveDto.setRelease_year(2023);
        bookToSaveDto.setPages(543);
        bookToSaveDto.setIsbn("Test ISBN");
        return bookToSaveDto;
    }

    private BookDto getBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Title");
        bookDto.setAuthor("Test Author");
        bookDto.setPublisher("Test Publisher");
        bookDto.setRelease_year(2023);
        bookDto.setPages(543);
        bookDto.setIsbn("Test ISBN");
        return bookDto;
    }

    private BookDto getBookDtoToPartialUpdate() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Title");
        bookDto.setPublisher("Test Publisher");
        bookDto.setRelease_year(2023);
        return bookDto;
    }

    private BookDto getBookDtoFromResponse(ResponseEntity<String> response) {
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
