package com.example.libraryapp.web;

import com.example.libraryapp.domain.bookItem.BookItemFormat;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.bookItem.dto.BookItemToSaveDto;
import com.example.libraryapp.domain.bookItem.dto.BookItemToUpdateDto;
import com.example.libraryapp.domain.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class BookItemControllerTest extends BaseTest {

//    @Test
//    void shouldReturnPageOf20BookItemsWhenListIsRequested() {
//        client.get()
//                .uri("/api/v1/book-items")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$._embedded.bookItemDtoList.length()").isEqualTo(20);
//    }
//
//    @Test
//    void shouldReturnPageOf3BookItemsWhenListIsRequested() {
//        client.get()
//                .uri("/api/v1/book-items?page=0&size=3")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$._embedded.bookItemDtoList.length()").isEqualTo(3)
//                .jsonPath("$.page.size").isEqualTo(3)
//                .jsonPath("$.page.totalElements").isEqualTo(70)
//                .jsonPath("$.page.totalPages").isEqualTo(24)
//                .jsonPath("$.page.number").isEqualTo(0);
//    }
//
//    @Test
//    void shouldReturnAnExistingBookItem() {
//        BookItemDto responseBody = client.get()
//                .uri("/api/v1/book-items/1")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(BookItemDto.class)
//                .returnResult().getResponseBody();
//
//        assertThat(responseBody.getId()).isEqualTo(1);
//        assertThat(responseBody.getBarcode()).isEqualTo("540200000001");
//        assertThat(responseBody.getIsReferenceOnly()).isEqualTo(true);
//        assertThat(responseBody.getBorrowed()).isNull();
//        assertThat(responseBody.getDueDate()).isNull();
//        assertThat(responseBody.getPrice()).isEqualTo(BigDecimal.valueOf(12.34));
//        assertThat(responseBody.getFormat()).isEqualTo(BookItemFormat.MAGAZINE);
//        assertThat(responseBody.getStatus()).isEqualTo(BookItemStatus.RESERVED);
//        assertThat(responseBody.getDateOfPurchase()).isEqualTo("2023-01-28");
//        assertThat(responseBody.getPublicationDate()).isEqualTo("2023-01-29");
//        assertThat(responseBody.getBook().getId()).isEqualTo(1);
//        assertThat(responseBody.getBook().getTitle()).isEqualTo("Araya");
//        assertThat(responseBody.getBook().getSubject()).isEqualTo("White Plains");
//        assertThat(responseBody.getBook().getPublisher()).isEqualTo("Xena Hallut");
//        assertThat(responseBody.getBook().getLanguage()).isEqualTo("Hungarian");
//        assertThat(responseBody.getBook().getPages()).isEqualTo(195);
//        assertThat(responseBody.getBook().getISBN()).isEqualTo("460302346-4");
//        assertThat(responseBody.getRack().getId()).isEqualTo(1L);
//        assertThat(responseBody.getRack().getLocationIdentifier()).isEqualTo("123-I-12");
//    }
//
//    @Test
//    void shouldNotReturnABookItemThatDoesNotExist() {
//        client.get()
//                .uri("/api/v1/book-items/9999999")
//                .exchange()
//                .expectStatus().isNotFound()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldCreateANewBookItemIfAdminRequested() {
//        BookItemToSaveDto bookToSaveDto = getBookToSaveDto();
//
//        EntityExchangeResult<BookItemDto> response = client.post()
//                .uri("/api/v1/book-items")
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .body(BodyInserters.fromValue(bookToSaveDto))
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody(BookItemDto.class).returnResult();
//
//        BookItemDto responseBody = response.getResponseBody();
//
//        client.get()
//                .uri(response.getResponseHeaders().getLocation())
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(BookItemDto.class).returnResult();
//
//        assertThat(responseBody.getId()).isNotNull();
//        assertThat(responseBody.getBarcode()).isNotNull();
//        assertThat(responseBody.getStatus()).isNotNull();
//        assertThat(responseBody.getIsReferenceOnly()).isEqualTo(bookToSaveDto.getIsReferenceOnly());
//        assertThat(responseBody.getPrice()).isEqualTo(bookToSaveDto.getPrice());
//        assertThat(responseBody.getFormat()).isEqualTo(bookToSaveDto.getFormat());
//        assertThat(responseBody.getDateOfPurchase()).isEqualTo(bookToSaveDto.getDateOfPurchase());
//        assertThat(responseBody.getPublicationDate()).isEqualTo(bookToSaveDto.getPublicationDate());
//        assertThat(responseBody.getBook().getId()).isEqualTo(1);
//        assertThat(responseBody.getBook().getTitle()).isEqualTo("Araya");
//        assertThat(responseBody.getBook().getSubject()).isEqualTo("White Plains");
//        assertThat(responseBody.getBook().getPublisher()).isEqualTo("Xena Hallut");
//        assertThat(responseBody.getBook().getLanguage()).isEqualTo("Hungarian");
//        assertThat(responseBody.getBook().getPages()).isEqualTo(195);
//        assertThat(responseBody.getBook().getISBN()).isEqualTo("460302346-4");
//        assertThat(responseBody.getRack().getId()).isEqualTo(3L);
//        assertThat(responseBody.getRack().getLocationIdentifier()).isEqualTo("123-III-34");
//    }
//
//    @Test
//    void shouldNotCreateANewBookItemIfUserRequested() {
//        BookItemToSaveDto bookToSaveDto = getBookToSaveDto();
//        client.post()
//                .uri("/api/v1/book-items")
//                .header(HttpHeaders.AUTHORIZATION, userToken)
//                .body(BodyInserters.fromValue(bookToSaveDto))
//                .exchange()
//                .expectStatus().isForbidden()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldNotCreateANewBookItemIfUnauthenticatedUserRequested() {
//        BookItemToSaveDto bookToSaveDto = getBookToSaveDto();
//        client.post()
//                .uri("/api/v1/book-items")
//                .body(BodyInserters.fromValue(bookToSaveDto))
//                .exchange()
//                .expectStatus().isForbidden()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldNotCreateANewBookItemIfRequestBodyIsEmpty() {
//        client.post()
//                .uri("/api/v1/book-items")
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .exchange()
//                .expectStatus().isBadRequest()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldUpdateAnExistingBookItemIfAdminRequested() {
//        BookItemToUpdateDto bookItemToReplace = getBookToUpdateDto();
//
//        BookItemDto responseBodyBefore = client.get()
//                .uri("/api/v1/book-items/3")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(BookItemDto.class)
//                .returnResult().getResponseBody();
//
//        BookItemDto responseBodyAfter = client.put()
//                .uri("/api/v1/book-items/3")
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .body(BodyInserters.fromValue(bookItemToReplace))
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(BookItemDto.class)
//                .returnResult().getResponseBody();
//
//        assertThat(responseBodyAfter.getId()).isEqualTo(responseBodyBefore.getId());
//        assertThat(responseBodyAfter.getBarcode()).isEqualTo(responseBodyBefore.getBarcode());
//        assertThat(responseBodyAfter.getStatus()).isEqualTo(bookItemToReplace.getStatus());
//        assertThat(responseBodyAfter.getIsReferenceOnly()).isEqualTo(bookItemToReplace.getIsReferenceOnly());
//        assertThat(responseBodyAfter.getPrice()).isEqualTo(bookItemToReplace.getPrice());
//        assertThat(responseBodyAfter.getFormat()).isEqualTo(bookItemToReplace.getFormat());
//        assertThat(responseBodyAfter.getDateOfPurchase()).isEqualTo(bookItemToReplace.getDateOfPurchase());
//        assertThat(responseBodyAfter.getPublicationDate()).isEqualTo(bookItemToReplace.getPublicationDate());
//        assertThat(responseBodyAfter.getRack().getId()).isEqualTo(bookItemToReplace.getRackId());
//        assertThat(responseBodyAfter.getRack().getLocationIdentifier()).isEqualTo("123-III-34");
//        assertThat(responseBodyAfter.getBook().getId()).isEqualTo(1);
//        assertThat(responseBodyAfter.getBook().getTitle()).isEqualTo("Araya");
//        assertThat(responseBodyAfter.getBook().getSubject()).isEqualTo("White Plains");
//        assertThat(responseBodyAfter.getBook().getPublisher()).isEqualTo("Xena Hallut");
//        assertThat(responseBodyAfter.getBook().getLanguage()).isEqualTo("Hungarian");
//        assertThat(responseBodyAfter.getBook().getPages()).isEqualTo(195);
//        assertThat(responseBodyAfter.getBook().getISBN()).isEqualTo("460302346-4");
//    }
//
//    @Test
//    void shouldNotUpdateAnExistingBookItemIfUserRequested() {
//        BookItemToUpdateDto bookItemToReplace = getBookToUpdateDto();
//        client.put()
//                .uri("/api/v1/book-items/3")
//                .header(HttpHeaders.AUTHORIZATION, userToken)
//                .body(BodyInserters.fromValue(bookItemToReplace))
//                .exchange()
//                .expectStatus().isForbidden()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldNotUpdateAnExistingBookItemIfUnauthenticatedUserRequested() {
//        BookItemToUpdateDto bookItemToReplace = getBookToUpdateDto();
//        client.put()
//                .uri("/api/v1/book-items/3")
//                .body(BodyInserters.fromValue(bookItemToReplace))
//                .exchange()
//                .expectStatus().isForbidden()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldNotUpdateAnExistingBookItemThatDoesNotExist() {
//        BookItemToUpdateDto bookItemToReplace = getBookToUpdateDto();
//        client.put()
//                .uri("/api/v1/book-items/999999999")
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .body(BodyInserters.fromValue(bookItemToReplace))
//                .exchange()
//                .expectStatus().isNotFound()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldNotUpdateAnExistingBookItemIfRequestBodyIsEmpty() {
//        client.put()
//                .uri("/api/v1/book-items/3")
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .exchange()
//                .expectStatus().isBadRequest()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1", "2", "3", "4", "5", "6"
//    })
//    void shouldPartiallyUpdateAnExistingBookItemIfAdminRequested(Long bookItemId) {
//        BookItemToUpdateDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
//
//        BookItemDto bookBeforeUpdate = client.get()
//                .uri("/api/v1/book-items/" + bookItemId)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(BookItemDto.class)
//                .returnResult().getResponseBody();
//
//        BookItemDto bookAfterUpdate = client.patch()
//                .uri("/api/v1/book-items/" + bookItemId)
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .body(BodyInserters.fromValue(bookFieldsToUpdate))
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(BookItemDto.class)
//                .returnResult().getResponseBody();
//
//        assertThat(bookAfterUpdate.getPrice()).isEqualTo(bookFieldsToUpdate.getPrice());
//        assertThat(bookAfterUpdate.getFormat()).isEqualTo(bookFieldsToUpdate.getFormat());
//        assertThat(bookAfterUpdate.getDateOfPurchase()).isEqualTo(bookFieldsToUpdate.getDateOfPurchase());
//        assertThat(bookAfterUpdate.getId()).isEqualTo(bookBeforeUpdate.getId());
//        assertThat(bookAfterUpdate.getBarcode()).isEqualTo(bookBeforeUpdate.getBarcode());
//        assertThat(bookAfterUpdate.getStatus()).isEqualTo(bookBeforeUpdate.getStatus());
//        assertThat(bookAfterUpdate.getIsReferenceOnly()).isEqualTo(bookBeforeUpdate.getIsReferenceOnly());
//        assertThat(bookAfterUpdate.getPublicationDate()).isEqualTo(bookBeforeUpdate.getPublicationDate());
//        assertThat(bookAfterUpdate.getBook().getId()).isEqualTo(bookBeforeUpdate.getBook().getId());
//        assertThat(bookAfterUpdate.getBook().getTitle()).isEqualTo(bookBeforeUpdate.getBook().getTitle());
//        assertThat(bookAfterUpdate.getBook().getSubject()).isEqualTo(bookBeforeUpdate.getBook().getSubject());
//        assertThat(bookAfterUpdate.getBook().getPublisher()).isEqualTo(bookBeforeUpdate.getBook().getPublisher());
//        assertThat(bookAfterUpdate.getBook().getLanguage()).isEqualTo(bookBeforeUpdate.getBook().getLanguage());
//        assertThat(bookAfterUpdate.getBook().getPages()).isEqualTo(bookBeforeUpdate.getBook().getPages());
//        assertThat(bookAfterUpdate.getBook().getISBN()).isEqualTo(bookBeforeUpdate.getBook().getISBN());
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1", "2", "3", "4", "5", "6"
//    })
//    void shouldNotPartiallyUpdateAnExistingBookItemIfUserRequested(Long bookItemId) {
//        BookItemToUpdateDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
//        client.patch()
//                .uri("/api/v1/book-items/" + bookItemId)
//                .header(HttpHeaders.AUTHORIZATION, userToken)
//                .body(BodyInserters.fromValue(bookFieldsToUpdate))
//                .exchange()
//                .expectStatus().isForbidden()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1", "2", "3", "4", "5", "6"
//    })
//    void shouldNotPartiallyUpdateAnExistingBookItemIfUnauthorizedUserRequested(Long bookItemId) {
//        BookItemToUpdateDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
//        client.patch()
//                .uri("/api/v1/book-items/" + bookItemId)
//                .body(BodyInserters.fromValue(bookFieldsToUpdate))
//                .exchange()
//                .expectStatus().isForbidden()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldNotPartiallyUpdateABookItemThatDoesNotExist() {
//        BookItemToUpdateDto bookFieldsToUpdate = getBookItemDtoToPartialUpdate();
//        client.patch()
//                .uri("/api/v1/book-items/99999")
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .body(BodyInserters.fromValue(bookFieldsToUpdate))
//                .exchange()
//                .expectStatus().isNotFound()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldNotPartiallyUpdateABookItemIfRequestBodyIsEmpty() {
//        client.patch()
//                .uri("/api/v1/book-items/99999")
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .exchange()
//                .expectStatus().isBadRequest()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldDeleteAnExistingBookItemIfBookIsAvailableAndAdminRequested() {
//        client.delete()
//                .uri("/api/v1/book-items/41")
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .exchange()
//                .expectStatus().isNoContent();
//
//        client.get()
//                .uri("/api/v1/book-items/41")
//                .exchange()
//                .expectStatus().isNotFound()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1", "2", "3", "4", "5", "6"
//    })
//    void shouldNotDeleteAnExistingBookItemIfUserRequested(Long bookItemId) {
//        client.delete()
//                .uri("/api/v1/book-items/" + bookItemId)
//                .header(HttpHeaders.AUTHORIZATION, userToken)
//                .exchange()
//                .expectStatus().isForbidden()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1", "3"
//    })
//    void shouldNotDeleteAnExistingBookItemIfBookItemIsAlreadyReservedOrNotReturned(Long bookItemId) {
//        client.delete()
//                .uri("/api/v1/book-items/" + bookItemId)
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .exchange()
//                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldNotDeleteBookItemThatDoesNotExist() {
//        client.delete()
//                .uri("/api/v1/book-items/999999")
//                .header(HttpHeaders.AUTHORIZATION, adminToken)
//                .exchange()
//                .expectStatus().isNotFound()
//                .expectBody(ErrorMessage.class);
//    }
//
//    @Test
//    void shouldNotDeleteBookItemIfUnauthenticatedUserRequested() {
//        client.delete()
//                .uri("/api/v1/book-items/4")
//                .exchange()
//                .expectStatus().isForbidden()
//                .expectBody(ErrorMessage.class);
//    }
//
//    private BookItemToSaveDto getBookToSaveDto() {
//        BookItemToSaveDto bookToSaveDto = new BookItemToSaveDto();
//        bookToSaveDto.setIsReferenceOnly(false);
//        bookToSaveDto.setPrice(BigDecimal.valueOf(12.34));
//        bookToSaveDto.setFormat(BookItemFormat.JOURNAL);
//        bookToSaveDto.setDateOfPurchase(LocalDate.parse("05-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        bookToSaveDto.setPublicationDate(LocalDate.parse("13-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        bookToSaveDto.setBookId(1L);
//        bookToSaveDto.setRackId(3L);
//        return bookToSaveDto;
//    }
//
//    private BookItemToUpdateDto getBookToUpdateDto() {
//        BookItemToUpdateDto bookToUpdateDto = new BookItemToUpdateDto();
//        bookToUpdateDto.setIsReferenceOnly(false);
//        bookToUpdateDto.setBorrowed(LocalDate.parse("14-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        bookToUpdateDto.setDueDate(LocalDate.parse("18-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        bookToUpdateDto.setPrice(BigDecimal.valueOf(12.34));
//        bookToUpdateDto.setFormat(BookItemFormat.JOURNAL);
//        bookToUpdateDto.setStatus(BookItemStatus.AVAILABLE);
//        bookToUpdateDto.setDateOfPurchase(LocalDate.parse("05-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        bookToUpdateDto.setPublicationDate(LocalDate.parse("13-12-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        bookToUpdateDto.setBookId(1L);
//        bookToUpdateDto.setRackId(3L);
//        return bookToUpdateDto;
//    }
//
//    private BookItemToUpdateDto getBookItemDtoToPartialUpdate() {
//        BookItemToUpdateDto bookDto = new BookItemToUpdateDto();
//        bookDto.setPrice(BigDecimal.valueOf(34.45));
//        bookDto.setFormat(BookItemFormat.AUDIO_BOOK);
//        bookDto.setDateOfPurchase(LocalDate.parse("01-10-1993", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        return bookDto;
//    }
}
