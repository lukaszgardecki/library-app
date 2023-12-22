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
import com.example.libraryapp.domain.bookItem.dto.BookItemToUpdateDto;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance( TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class BookItemControllerTest {

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
    void shouldReturnPageOf20BookItemsWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/book-items", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int bookListLength = documentContext.read("$._embedded.bookItemDtoList.length()");
        assertThat(bookListLength).isEqualTo(7);
    }

    @Test
    @Order(2)
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
    @Order(3)
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
    @Order(4)
    void shouldNotReturnABookItemThatDoesNotExist() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/book-items/99999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(17)
    void shouldCreateANewBookItemIfAdminRequested() {
        BookItemToSaveDto bookToSaveDto = getBookToSaveDto();
        HttpEntity<?> request = new HttpEntity<>(bookToSaveDto, adminHeader);

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
    @Order(5)
    void shouldNotCreateANewBookItemIfUserRequested() {
        BookItemToSaveDto bookToSaveDto = getBookToSaveDto();
        HttpEntity<?> request = new HttpEntity<>(bookToSaveDto, userHeader);

        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/book-items", request, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(6)
    void shouldNotCreateANewBookItemIfUnauthenticatedUserRequested() {
        BookItemToSaveDto bookToSaveDto = getBookToSaveDto();
        ResponseEntity<String> createResponse = restTemplate
                .postForEntity("/api/v1/book-items", bookToSaveDto, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldUpdateAnExistingBookItemIfAdminRequested() {
        BookItemToUpdateDto bookItemToReplace = getBookToUpdateDto();
        HttpEntity<BookItemToUpdateDto> request = new HttpEntity<>(bookItemToReplace, adminHeader);

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

        assertThat(bookItemAfter.getStatus()).isEqualTo(bookItemToReplace.getStatus());
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
    @Order(7)
    void shouldNotUpdateAnExistingBookItemIfUserRequested() {
        BookItemToUpdateDto bookItemToReplace = getBookToUpdateDto();
        HttpEntity<BookItemToUpdateDto> request = new HttpEntity<>(bookItemToReplace, userHeader);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/book-items/3", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(8)
    void shouldNotUpdateAnExistingBookItemIfUnauthenticatedUserRequested() {
        BookItemToUpdateDto bookItemToReplace = getBookToUpdateDto();
        HttpEntity<BookItemToUpdateDto> request = new HttpEntity<>(bookItemToReplace);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/book-items/3", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(9)
    void shouldNotUpdateABookItemThatDoesNotExist() {
        BookItemToUpdateDto bookItemToReplace = getBookToUpdateDto();
        HttpEntity<BookItemToUpdateDto> request = new HttpEntity<>(bookItemToReplace, adminHeader);
        ResponseEntity<String> putResponse = restTemplate
                .exchange("/api/v1/book-items/999999999", HttpMethod.PUT, request, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldPartiallyUpdateAnExistingBookItemIfAdminRequested(Long bookItemId) {
        ResponseEntity<String> getResponseBeforeUpdate = restTemplate
                .getForEntity("/api/v1/book-items/" + bookItemId, String.class);
        assertThat(getResponseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        BookItemDto bookBeforeUpdate = getBookItemDtoFromResponse(getResponseBeforeUpdate);

        BookItemToUpdateDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<BookItemToUpdateDto> request = new HttpEntity<>(bookFieldsToUpdate, adminHeader);

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
    @Order(10)
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotPartiallyUpdateAnExistingBookItemIfUserRequested(Long bookItemId) {
        BookItemToUpdateDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<BookItemToUpdateDto> request = new HttpEntity<>(bookFieldsToUpdate, userHeader);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/book-items/" + bookItemId, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @Order(11)
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotPartiallyUpdateAnExistingBookItemIfUnauthorizedUserRequested(Long bookItemId) {
        BookItemToUpdateDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<BookItemToUpdateDto> request = new HttpEntity<>(bookFieldsToUpdate);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/book-items/" + bookItemId, HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(12)
    void shouldNotPartiallyUpdateABookItemThatDoesNotExist() {
        BookItemToUpdateDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        HttpEntity<BookItemToUpdateDto> request = new HttpEntity<>(bookFieldsToUpdate, adminHeader);

        ResponseEntity<Void> patchResponse = restTemplate
                .getRestTemplate()
                .exchange("/api/v1/book-items/99999", HttpMethod.PATCH, request, Void.class);
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldDeleteAnExistingBookItemIfAdminRequested() {
        HttpEntity<?> request = new HttpEntity<>(adminHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/book-items/1", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/v1/book-items/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @Order(13)
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotDeleteAnExistingBookItemIfUserRequested(Long bookItemId) {
        HttpEntity<?> request = new HttpEntity<>(userHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/book-items/" + bookItemId, HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @Order(14)
    @CsvSource({
            "3", "4", "7"
    })
    void shouldNotDeleteAnExistingBookItemIfBookItemIsAlreadyReservedOrNotReturned(Long bookItemId) {
        HttpEntity<?> request = new HttpEntity<>(adminHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/book-items/" + bookItemId, HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(15)
    void shouldNotDeleteBookItemThatDoesNotExist() {
        HttpEntity<?> request = new HttpEntity<>(adminHeader);

        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/book-items/999999", HttpMethod.DELETE, request, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(16)
    void shouldNotDeleteBookItemIfUnauthenticatedUserRequested() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/v1/book-items/4", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
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

    private BookItemToUpdateDto getBookToUpdateDto() {
        BookItemToUpdateDto bookToUpdateDto = new BookItemToUpdateDto();
        bookToUpdateDto.setIsReferenceOnly(false);
        bookToUpdateDto.setBorrowed(LocalDate.parse("14-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        bookToUpdateDto.setDueDate(LocalDate.parse("18-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        bookToUpdateDto.setPrice(BigDecimal.valueOf(12.34));
        bookToUpdateDto.setFormat(BookItemFormat.JOURNAL);
        bookToUpdateDto.setStatus(BookItemStatus.AVAILABLE);
        bookToUpdateDto.setDateOfPurchase(LocalDate.parse("05-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        bookToUpdateDto.setPublicationDate(LocalDate.parse("13-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        bookToUpdateDto.setBookId(1L);
        return bookToUpdateDto;
    }

    private BookItemToUpdateDto getBookItemDtoToPartialUpdate() {
        BookItemToUpdateDto bookDto = new BookItemToUpdateDto();
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
