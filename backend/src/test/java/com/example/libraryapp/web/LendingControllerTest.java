package com.example.libraryapp.web;

import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.domain.lending.LendingStatus;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.ActionRequest;
import com.example.libraryapp.management.Constants;
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

public class LendingControllerTest extends BaseTest {

    @Test
    void shouldReturnAllLendingsIfAdminRequested() {
        client.get()
                .uri("/api/v1/lendings")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.lendingDtoList.length()").isEqualTo(8);

        client.get()
                .uri("/api/v1/lendings?memberId=1")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.lendingDtoList.length()").isEqualTo(2);
    }

    @Test
    void shouldReturnPageOf3LendingsIfAdminRequested() {
        client.get()
                .uri("/api/v1/lendings?page=1&size=3")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.lendingDtoList.length()").isEqualTo(3)
                .jsonPath("$.page.size").isEqualTo(3)
                .jsonPath("$.page.totalElements").isEqualTo(8)
                .jsonPath("$.page.totalPages").isEqualTo(3)
                .jsonPath("$.page.number").isEqualTo(1);
    }

    @Test
    void shouldReturnAllUsersLendingsIfUserRequestedAndDoesOwnThisData() {
        client.get()
                .uri("/api/v1/lendings?memberId=2")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.page.totalElements").isEqualTo(1);
    }

    @Test
    void shouldNotReturnAllUsersLendingsIfUserIdDoesNotExist() {
        client.get()
                .uri("/api/v1/lendings?memberId=99999999")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);

        client.get()
                .uri("/api/v1/lendings?memberId=badrequest")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAllUsersLendingsIfUserRequestedAndDoesNotOwnThisData(Long memberId) {
        client.get()
                .uri("/api/v1/lendings?memberId=" + memberId)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllUsersLendingsIfUserRequested() {
        client.get()
                .uri("/api/v1/lendings")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllUsersLendingsIfUserIsNotAuthenticated() {
        client.get()
                .uri("/api/v1/lendings")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);

        client.get()
                .uri("/api/v1/lendings?memberId=1")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 1, 3",
            "5, 3, 9",
            "6, 3, 10"
    })
    void shouldReturnAnExistingLendingIfAdminRequested(Long lendingId, Long memberId, Long bookItemId) {
        LendingDto responseBody = client.get()
                .uri("/api/v1/lendings/" + lendingId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LendingDto.class)
                .returnResult().getResponseBody();

        MemberDto user = findMemberById(memberId);
        BookItemDto book = findBookItemById(bookItemId);

        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getCreationDate()).isNotNull();
        assertThat(responseBody.getDueDate()).isEqualTo(responseBody.getCreationDate().plusDays(Constants.MAX_LENDING_DAYS));
        assertThat(responseBody.getStatus()).isNotNull();
        assertThat(responseBody.getMember()).isEqualTo(user);
        assertThat(responseBody.getBookItem()).isEqualTo(book);
    }

    @Test
    void shouldReturnAnExistingLendingIfUserRequestedAndDoesOwnThisData() {
        LendingDto responseBody = client.get()
                .uri("/api/v1/lendings/3")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LendingDto.class)
                .returnResult().getResponseBody();

        MemberDto member = findMemberById(2L);
        BookItemDto book = findBookItemById(6L);

        assertThat(responseBody.getId()).isEqualTo(3L);
        assertThat(responseBody.getCreationDate()).isEqualTo(LocalDate.parse("2023-02-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(responseBody.getDueDate()).isEqualTo(LocalDate.parse("2025-03-22", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(responseBody.getReturnDate()).isNull();
        assertThat(responseBody.getStatus()).isEqualTo(LendingStatus.CURRENT);
        assertThat(responseBody.getMember()).isEqualTo(member);
        assertThat(responseBody.getBookItem()).isEqualTo(book);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "4"
    })
    void shouldNotReturnAnExistingLendingIfUserRequestedAndDoesNotOwnThisData(Long lendingId) {
        client.get()
                .uri("/api/v1/lendings/" + lendingId)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "57", "510", "99999"
    })
    void shouldNotReturnLendingThatDoesNotExist(Long lendingId) {
        client.get()
                .uri("/api/v1/lendings/" + lendingId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4"
    })
    void shouldNotReturnAnExistingLendingIfUserIsNotAuthenticated(Long lendingId) {
        client.get()
                .uri("/api/v1/lendings/" + lendingId)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 4, 540200000004, 3"
    })
    void shouldBorrowABookIfAdminRequestedAndMemberHasReservedABookEarlier(
            Long memberId, Long bookItemId, String bookBarcode, Long reservationId
    ) {
        ActionRequest lendingToSave = createRequestBody(memberId, bookBarcode);
        MemberDto memberBefore = findMemberById(memberId);
        BookItemDto bookItemBefore = findBookItemById(bookItemId);

        EntityExchangeResult<LendingDto> response = client.post()
                .uri("/api/v1/lendings")
                .body(BodyInserters.fromValue(lendingToSave))
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LendingDto.class)
                .returnResult();

        LendingDto returnedLending = client.get()
                .uri(response.getResponseHeaders().getLocation())
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LendingDto.class)
                .returnResult().getResponseBody();
        assertThat(returnedLending.getId()).isNotNull();
        assertThat(returnedLending.getCreationDate()).isNotNull();
        assertThat(returnedLending.getDueDate()).isEqualTo(returnedLending.getCreationDate().plusDays(Constants.MAX_LENDING_DAYS));
        assertThat(returnedLending.getReturnDate()).isNull();
        assertThat(returnedLending.getStatus()).isEqualTo(LendingStatus.CURRENT);
        assertThat(returnedLending.getMember()).isEqualTo(memberBefore);
        assertThat(returnedLending.getBookItem()).isEqualTo(bookItemBefore);

        ReservationResponse reservation = client.get()
                .uri("/api/v1/reservations/" + reservationId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectBody(ReservationResponse.class)
                .returnResult().getResponseBody();
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.COMPLETED);

        MemberDto memberAfter = findMemberById(memberId);
        assertThat(memberAfter.getTotalBooksBorrowed()).isEqualTo(memberBefore.getTotalBooksBorrowed() + 1);
        assertThat(memberAfter.getTotalBooksReserved()).isEqualTo(memberBefore.getTotalBooksReserved() - 1);

        BookItemDto bookItemAfter = findBookItemById(4L);
        assertThat(bookItemAfter.getBorrowed()).isEqualTo(returnedLending.getCreationDate());
        assertThat(bookItemAfter.getDueDate()).isEqualTo(returnedLending.getDueDate());
        assertThat(bookItemAfter.getStatus()).isEqualTo(BookItemStatus.LOANED);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 540200000002",
            "1, 540200000100",
            "2, 540200000005",
            "3, 540200000004",
            "3, 540200000005",
            "3, 540200000007",
            "4, 540200000001",
            "4, 540200000008",
            "5, 540200000004",
            "5, 540200000007",
            "6, 540200000005",
            "6, 540200000001",
            "7, 540200000006",
            "7, 540200000004"
    })
    void shouldNotBorrowABookIfAdminRequestedButUserHasNotReservedABookEarlier(Long memberId, String bookBarcode) {
        ActionRequest lendingToSave = createRequestBody(memberId, bookBarcode);
        client.post()
                .uri("/api/v1/lendings")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(lendingToSave))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 540200000003",
            "2, 540200000006",
            "3, 540200000008"
    })
    void shouldNotBorrowABookIfAdminRequestedAndUserHasNotReturnedOneYet(Long memberId, String bookBarcode) {
        ActionRequest lendingToSave = createRequestBody(memberId, bookBarcode);
        client.post()
                .uri("/api/v1/lendings")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(lendingToSave))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotBorrowABookIfAdminRequestedAndUserHasMaxTotalBooksBorrowed() {
        ActionRequest lendingToSave = createRequestBody(3L, "540200000013");
        client.post()
                .uri("/api/v1/lendings")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(lendingToSave))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotBorrowABookIfAdminRequestedAndBookIdDoesNotExist() {
        ActionRequest lendingToSave = createRequestBody(2L, "540200099999");
        client.post()
                .uri("/api/v1/lendings")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(lendingToSave))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotBorrowABookIfAdminRequestedAndUserIdDoesNotExist() {
        ActionRequest lendingToSave = createRequestBody(99999999L, "540200000002");
        client.post()
                .uri("/api/v1/lendings")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(lendingToSave))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotBorrowABookIfUserRequested() {
        ActionRequest lendingToSave = createRequestBody(2L, "540200000001");
        client.post()
                .uri("/api/v1/lendings")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(lendingToSave))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotBorrowABookIfUserIsNotAuthenticated() {
        ActionRequest lendingToSave = createRequestBody(2L, "540200000001");
        client.post()
                .uri("/api/v1/lendings")
                .body(BodyInserters.fromValue(lendingToSave))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotBorrowABookItemIfRequestBodyIsEmpty() {
        client.post()
                .uri("/api/v1/lendings")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldRenewABookIfAdminRequestedAndBookIsNotReservedAndDateIsOK() {
        String bookBarcode = "540200000006";

        LendingDto lendingBefore = client.get()
                .uri("/api/v1/lendings/3")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LendingDto.class)
                .returnResult().getResponseBody();

        client.post()
                .uri("/api/v1/lendings/renew?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk();

        LendingDto lendingAfter = client.get()
                .uri("/api/v1/lendings/3")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LendingDto.class)
                .returnResult().getResponseBody();

        assertThat(lendingBefore.getDueDate()).isNotEqualTo(lendingAfter.getDueDate());
    }

    @Test
    void shouldNotRenewABookIfAdminRequestedAndBookIsReserved() {
        String bookBarcode = "540200000008";
        client.post()
                .uri("/api/v1/lendings/renew?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotRenewABookIfAdminRequestedAndDateIsNotOK() {
        String bookBarcode = "540200000003";
        client.post()
                .uri("/api/v1/lendings/renew?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotRenewABookIfAdminRequestedAndBookItemDoesNotExist() {
        String bookBarcode = "540299999999";
        client.post()
                .uri("/api/v1/lendings/renew?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotRenewABookIfAdminRequestedAndLendingDoesNotExist() {
        String bookBarcode = "540200000055";
        client.post()
                .uri("/api/v1/lendings/renew?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotRenewABookIfUserRequested() {
        String bookBarcode = "540200000008";
        client.post()
                .uri("/api/v1/lendings/renew?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotRenewABookIfUserIsNotAuthenticated() {
        String bookBarcode = "540200000008";
        client.post()
                .uri("/api/v1/lendings/renew?bookBarcode=" + bookBarcode)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 540200000003, 2, 3, true",
            "2, 540200000006, 3, 6, false",
            "3, 540200000008, 4, 8, false"
    })
    void shouldAllowToReturnABookIfAdminRequestedAndBookItemIsBorrowedAlready(
            Long memberId,
            String bookBarcode,
            Long lendingId,
            Long bookItemId,
            boolean isAfterDate
    ) {
        MemberDto memberBefore = findMemberById(memberId);
        BookItemDto bookItemBefore = findBookItemById(bookItemId);
        assertThat(bookItemBefore.getStatus()).isEqualTo(BookItemStatus.LOANED);

        client.patch()
                .uri("/api/v1/lendings/return?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk();

        LendingDto lending = client.get()
                .uri("/api/v1/lendings/" + lendingId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LendingDto.class)
                .returnResult().getResponseBody();
        assertThat(lending.getStatus()).isEqualTo(LendingStatus.COMPLETED);
        assertThat(lending.getReturnDate()).isNotNull();

        BookItemDto bookItemAfter = findBookItemById(bookItemId);
        assertThat(bookItemAfter.getStatus()).isIn(BookItemStatus.AVAILABLE, BookItemStatus.RESERVED);
        assertThat(bookItemAfter.getDueDate()).isEqualTo(lending.getReturnDate());

        MemberDto memberAfter = findMemberById(memberId);
        assertThat(memberAfter.getTotalBooksBorrowed()).isEqualTo(memberBefore.getTotalBooksBorrowed() - 1);
        if (isAfterDate) {
            assertThat(memberAfter.getCharge()).isGreaterThan(memberBefore.getCharge());
        } else {
            assertThat(memberAfter.getCharge()).isEqualTo(memberBefore.getCharge());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1, 540200000002"
    })
    void shouldNotAllowToReturnABookIfAdminRequestedAndBookItemIsReturnedAlready(Long memberId, String bookBarcode) {
        ActionRequest lendingToSave = createRequestBody(memberId, bookBarcode);
        client.patch()
                .uri("/api/v1/lendings/return?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(lendingToSave))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotAllowToReturnABookIfAdminRequestedAndBookItemIdIsDoesNotExist() {
        String bookBarcode = "540299999999";
        client.patch()
                .uri("/api/v1/lendings/return?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnABookIfAdminRequestedAndLendingDoesNotExist() {
        String bookBarcode = "540200000055";
        client.patch()
                .uri("/api/v1/lendings/return?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotAllowToReturnABookItemIfUserRequested() {
        String bookBarcode = "540200000003";
        client.patch()
                .uri("/api/v1/lendings/return?bookBarcode=" + bookBarcode)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotAllowToReturnABookItemIfUserIsNotAuthenticated() {
        String bookBarcode = "540200000003";
        client.patch()
                .uri("/api/v1/lendings/return?bookBarcode=" + bookBarcode)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldSetLendingLostIfAdminRequested() {
        long lendingId = 4L;
        long memberId = 3L;
        long bookItemId = 8L;

        LendingDto lendingBefore = client.get()
                .uri("/api/v1/lendings/" + lendingId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LendingDto.class)
                .returnResult().getResponseBody();

        MemberDto memberBefore = client.get()
                .uri("/api/v1/members/" + memberId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();

        BookItemDto bookItemBefore = client.get()
                .uri("/api/v1/book-items/" + bookItemId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookItemDto.class)
                .returnResult().getResponseBody();

        BigDecimal bookItemPrice = bookItemBefore.getPrice();

        assertThat(lendingBefore.getStatus()).isEqualTo(LendingStatus.CURRENT);
        assertThat(memberBefore.getCharge()).isEqualTo(new BigDecimal("0.00"));
        assertThat(memberBefore.getTotalBooksBorrowed()).isEqualTo(5);
        assertThat(bookItemBefore.getStatus()).isEqualTo(BookItemStatus.LOANED);

        LendingDto returnBodyPost = client.post()
                .uri("/api/v1/lendings/" + lendingId + "/lost")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LendingDto.class)
                .returnResult().getResponseBody();

        assertThat(returnBodyPost.getStatus()).isEqualTo(LendingStatus.COMPLETED);
        assertThat(returnBodyPost.getMember().getCharge()).isEqualTo(bookItemPrice);
        assertThat(returnBodyPost.getBookItem().getStatus()).isEqualTo(BookItemStatus.LOST);

        ReservationResponse anotherReservation = client.get()
                .uri("/api/v1/reservations/16")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReservationResponse.class)
                .returnResult().getResponseBody();
        assertThat(anotherReservation.getStatus()).isEqualTo(ReservationStatus.CANCELED);
    }

    @Test
    void shouldNotSetLendingLostIfUserRequested() {
        long lendingId = 4L;
        client.post()
                .uri("/api/v1/lendings/" + lendingId + "/lost")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotSetLendingLostIfUnauthenticatedUserRequested() {
        long lendingId = 4L;
        client.post()
                .uri("/api/v1/lendings/" + lendingId + "/lost")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotSetLendingLostIfCashierRequested() {
        long lendingId = 4L;
        client.post()
                .uri("/api/v1/lendings/" + lendingId + "/lost")
                .header(HttpHeaders.AUTHORIZATION, cashierToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotSetLendingLostIfWarehouseRequested() {
        long lendingId = 4L;
        client.post()
                .uri("/api/v1/lendings/" + lendingId + "/lost")
                .header(HttpHeaders.AUTHORIZATION, warehouseToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    private ActionRequest createRequestBody(Long userId, String bookBarcode) {
        return new ActionRequest(userId, bookBarcode);
    }

    private MemberDto findMemberById(Long memberId) {
        return client.get()
                .uri("/api/v1/members/" + memberId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();
    }

    private BookItemDto findBookItemById(Long bookId) {
        return client.get()
                .uri("/api/v1/book-items/" + bookId)
                .exchange()
                .expectBody(BookItemDto.class)
                .returnResult().getResponseBody();
    }
}
