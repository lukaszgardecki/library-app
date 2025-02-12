package com.example.libraryapp.OLDweb;

import com.example.libraryapp.OLDdomain.book.dto.BookDto;
import com.example.libraryapp.OLDdomain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.OLDdomain.exception.ErrorMessage;
import com.example.libraryapp.OLDmanagement.LanguageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

public class BookControllerTest extends BaseTest {

    @Nested
    @DisplayName("Tests for GET endpoints")
    class GetBooksTests {
        @Test
        @DisplayName("Should return a page of 20 books.")
        void shouldReturnPageOf20BooksWhenListIsRequested() {
            client.testRequest(GET, "/books", OK)
                    .expectBody()
                    .jsonPath("$._embedded.bookPreviewDtoList.length()").isEqualTo(20);
        }

        @Test
        @DisplayName("Should return a page of 6 books if param lang=Hindi.")
        void shouldReturnPageOf6BooksWhenListByLanguageIsRequested() {
            client.testRequest(GET, "/books?lang=Hindi", OK)
                    .expectBody()
                    .jsonPath("$._embedded.bookPreviewDtoList.length()").isEqualTo(6)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(6)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return a page of 22 books.")
        void shouldReturnPageOf22BooksWhenListByLanguageIsRequested() {
            client.testRequest(GET, "/books?page=0&size=10&sort=&lang=Moldovan&lang=Bosnian&lang=Hebrew&lang=Belarusian", OK)
                    .expectBody()
                    .jsonPath("$._embedded.bookPreviewDtoList.length()").isEqualTo(10)
                    .jsonPath("$.page.size").isEqualTo(10)
                    .jsonPath("$.page.totalElements").isEqualTo(22)
                    .jsonPath("$.page.totalPages").isEqualTo(3)
                    .jsonPath("$.page.number").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return a page of 3 books if param size=3.")
        void shouldReturnPageOf3BooksWhenListIsRequested() {
            client.testRequest(GET, "/books?page=0&size=3", OK)
                    .expectBody()
                    .jsonPath("$._embedded.bookPreviewDtoList.length()").isEqualTo(3)
                    .jsonPath("$.page.size").isEqualTo(3)
                    .jsonPath("$.page.totalElements").isEqualTo(492)
                    .jsonPath("$.page.totalPages").isEqualTo(164)
                    .jsonPath("$.page.number").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return a list of books' languages.")
        void shouldReturnListOfBookLanguages() {
            client.testRequest(GET, "/books/languages/count", OK)
                    .expectBodyList(LanguageDto.class);
        }

        @Test
        @DisplayName("Should return a single book.")
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

            BookDto responseBody = client.testRequest(GET, "/books/1", OK)
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
        @DisplayName("Should not return a book if ID doesn't exist.")
        void shouldNotReturnABookThatDoesNotExist() {
            client.testRequest(GET, "/books/9999999", NOT_FOUND)
                    .expectBody(ErrorMessage.class);
        }

        @Test
        @DisplayName("Should return a list of book items.")
        void shouldReturnABookItemListOfBook() {
            List<BookItemDto> responseBody = client.testRequest(GET, "/books/1/book-items", OK)
                    .expectBodyList(BookItemDto.class)
                    .returnResult().getResponseBody();

            assertThat(responseBody).isNotNull();
            assertThat(responseBody.size()).isEqualTo(3);
            assertThat(responseBody.get(0).getStatus()).isEqualTo(BookItemStatus.REQUESTED);
            assertThat(responseBody.get(1).getStatus()).isEqualTo(BookItemStatus.AVAILABLE);
            assertThat(responseBody.get(2).getStatus()).isEqualTo(BookItemStatus.LOANED);
        }
    }

    @Nested
    @DisplayName("Tests for POST endpoint")
    class CreateBookTests {
        @Test
        @DisplayName("Should create a new book if ADMIN requested.")
        void shouldCreateANewBookIfAdminRequested() {
            BookToSaveDto bookToSaveDto = getBookToSaveDto();

            EntityExchangeResult<BookDto> response = client.testRequest(POST, "/books", bookToSaveDto, admin, CREATED)
                    .expectBody(BookDto.class).returnResult();

            client.testRequest(GET, extractURI(response), OK)
                    .expectBody(BookDto.class);
        }

        @Test
        @DisplayName("Should not create a new book if USER requested.")
        void shouldNotCreateANewBookIfUserRequested() {
            BookToSaveDto bookToSaveDto = getBookToSaveDto();
            ErrorMessage responseBody = client.testRequest(POST, "/books", bookToSaveDto, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not create a new book if an unauthorized user requested.")
        void shouldNotCreateANewBookIfUnauthorizedUserRequested() {
            BookToSaveDto bookToSaveDto = getBookToSaveDto();
            ErrorMessage responseBody = client.testRequest(POST, "/books", bookToSaveDto, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @Test
        @DisplayName("Should not create a new book if request body is missing.")
        void shouldNotCreateABookIfRequestBodyIsEmpty() {
            ErrorMessage responseBody = client.testRequest(POST, "/books", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BODY_MISSING.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for PUT endpoint")
    class UpdateBookTests {
        @Test
        @DisplayName("Should update a book if ADMIN requested.")
        void shouldUpdateAnExistingBookIfAdminRequested() {
            BookToSaveDto bookToReplace = getBookToSaveDto();

            BookDto responseBodyBefore = client.testRequest(GET, "/books/3", OK)
                    .expectBody(BookDto.class)
                    .returnResult().getResponseBody();

            BookDto responseBodyAfter = client.testRequest(PUT, "/books/3", bookToReplace, admin, OK)
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
        @DisplayName("Should not update a book if USER requested.")
        void shouldNotUpdateAnExistingBookIfUserRequested() {
            BookToSaveDto bookToReplace = getBookToSaveDto();
            ErrorMessage responseBody = client.testRequest(PUT, "/books/3", bookToReplace, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not update a book if an unauthorized user requested.")
        void shouldNotUpdateAnExistingBookIfUnauthorizedUserRequested() {
            BookToSaveDto bookToReplace = getBookToSaveDto();
            ErrorMessage responseBody = client.testRequest(PUT, "/books/3", bookToReplace, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @Test
        @DisplayName("Should not update a book if an ID doesn't exist.")
        void shouldNotUpdateABookThatDoesNotExist() {
            BookToSaveDto bookToReplace = getBookToSaveDto();
            long bookId = 999999999;
            ErrorMessage responseBody = client.testRequest(PUT, "/books/" + bookId, bookToReplace, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BOOK_NOT_FOUND.getMessage(bookItemId)");
        }

        @Test
        @DisplayName("Should not update a book if request body is missing.")
        void shouldNotUpdateABookIfRequestBodyIsEmpty() {
            ErrorMessage responseBody = client.testRequest(PUT, "/books/3", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BODY_MISSING.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for PATCH endpoint")
    class PartialUpdateBookTests {
        @ParameterizedTest
        @DisplayName("Should partially update a book if ADMIN requested.")
        @CsvSource({
                "1", "2", "3", "4", "5", "6"
        })
        void shouldPartiallyUpdateAnExistingBookIfAdminRequested(Long bookId) {
            BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();

            BookDto bookBeforeUpdate = client.testRequest(GET, "/books/" + bookId, OK)
                    .expectBody(BookDto.class)
                    .returnResult().getResponseBody();

            BookDto bookAfterUpdate = client.testRequest(PATCH, "/books/" + bookId, bookFieldsToUpdate, admin, OK)
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
        @DisplayName("Should not partially update a book if USER requested.")
        @CsvSource({
                "1", "2", "3", "4", "5", "6"
        })
        void shouldNotPartiallyUpdateAnExistingBookIfUserRequested(Long bookId) {
            BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
            ErrorMessage responseBody = client.testRequest(PATCH, "/books/" + bookId, bookFieldsToUpdate, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should not partially update a book if an unauthorized user requested.")
        @CsvSource({
                "1", "2", "3", "4", "5", "6"
        })
        void shouldNotPartiallyUpdateAnExistingBookIfUnauthorizedUserRequested(Long bookId) {
            BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
            ErrorMessage responseBody = client.testRequest(PATCH, "/books/" + bookId, bookFieldsToUpdate, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @Test
        @DisplayName("Should not partially update a book if an ID doesn't exist.")
        void shouldNotPartiallyUpdateABookThatDoesNotExist() {
            BookDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
            long bookId = 99999;
            ErrorMessage responseBody = client.testRequest(PATCH, "/books/" + bookId, bookFieldsToUpdate, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BOOK_NOT_FOUND.getMessage(bookItemId)");
        }

        @Test
        @DisplayName("Should not partially update a book if request body is missing.")
        void shouldNotPartiallyUpdateABookIfRequestBodyIsEmpty() {
            ErrorMessage responseBody = client.testRequest(PATCH, "/books/99999", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BODY_MISSING.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for DELETE endpoint")
    class DeleteBookTests {
        @Test
        @DisplayName("Should delete a book if ADMIN requested.")
        void shouldDeleteAnExistingBookIfAdminRequested() {
            long bookId = 1;
            client.testRequest(DELETE, "/books/" + bookId, admin, NO_CONTENT)
                    .expectBody().isEmpty();

            ErrorMessage responseBody = client.testRequest(GET, "/books/" + bookId, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BOOK_NOT_FOUND.getMessage(bookItemId)");
        }

        @ParameterizedTest
        @DisplayName("Should not delete a book if USER requested.")
        @CsvSource({
                "1", "2", "3", "4", "5", "6"
        })
        void shouldNotDeleteAnExistingBookIfUserRequested(Long bookId) {
            ErrorMessage responseBody = client.testRequest(DELETE, "/books/" + bookId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not delete a book if an ID doesn't exist.")
        void shouldNotDeleteBookThatDoesNotExist() {
            long bookId = 999999;
            ErrorMessage responseBody = client.testRequest(DELETE, "/books/" + bookId, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BOOK_NOT_FOUND.getMessage(bookItemId)");
        }

        @Test
        @DisplayName("Should not delete a book if an unauthorized user requested.")
        void shouldNotDeleteBookIfUnauthorizedUserRequested() {
            ErrorMessage responseBody = client.testRequest(DELETE, "/books/4", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }
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
