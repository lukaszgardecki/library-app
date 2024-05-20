package com.example.libraryapp.web;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.management.PairDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookControllerTest extends BaseTest {

    @Test
    void shouldReturnPageOf20BooksWhenListIsRequested() {
        client.get()
                .uri("/api/v1/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.bookDtoList.length()").isEqualTo(20);
    }

    @Test
    void shouldReturnPageOf6BooksWhenListByLanguageIsRequested() {
        client.get()
                .uri("/api/v1/books?lang=Hindi")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.bookDtoList.length()").isEqualTo(6)
                .jsonPath("$.page.size").isEqualTo(20)
                .jsonPath("$.page.totalElements").isEqualTo(6)
                .jsonPath("$.page.totalPages").isEqualTo(1)
                .jsonPath("$.page.number").isEqualTo(0);
    }

    @Test
    void shouldReturnPageOf22BooksWhenListByLanguageIsRequested() {
        client.get()
                .uri("/api/v1/books?page=0&size=10&sort=&lang=Moldovan&lang=Bosnian&lang=Hebrew&lang=Belarusian")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.bookDtoList.length()").isEqualTo(10)
                .jsonPath("$.page.size").isEqualTo(10)
                .jsonPath("$.page.totalElements").isEqualTo(22)
                .jsonPath("$.page.totalPages").isEqualTo(3)
                .jsonPath("$.page.number").isEqualTo(0);
    }

    @Test
    void shouldReturnPageOf3BooksWhenListIsRequested() {
        client.get()
                .uri("/api/v1/books?page=0&size=3")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.bookDtoList.length()").isEqualTo(3)
                .jsonPath("$.page.size").isEqualTo(3)
                .jsonPath("$.page.totalElements").isEqualTo(492)
                .jsonPath("$.page.totalPages").isEqualTo(164)
                .jsonPath("$.page.number").isEqualTo(0);
    }

    @Test
    void shouldReturnListOfBookLanguages() {
        client.get()
                .uri("/api/v1/books/languages/count")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PairDto.class);
    }

    @Test
    void shouldReturnAnExistingBook() {
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .title("Araya")
                .subject("White Plains")
                .publisher("Xena Hallut")
                .language("Hungarian")
                .pages(195)
                .ISBN("460302346-4")
                .build();

        BookDto responseBody = client.get()
                .uri("/api/v1/books/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody.getId()).isEqualTo(bookDto.getId());
        assertThat(responseBody.getTitle()).isEqualTo(bookDto.getTitle());
        assertThat(responseBody.getSubject()).isEqualTo(bookDto.getSubject());
        assertThat(responseBody.getPublisher()).isEqualTo(bookDto.getPublisher());
        assertThat(responseBody.getLanguage()).isEqualTo(bookDto.getLanguage());
        assertThat(responseBody.getPages()).isEqualTo(bookDto.getPages());
        assertThat(responseBody.getISBN()).isEqualTo(bookDto.getISBN());
    }

    @Test
    void shouldNotReturnABookThatDoesNotExist() {
        client.get()
                .uri("/api/v1/books/9999999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldReturnABookItemListOfBook() {
        List<BookItemDto> responseBody = client.get()
                .uri("/api/v1/books/1/book-items")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookItemDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.size()).isEqualTo(3);
        assertThat(responseBody.get(0).getStatus()).isEqualTo(BookItemStatus.RESERVED);
        assertThat(responseBody.get(1).getStatus()).isEqualTo(BookItemStatus.AVAILABLE);
        assertThat(responseBody.get(2).getStatus()).isEqualTo(BookItemStatus.LOANED);
    }

    @Test
    void shouldCreateANewBookIfAdminRequested() {
        BookToSaveDto bookToSaveDto = getBookToSaveDto();

        EntityExchangeResult<BookDto> response = client.post()
                .uri("/api/v1/books")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(bookToSaveDto))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class).returnResult();

        client.get()
                .uri(response.getResponseHeaders().getLocation())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class);
    }

    @Test
    void shouldNotCreateANewBookIfUserRequested() {
        BookToSaveDto bookToSaveDto = getBookToSaveDto();
        client.post()
                .uri("/api/v1/books")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(bookToSaveDto))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotCreateANewBookIfUnauthenticatedUserRequested() {
        BookToSaveDto bookToSaveDto = getBookToSaveDto();
        client.post()
                .uri("/api/v1/books")
                .body(BodyInserters.fromValue(bookToSaveDto))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotCreateABookIfRequestBodyIsEmpty() {
        client.post()
                .uri("/api/v1/books")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldUpdateAnExistingBookIfAdminRequested() {
        BookToSaveDto bookToReplace = getBookToSaveDto();

        BookDto responseBodyBefore = client.get()
                .uri("/api/v1/books/3")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();

        BookDto responseBodyAfter = client.put()
                .uri("/api/v1/books/3")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(bookToReplace))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBodyAfter.getId()).isEqualTo(responseBodyBefore.getId());
        assertThat(responseBodyAfter.getTitle()).isEqualTo(bookToReplace.getTitle());
        assertThat(responseBodyAfter.getSubject()).isEqualTo(bookToReplace.getSubject());
        assertThat(responseBodyAfter.getPublisher()).isEqualTo(bookToReplace.getPublisher());
        assertThat(responseBodyAfter.getLanguage()).isEqualTo(bookToReplace.getLanguage());
        assertThat(responseBodyAfter.getPages()).isEqualTo(bookToReplace.getPages());
        assertThat(responseBodyAfter.getISBN()).isEqualTo(bookToReplace.getISBN());
    }

    @Test
    void shouldNotUpdateAnExistingBookIfUserRequested() {
        BookToSaveDto bookToReplace = getBookToSaveDto();
        client.put()
                .uri("/api/v1/books/3")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(bookToReplace))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotUpdateAnExistingBookIfUnauthenticatedUserRequested() {
        BookToSaveDto bookToReplace = getBookToSaveDto();
        client.put()
                .uri("/api/v1/books/3")
                .body(BodyInserters.fromValue(bookToReplace))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotUpdateABookThatDoesNotExist() {
        BookToSaveDto bookToReplace = getBookToSaveDto();
        client.put()
                .uri("/api/v1/books/999999999")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(bookToReplace))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotUpdateABookIfRequestBodyIsEmpty() {
        client.put()
                .uri("/api/v1/books/3")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldPartiallyUpdateAnExistingBookIfAdminRequested(Long bookId) {
        BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();

        BookDto bookBeforeUpdate = client.get()
                .uri("/api/v1/books/" + bookId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();

        BookDto bookAfterUpdate = client.patch()
                .uri("/api/v1/books/" + bookId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(bookFieldsToUpdate))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();

        assertThat(bookAfterUpdate.getTitle()).isEqualTo(bookFieldsToUpdate.getTitle());
        assertThat(bookAfterUpdate.getPages()).isEqualTo(bookFieldsToUpdate.getPages());
        assertThat(bookAfterUpdate.getPublisher()).isEqualTo(bookFieldsToUpdate.getPublisher());

        assertThat(bookAfterUpdate.getId()).isEqualTo(bookBeforeUpdate.getId());
        assertThat(bookAfterUpdate.getSubject()).isEqualTo(bookBeforeUpdate.getSubject());
        assertThat(bookAfterUpdate.getLanguage()).isEqualTo(bookBeforeUpdate.getLanguage());
        assertThat(bookAfterUpdate.getISBN()).isEqualTo(bookBeforeUpdate.getISBN());
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotPartiallyUpdateAnExistingBookIfUserRequested(Long bookId) {
        BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        client.patch()
                .uri("/api/v1/books/" + bookId)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(bookFieldsToUpdate))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotPartiallyUpdateAnExistingBookIfUnauthorizedUserRequested(Long bookId) {
        BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        client.patch()
                .uri("/api/v1/books/" + bookId)
                .body(BodyInserters.fromValue(bookFieldsToUpdate))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotPartiallyUpdateABookThatDoesNotExist() {
        BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
        client.patch()
                .uri("/api/v1/books/99999")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(bookFieldsToUpdate))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotPartiallyUpdateABookIfRequestBodyIsEmpty() {
        client.patch()
                .uri("/api/v1/books/99999")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldDeleteAnExistingBookIfAdminRequested() {
        client.delete()
                .uri("/api/v1/books/1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/api/v1/books/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "5", "6"
    })
    void shouldNotDeleteAnExistingBookIfUserRequested(Long bookId) {
        client.delete()
                .uri("/api/v1/books/" + bookId)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotDeleteBookThatDoesNotExist() {
        client.delete()
                .uri("/api/v1/books/999999")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotDeleteBookIfUnauthenticatedUserRequested() {
        client.delete()
                .uri("/api/v1/books/4")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
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
}
