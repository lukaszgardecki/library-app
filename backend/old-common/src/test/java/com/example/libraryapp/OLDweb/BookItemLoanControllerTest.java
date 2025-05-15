package com.example.libraryapp.OLDweb;

import com.example.libraryapp.OLDdomain.exception.ErrorMessage;
import com.example.libraryapp.OLDdomain.lending.LendingStatus;
import com.example.libraryapp.OLDdomain.lending.dto.LendingDto;
import com.example.libraryapp.OLDdomain.member.dto.MemberDto;
import com.example.libraryapp.OLDdomain.reservation.ReservationStatus;
import com.example.libraryapp.OLDdomain.reservation.dto.ReservationResponse;
import com.example.libraryapp.OLDmanagement.ActionRequest;
import com.example.libraryapp.OLDmanagement.Constants;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

public class BookItemLoanControllerTest extends BaseTest {

    @Nested
    @DisplayName("Tests for GET endpoints")
    class GetLendingsTests {
        @Test
        @DisplayName("Should return all lendings if ADMIN requested.")
        void shouldReturnAllLendingsIfAdminRequested() {
            client.testRequest(GET, "/lendings", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.lendingDtoList.length()").isEqualTo(12);
        }

        @Test
        @DisplayName("Should return a page of 3 lendings if ADMIN requested.")
        void shouldReturnPageOf3LendingsIfAdminRequested() {
            client.testRequest(GET, "/lendings?page=1&size=3", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.lendingDtoList.length()").isEqualTo(3)
                    .jsonPath("$.page.size").isEqualTo(3)
                    .jsonPath("$.page.totalElements").isEqualTo(12)
                    .jsonPath("$.page.totalPages").isEqualTo(4)
                    .jsonPath("$.page.number").isEqualTo(1);
        }

        @Test
        @DisplayName("Should return all member's lendings if ADMIN requested.")
        void shouldReturnAllUsersLendingsIfAdminRequested() {
            client.testRequest(GET, "/lendings?memberId=1", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.lendingDtoList.length()").isEqualTo(6);

            client.testRequest(GET, "/lendings?memberId=3&status=CURRENT", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.lendingDtoList.length()").isEqualTo(5);

            client.testRequest(GET, "/lendings?status=COMPLETED", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.lendingDtoList.length()").isEqualTo(1);
        }

        @Test
        @DisplayName("Should return all member's lendings if USER requested and does own this data.")
        void shouldReturnAllUsersLendingsIfUserRequestedAndDoesOwnThisData() {
            client.testRequest(GET, "/lendings?memberId=2", user, OK)
                    .expectBody()
                    .jsonPath("$.page.totalElements").isEqualTo(1);
        }

        @Test
        @DisplayName("Should not return member's lendings if ADMIN requested and user ID doesn't exist.")
        void shouldNotReturnAllUsersLendingsIfUserIdDoesNotExist() {
            long memberId = 99999999;
            client.testRequest(GET, "/lendings?memberId=" + memberId, admin, OK)
                    .expectBody()
                    .jsonPath("_embedded").doesNotExist();

            client.testRequest(GET, "/lendings?memberId=badrequest", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class);
        }

        @ParameterizedTest
        @DisplayName("Should not return all member's lendings if USER requested and doesn't own this data")
        @CsvSource({
                "1", "3", "4", "5", "6"
        })
        void shouldNotReturnAllUsersLendingsIfUserRequestedAndDoesNotOwnThisData(Long memberId) {
            ErrorMessage responseBody = client.testRequest(GET, "/lendings?memberId=" + memberId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return all lendings if USER requested.")
        void shouldNotReturnAllUsersLendingsIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/lendings", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return all lendings if an unauthorized USER requested.")
        void shouldNotReturnAllUsersLendingsIfUserIsNotAuthenticated() {
            ErrorMessage responseBody1 = client.testRequest(GET, "/lendings", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody1.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            ErrorMessage responseBody2 = client.testRequest(GET, "/lendings?memberId=1", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody2.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should return a lending if ADMIN requested.")
        @CsvSource({
                "2, 1, 3",
                "5, 3, 9",
                "6, 3, 10"
        })
        void shouldReturnAnExistingLendingIfAdminRequested(Long lendingId, Long memberId, Long bookItemId) {
            LendingDto responseBody = client.testRequest(GET, "/lendings/" + lendingId, admin, OK)
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
        @DisplayName("Should return a lending if USER requested and does own this data.")
        void shouldReturnAnExistingLendingIfUserRequestedAndDoesOwnThisData() {
            LendingDto responseBody = client.testRequest(GET, "/lendings/3", user, OK)
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
        @DisplayName("Should not return a lending if USER requested and doesn't own this data.")
        @CsvSource({
                "1", "2", "4"
        })
        void shouldNotReturnAnExistingLendingIfUserRequestedAndDoesNotOwnThisData(Long lendingId) {
            ErrorMessage responseBody = client.testRequest(GET, "/lendings/" + lendingId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should not return a lending if ADMIN requested and the ID is wrong.")
        @CsvSource({
                "57", "510", "99999"
        })
        void shouldNotReturnLendingThatDoesNotExist(Long lendingId) {
            ErrorMessage responseBody = client.testRequest(GET, "/lendings/" + lendingId, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.LENDING_NOT_FOUND.getMessage(lendingId)");
        }

        @ParameterizedTest
        @DisplayName("Should not return a lending if an unauthorized USER requested.")
        @CsvSource({
                "1", "2", "3", "4"
        })
        void shouldNotReturnAnExistingLendingIfUserIsNotAuthenticated(Long lendingId) {
            ErrorMessage responseBody = client.testRequest(GET, "/lendings/" + lendingId, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for borrowing a book")
    class BorrowBookTests {
        @ParameterizedTest
        @DisplayName("Should borrow a book if ADMIN requested and member has reserved the book earlier.")
        @CsvSource({
                "2, 7, 540200000007, 6"
        })
        void shouldBorrowABookIfAdminRequestedAndMemberHasReservedABookEarlier(
                Long memberId, Long bookItemId, String bookBarcode, Long reservationId
        ) {
            ActionRequest lendingToSave = createRequestBody(memberId, bookBarcode);
            MemberDto memberBefore = findMemberById(memberId);
            BookItemDto bookItemBefore = findBookItemById(bookItemId);

            EntityExchangeResult<LendingDto> response = client.testRequest(POST, "/lendings", lendingToSave, admin, CREATED)
                    .expectBody(LendingDto.class)
                    .returnResult();

            LendingDto returnedLending = client.testRequest(GET, extractURI(response), admin, OK)
                    .expectBody(LendingDto.class)
                    .returnResult().getResponseBody();
            assertThat(returnedLending.getId()).isNotNull();
            assertThat(returnedLending.getCreationDate()).isNotNull();
            assertThat(returnedLending.getDueDate()).isEqualTo(returnedLending.getCreationDate().plusDays(Constants.MAX_LENDING_DAYS));
            assertThat(returnedLending.getReturnDate()).isNull();
            assertThat(returnedLending.getStatus()).isEqualTo(LendingStatus.CURRENT);
            assertThat(returnedLending.getMember()).isEqualTo(memberBefore);
            assertThat(returnedLending.getBookItem()).isEqualTo(bookItemBefore);

            ReservationResponse reservation = client.testRequest(GET, "/reservations/" + reservationId, admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();
            assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.COMPLETED);

            MemberDto memberAfter = findMemberById(memberId);
            assertThat(memberAfter.getTotalBooksBorrowed()).isEqualTo(memberBefore.getTotalBooksBorrowed() + 1);
            assertThat(memberAfter.getTotalBooksReserved()).isEqualTo(memberBefore.getTotalBooksReserved() - 1);

            BookItemDto bookItemAfter = findBookItemById(bookItemId);
            assertThat(bookItemAfter.getBorrowed()).isEqualTo(returnedLending.getCreationDate());
            assertThat(bookItemAfter.getDueDate()).isEqualTo(returnedLending.getDueDate());
            assertThat(bookItemAfter.getStatus()).isEqualTo(BookItemStatus.LOANED);
        }

        @ParameterizedTest
        @DisplayName("Should not borrow a book if ADMIN requested but member hasn't reserved the book earlier.")
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
            ErrorMessage responseBody = client.testRequest(POST, "/lendings", lendingToSave, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.RESERVATION_NOT_FOUND.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should not borrow a book if ADMIN requested but member hasn't returned one yet.")
        @CsvSource({
                "1, 540200000003",
                "2, 540200000006",
                "3, 540200000008"
        })
        void shouldNotBorrowABookIfAdminRequestedAndUserHasNotReturnedOneYet(Long memberId, String bookBarcode) {
            ActionRequest lendingToSave = createRequestBody(memberId, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/lendings", lendingToSave, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.RESERVATION_NOT_FOUND.getMessage()");
        }

        @Test
        @DisplayName("Should not borrow a book if ADMIN requested but member has already borrowed a maximum number of books.")
        void shouldNotBorrowABookIfAdminRequestedAndUserHasMaxTotalBooksBorrowed() {
            ActionRequest lendingToSave = createRequestBody(3L, "540200000013");
            ErrorMessage responseBody = client.testRequest(POST, "/lendings", lendingToSave, admin, CONFLICT)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.LENDING_LIMIT_EXCEEDED.getMessage()");
        }

        @Test
        @DisplayName("Should not borrow a book if ADMIN requested but book barcode doesn't exist.")
        void shouldNotBorrowABookIfAdminRequestedAndBookIdDoesNotExist() {
            String bookBarcode = "540200099999";
            ActionRequest lendingToSave = createRequestBody(2L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/lendings", lendingToSave, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.RESERVATION_NOT_FOUND.getMessage()");
        }

        @Test
        @DisplayName("Should not borrow a book if ADMIN requested but member ID doesn't exist.")
        void shouldNotBorrowABookIfAdminRequestedAndUserIdDoesNotExist() {
            long memberId = 99999999L;
            ActionRequest lendingToSave = createRequestBody(memberId, "540200000002");
            ErrorMessage responseBody = client.testRequest(POST, "/lendings", lendingToSave, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.RESERVATION_NOT_FOUND.getMessage()");
        }

        @Test
        @DisplayName("Should not borrow a book if USER requested.")
        void shouldNotBorrowABookIfUserRequested() {
            ActionRequest lendingToSave = createRequestBody(2L, "540200000001");
            ErrorMessage responseBody = client.testRequest(POST, "/lendings", lendingToSave, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not borrow a book if CASHIER requested.")
        void shouldNotBorrowABookIfCashierRequested() {
            ActionRequest lendingToSave = createRequestBody(2L, "540200000001");
            ErrorMessage responseBody = client.testRequest(POST, "/lendings", lendingToSave, cashier, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not borrow a book if WAREHOUSE requested.")
        void shouldNotBorrowABookIfWarehouseRequested() {
            ActionRequest lendingToSave = createRequestBody(2L, "540200000001");
            ErrorMessage responseBody = client.testRequest(POST, "/lendings", lendingToSave, warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not borrow a book if an unauthorized USER requested.")
        void shouldNotBorrowABookIfUserIsNotAuthenticated() {
            ActionRequest lendingToSave = createRequestBody(2L, "540200000001");
            ErrorMessage responseBody = client.testRequest(POST, "/lendings", lendingToSave, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @Test
        @DisplayName("Should not borrow a book if ADMIN requested and the request body is missing.")
        void shouldNotBorrowABookItemIfRequestBodyIsEmpty() {
            ErrorMessage responseBody = client.testRequest(POST, "/lendings", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BODY_MISSING.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for renewing a book")
    class RenewBookTests {
        @Test
        @DisplayName("Should renew a book if ADMIN requested and the book is not reserved and date is before return date.")
        void shouldRenewABookIfAdminRequestedAndBookIsNotReservedAndDateIsOK() {
            String bookBarcode = "540200000006";
            ActionRequest requestBody = new ActionRequest(2L, bookBarcode);

            LendingDto lendingBefore = client.testRequest(GET, "/lendings/3", admin, OK)
                    .expectBody(LendingDto.class)
                    .returnResult().getResponseBody();

            client.testRequest(POST, "/lendings/renew", requestBody, admin, OK)
                    .expectBody(LendingDto.class);

            LendingDto lendingAfter = client.testRequest(GET, "/lendings/3", admin, OK)
                    .expectBody(LendingDto.class)
                    .returnResult().getResponseBody();

            assertThat(lendingBefore.getDueDate()).isNotEqualTo(lendingAfter.getDueDate());
        }

        @Test
        @DisplayName("Should not renew a book if ADMIN requested and the book is reserved.")
        void shouldNotRenewABookIfAdminRequestedAndBookIsReserved() {
            String bookBarcode = "540200000008";
            ActionRequest requestBody = new ActionRequest(3L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/renew", requestBody, admin, CONFLICT)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.LENDING_RENEWAL_FAILED.getMessage()");
        }

        @Test
        @DisplayName("Should not renew a book if ADMIN requested and returning date is late.")
        void shouldNotRenewABookIfAdminRequestedAndDateIsNotOK() {
            String bookBarcode = "540200000003";
            ActionRequest requestBody = new ActionRequest(1L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/renew", requestBody, admin, CONFLICT)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.LENDING_RENEWAL_FAILED.getMessage()");
        }

        @Test
        @DisplayName("Should not renew a book if ADMIN requested and book barcode doesn't exist.")
        void shouldNotRenewABookIfAdminRequestedAndBookItemDoesNotExist() {
            String bookBarcode = "540299999999";
            ActionRequest requestBody = new ActionRequest(1L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/renew", requestBody, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BOOK_ITEM_NOT_FOUND_BARCODE.getMessage(bookBarcode)");
        }

        @Test
        @DisplayName("Should not renew a book if ADMIN requested and the lending doesn't exist.")
        void shouldNotRenewABookIfAdminRequestedAndLendingDoesNotExist() {
            String bookBarcode = "540200000055";
            ActionRequest requestBody = new ActionRequest(1L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/renew", requestBody, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.LENDING_NOT_FOUND_BARCODE.getMessage(bookBarcode)");
        }

        @Test
        @DisplayName("Should renew a book if USER requested and the lending is theirs.")
        void shouldRenewABookIfUserRequestedAndLendingIsTheirs() {
            String bookBarcode = "540200000006";
            ActionRequest requestBody = new ActionRequest(2L, bookBarcode);
            client.testRequest(POST, "/lendings/renew", requestBody, user, OK)
                    .expectBody(LendingDto.class);
        }

        @Test
        @DisplayName("Should not renew a book if USER requested and the lending is not theirs.")
        void shouldNotRenewABookIfUserRequested() {
            String bookBarcode = "540200000003";
            ActionRequest requestBody = new ActionRequest(1L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/renew", requestBody, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not renew a book if CASHIER requested.")
        void shouldNotRenewABookIfCashierRequested() {
            String bookBarcode = "540200000008";
            ActionRequest requestBody = new ActionRequest(3L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/renew", requestBody, cashier, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not renew a book if WAREHOUSE requested.")
        void shouldNotRenewABookIfWarehouseRequested() {
            String bookBarcode = "540200000008";
            ActionRequest requestBody = new ActionRequest(3L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/renew", requestBody, warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not renew a book if an unauthorized USER requested.")
        void shouldNotRenewABookIfUserIsNotAuthenticated() {
            String bookBarcode = "540200000008";
            ActionRequest requestBody = new ActionRequest(3L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/renew", requestBody, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for returning a book")
    class PatchLendingsTests {
        @ParameterizedTest
        @DisplayName("Should allow to return a book if ADMIN requested and book is already borrowed.")
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

            client.testRequest(PATCH, "/lendings/return?bookBarcode=" + bookBarcode, admin, OK);

            LendingDto lending = client.testRequest(GET, "/lendings/" + lendingId, admin, OK)
                    .expectBody(LendingDto.class)
                    .returnResult().getResponseBody();
            assertThat(lending.getStatus()).isEqualTo(LendingStatus.COMPLETED);
            assertThat(lending.getReturnDate()).isNotNull();

            BookItemDto bookItemAfter = findBookItemById(bookItemId);
            assertThat(bookItemAfter.getStatus()).isIn(BookItemStatus.AVAILABLE, BookItemStatus.REQUESTED);
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
        @DisplayName("Should not allow to return a book if ADMIN requested and book is already returned.")
        @CsvSource({
                "1, 540200000002"
        })
        void shouldNotAllowToReturnABookIfAdminRequestedAndBookItemIsReturnedAlready(Long memberId, String bookBarcode) {
            ActionRequest lendingToSave = createRequestBody(memberId, bookBarcode);
            ErrorMessage responseBody = client.testRequest(PATCH, "/lendings/return?bookBarcode=" + bookBarcode, lendingToSave, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.LENDING_NOT_FOUND_BARCODE.getMessage(bookBarcode)");
        }

        @Test
        @DisplayName("Should not allow to return a book if ADMIN requested and book barcode doesn't exist.")
        void shouldNotAllowToReturnABookIfAdminRequestedAndBookItemIdIsDoesNotExist() {
            String bookBarcode = "540299999999";
            ErrorMessage responseBody = client.testRequest(PATCH, "/lendings/return?bookBarcode=" + bookBarcode, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BOOK_ITEM_NOT_FOUND_BARCODE.getMessage(bookBarcode)");
        }

        @Test
        @DisplayName("Should not allow to return a book if ADMIN requested and the lending doesn't exist.")
        void shouldNotAllowToReturnABookIfAdminRequestedAndLendingDoesNotExist() {
            String bookBarcode = "540200000055";
            ErrorMessage responseBody = client.testRequest(PATCH, "/lendings/return?bookBarcode=" + bookBarcode, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.LENDING_NOT_FOUND_BARCODE.getMessage(bookBarcode)");
        }

        @Test
        @DisplayName("Should not allow to return a book if USER requested.")
        void shouldNotAllowToReturnABookIfUserRequested() {
            String bookBarcode = "540200000003";
            ErrorMessage responseBody = client.testRequest(PATCH, "/lendings/return?bookBarcode=" + bookBarcode, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not allow to return a book if CASHIER requested.")
        void shouldNotAllowToReturnABookIfCashierRequested() {
            String bookBarcode = "540200000003";
            ErrorMessage responseBody = client.testRequest(PATCH, "/lendings/return?bookBarcode=" + bookBarcode, cashier, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not allow to return a book if WAREHOUSE requested.")
        void shouldNotAllowToReturnABookIfWarehouseRequested() {
            String bookBarcode = "540200000003";
            ErrorMessage responseBody = client.testRequest(PATCH, "/lendings/return?bookBarcode=" + bookBarcode, warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not allow to return a book if an unauthorized USER requested.")
        void shouldNotAllowToReturnABookIfUserIsNotAuthenticated() {
            String bookBarcode = "540200000003";
            ErrorMessage responseBody = client.testRequest(PATCH, "/lendings/return?bookBarcode=" + bookBarcode, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for setting a book lost")
    class SetLendingsLostTests {
        @Test
        @DisplayName("Should set a lending lost if ADMIN requested.")
        void shouldSetLendingLostIfAdminRequested() {
            long lendingId = 4L;
            long memberId = 3L;
            long bookItemId = 8L;

            LendingDto lendingBefore = client.testRequest(GET, "/lendings/" + lendingId, admin, OK)
                    .expectBody(LendingDto.class)
                    .returnResult().getResponseBody();

            MemberDto memberBefore = findMemberById(memberId);
            MemberDto anotherMemberReservedThatBookBefore = findMemberById(6L);
            BookItemDto bookItemBefore = client.testRequest(GET, "/book-items/" + bookItemId, admin, OK)
                    .expectBody(BookItemDto.class)
                    .returnResult().getResponseBody();

            BigDecimal bookItemPrice = bookItemBefore.getPrice();

            assertThat(lendingBefore.getStatus()).isEqualTo(LendingStatus.CURRENT);
            assertThat(memberBefore.getCharge()).isEqualTo(new BigDecimal("0.00"));
            assertThat(memberBefore.getTotalBooksBorrowed()).isEqualTo(5);
            assertThat(bookItemBefore.getStatus()).isEqualTo(BookItemStatus.LOANED);

            LendingDto returnBodyPost = client.testRequest(POST, "/lendings/" + lendingId + "/lost", admin, OK)
                    .expectBody(LendingDto.class)
                    .returnResult().getResponseBody();

            assertThat(returnBodyPost.getStatus()).isEqualTo(LendingStatus.COMPLETED);
            assertThat(returnBodyPost.getMember().getCharge()).isEqualTo(bookItemPrice);
            assertThat(returnBodyPost.getBookItem().getStatus()).isEqualTo(BookItemStatus.LOST);

            MemberDto anotherMemberReservedThatBookAfter = findMemberById(6L);
            ReservationResponse anotherReservation = client.testRequest(GET, "/reservations/16", admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();
            assertThat(anotherReservation.getStatus()).isEqualTo(ReservationStatus.CANCELED);
            assertThat(anotherMemberReservedThatBookAfter.getTotalBooksReserved())
                    .isEqualTo(anotherMemberReservedThatBookBefore.getTotalBooksReserved() - 1);
        }

        @Test
        @DisplayName("Should not set a lending lost if USER requested.")
        void shouldNotSetLendingLostIfUserRequested() {
            long lendingId = 4L;
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/" + lendingId + "/lost", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not set a lending lost if an unauthorized USER requested.")
        void shouldNotSetLendingLostIfUnauthenticatedUserRequested() {
            long lendingId = 4L;
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/" + lendingId + "/lost", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @Test
        @DisplayName("Should not set a lending lost if CASHIER requested.")
        void shouldNotSetLendingLostIfCashierRequested() {
            long lendingId = 4L;
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/" + lendingId + "/lost", cashier, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not set a lending lost if WAREHOUSE requested.")
        void shouldNotSetLendingLostIfWarehouseRequested() {
            long lendingId = 4L;
            ErrorMessage responseBody = client.testRequest(POST, "/lendings/" + lendingId + "/lost", warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }
    }

    private ActionRequest createRequestBody(Long userId, String bookBarcode) {
        return new ActionRequest(userId, bookBarcode);
    }

    private MemberDto findMemberById(Long memberId) {
        return client.testRequest(GET, "/members/" + memberId, admin, OK)
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();
    }

    private BookItemDto findBookItemById(Long bookId) {
        return client.testRequest(GET, "/book-items/" + bookId, OK)
                .expectBody(BookItemDto.class)
                .returnResult().getResponseBody();
    }
}
