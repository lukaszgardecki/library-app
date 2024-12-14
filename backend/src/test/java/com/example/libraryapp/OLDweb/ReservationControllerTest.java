package com.example.libraryapp.OLDweb;

import com.example.libraryapp.NEWdomain.bookitem.model.BookItemStatus;
import com.example.libraryapp.NEWdomain.bookitem.dto.BookItemDto;
import com.example.libraryapp.OLDdomain.exception.ErrorMessage;
import com.example.libraryapp.OLDdomain.member.dto.MemberDto;
import com.example.libraryapp.OLDdomain.reservation.ReservationStatus;
import com.example.libraryapp.OLDdomain.reservation.dto.ReservationResponse;
import com.example.libraryapp.OLDmanagement.ActionRequest;
import com.example.libraryapp.OLDmanagement.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

public class ReservationControllerTest extends BaseTest {

    @Nested
    @DisplayName("Tests for GET endpoints")
    class GetReservationsTests {
        @Test
        @DisplayName("Should return all reservations if ADMIN requested.")
        void shouldReturnAllReservationsIfAdminRequested() {
            client.testRequest(GET, "/reservations", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.reservationResponseList.length()").isEqualTo(16);
        }

        @Test
        @DisplayName("Should return a page of 3 reservations if ADMIN requested.")
        void shouldReturnPageOf3ReservationsIfAdminRequested() {
            client.testRequest(GET, "/reservations?page=1&size=3", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.reservationResponseList.length()").isEqualTo(3)
                    .jsonPath("$.page.size").isEqualTo(3)
                    .jsonPath("$.page.totalElements").isEqualTo(16)
                    .jsonPath("$.page.totalPages").isEqualTo(6)
                    .jsonPath("$.page.number").isEqualTo(1);
        }

        @ParameterizedTest
        @DisplayName("Should return all member's reservations if ADMIN requested.")
        @CsvSource({
                "memberId=2, 2",
                "status=PENDING, 4",
                "status=READY, 4",
                "status=COMPLETED, 8",
                "memberId=3&status=COMPLETED, 5",
        })
        void shouldReturnAllUsersReservationsIfAdminRequested(String params, int expectedValue) {
            client.testRequest(GET, "/reservations?" + params, admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.reservationResponseList.length()").isEqualTo(expectedValue);
        }

        @Test
        @DisplayName("Should return all user's reservations if USER requested and does own this data.")
        void shouldReturnAllUsersReservationsIfUserRequestedAndDoesOwnThisData() {
            client.testRequest(GET, "/reservations?memberId=2", user, OK)
                    .expectBody()
                    .jsonPath("$.page.totalElements").isEqualTo(2);
        }

        @Test
        @DisplayName("Should not return member's reservations if ADMIN requested and member ID doesn't exist.")
        void shouldNotReturnMembersReservationsIfMemberIdDoesNotExist() {
            long memberId = 99999999;
            client.testRequest(GET, "/reservations?memberId=" + memberId, admin, OK)
                    .expectBody()
                    .jsonPath("_embedded").doesNotExist();

            client.testRequest(GET, "/reservations?memberId=badrequest", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class);
        }

        @ParameterizedTest
        @DisplayName("Should not return member's reservations if USER requested and doesn't own this data.")
        @CsvSource({
                "1", "3", "4", "5", "6"
        })
        void shouldNotReturnAllUsersReservationsIfUserRequestedAndDoesNotOwnThisData(Long memberId) {
            ErrorMessage responseBody = client.testRequest(GET, "/reservations?memberId=" + memberId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return all reservations if USER requested.")
        void shouldNotReturnAllUsersReservationsIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/reservations", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return all reservations if an unauthorized USER requested.")
        void shouldNotReturnAllReservationsIfUserIsNotAuthenticated() {
            ErrorMessage responseBody1 = client.testRequest(GET, "/reservations", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody1.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");

            ErrorMessage responseBody2 = client.testRequest(GET, "/reservations?memberId=1", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody2.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should return a reservation if ADMIN requested.")
        @CsvSource({
                "1, 1, 2",
                "2, 1, 3",
                "3, 1, 4",
                "4, 1, 5",
                "5, 2, 6",
                "6, 2, 7",
                "7, 3, 8",
                "8, 3, 9",
                "9, 3, 10",
                "10, 3, 11"
        })
        void shouldReturnAnExistingReservationIfAdminRequested(Long reservationId, Long memberId, Long bookItemId) {
            ReservationResponse returnedReservation = client.testRequest(GET, "/reservations/" + reservationId, admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            MemberDto member = findMemberById(memberId);
            BookItemDto book = findBookItemById(bookItemId);

            assertThat(returnedReservation.getId()).isNotNull();
            assertThat(returnedReservation.getCreationDate()).isNotNull();
            assertThat(returnedReservation.getStatus()).isNotNull();
            assertThat(returnedReservation.getMember()).isEqualTo(member);
            assertThat(returnedReservation.getBookItem()).isEqualTo(book);
        }

        @Test
        @DisplayName("Should return a reservation if USER requested and does own this data.")
        void shouldReturnAnExistingReservationIfUserRequestedAndDoesOwnThisData() {
            ReservationResponse returnedReservation = client.testRequest(GET, "/reservations/5", user, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            MemberDto member = findMemberById(2L);
            BookItemDto book = findBookItemById(6L);

            assertThat(returnedReservation.getId()).isEqualTo(5L);
            assertThat(returnedReservation.getCreationDate()).isEqualTo(LocalDateTime.parse("2023-02-20 04:27:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            assertThat(returnedReservation.getStatus()).isEqualTo(ReservationStatus.COMPLETED);
            assertThat(returnedReservation.getMember()).isEqualTo(member);
            assertThat(returnedReservation.getBookItem()).isEqualTo(book);
        }

        @ParameterizedTest
        @DisplayName("Should not return a reservation if USER requested and doesn't own this data.")
        @CsvSource({
                "1", "2", "3", "4", "7", "8", "9", "10"
        })
        void shouldNotReturnAnExistingReservationIfUserRequestedAndDoesNotOwnThisData(Long reservationId) {
            ErrorMessage responseBody = client.testRequest(GET, "/reservations/" + reservationId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should not return a reservation if ADMIN requested and an ID doesn't exist.")
        @CsvSource({
                "57", "510", "99999"
        })
        void shouldNotReturnReservationThatDoesNotExist(Long reservationId) {
            ErrorMessage responseBody = client.testRequest(GET, "/reservations/" + reservationId, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.RESERVATION_NOT_FOUND_ID.getMessage(reservationId)");
        }

        @ParameterizedTest
        @DisplayName("Should not return a reservation if an unauthorized USER requested.")
        @CsvSource({
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"
        })
        void shouldNotReturnAnExistingReservationIfUserIsNotAuthenticated(Long reservationId) {
            ErrorMessage responseBody = client.testRequest(GET, "/reservations/" + reservationId, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for POST endpoints")
    class PostReservationsTests {
        @Test
        @DisplayName("Should make a reservation if USER requested and ID is theirs.")
        void shouldMakeAReservationIfUserIsAuthenticated() {
            ActionRequest reservationToSave = createPostRequestBody(2L, "540200000002");
            MemberDto user = findMemberById(2L);
            BookItemDto bookAfterReservation = findBookItemById(2L);

            EntityExchangeResult<ReservationResponse> response = client.testRequest(POST, "/reservations", reservationToSave, ReservationControllerTest.this.user, CREATED)
                    .expectBody(ReservationResponse.class)
                    .returnResult();

            ReservationResponse returnedReservation = client.testRequest(GET, extractURI(response), admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            assertThat(returnedReservation.getId()).isNotNull();
            assertThat(returnedReservation.getCreationDate()).isNotNull();
            assertThat(returnedReservation.getStatus()).isEqualTo(ReservationStatus.PENDING);
            assertThat(returnedReservation.getMember()).isEqualTo(user);
            assertThat(returnedReservation.getBookItem()).isEqualTo(bookAfterReservation);
        }

        @Test
        @DisplayName("Should not make a reservation if USER requested and book barcode doesn't exist.")
        void shouldNotMakeAReservationIfUserIsAuthenticatedAndBookIdDoesNotExist() {
            String bookBarcode = "540200099999";
            ActionRequest reservationToSave = createPostRequestBody(2L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/reservations", reservationToSave, user, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BOOK_ITEM_NOT_FOUND_BARCODE.getMessage(bookBarcode)");
        }

        @Test
        @DisplayName("Should not make a reservation if USER requested and they have already reserved a maximum amount of books.")
        void shouldNotMakeAReservationIfUserHasMaxTotalBooksReserved() {
            ActionRequest reservationToSave = createPostRequestBody(7L, "540200000001");

            for (int i = 1; i <= Constants.MAX_BOOKS_RESERVED_BY_USER; i++) {
                client.testRequest(POST, "/reservations", reservationToSave, admin, CREATED);

                reservationToSave = createPostRequestBody(7L, "54020000000" + (i + 1));
            }

            ErrorMessage responseBody = client.testRequest(POST, "/reservations", reservationToSave, admin, CONFLICT)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.RESERVATION_LIMIT_EXCEEDED.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should not make a reservation if USER requested and an ID is not theirs.")
        @CsvSource({
                "1", "3", "4", "5", "6"
        })
        void shouldNotMakeAReservationIfUserIsAuthenticatedButUserIdIsNotTheir(Long userId) {
            ActionRequest reservationToSave = createPostRequestBody(userId, "540200000002");
            ErrorMessage responseBody = client.testRequest(POST, "/reservations", reservationToSave, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should not make a reservation if USER requested and the book is lost.")
        @CsvSource({
                "540200000061",
                "540200000062"
        })
        void shouldNotMakeAReservationIfUserIsAuthenticatedAndBookItemIsLost(String bookBarcode) {
            ActionRequest reservationToSave = createPostRequestBody(2L, bookBarcode);
            ErrorMessage responseBody = client.testRequest(POST, "/reservations", reservationToSave, user, CONFLICT)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.RESERVATION_CREATION_FAILED_BOOK_ITEM_LOST.getMessage()");
        }

        @Test
        @DisplayName("Should not make a reservation if an unauthorized USER requested.")
        void shouldNotMakeAReservationIfUserIsNotAuthenticated() {
            ActionRequest reservationToSave = createPostRequestBody(2L, "540200000002");
            ErrorMessage responseBody = client.testRequest(POST, "/reservations", reservationToSave, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @Test
        @DisplayName("Should not make a reservation if ADMIN requested and a request body is missing.")
        void shouldNotMakeAReservationIfRequestBodyIsEmpty() {
            ErrorMessage responseBody = client.testRequest(POST, "/reservations", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BODY_MISSING.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for DELETE endpoints")
    class DeleteReservationsTests {
        @ParameterizedTest
        @DisplayName("Should cancel a reservation if its status is still PENDING and ADMIN requested.")
        @CsvSource({
                "13, 4, 1, 540200000001, false",
                "14, 5, 21, 540200000021, false",
                "15, 6, 3, 540200000003, false",
                "16, 6, 8, 540200000008, false",
        })
        void shouldCancelAReservationIfItsStatusIsStillPendingAndIfAdminRequested(
                Long reservationId,
                Long memberId,
                Long bookItemId,
                String bookBarcode,
                Boolean isReservedBySomeoneElse
        ) {
            ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
            MemberDto memberBeforeResCanceling = findMemberById(memberId);

            client.testRequest(DELETE, "/reservations", reservationToCancel, admin, NO_CONTENT);

            ReservationResponse returnedReservation = client.testRequest(GET, "/reservations/" + reservationId, admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            assertThat(returnedReservation.getStatus()).isEqualTo(ReservationStatus.CANCELED);

            MemberDto memberAfterResCanceling = findMemberById(memberId);
            assertThat(memberBeforeResCanceling.getTotalBooksReserved()).isEqualTo(memberAfterResCanceling.getTotalBooksReserved() + 1);

            BookItemDto bookItemAfterResCanceling = findBookItemById(bookItemId);
            if (isReservedBySomeoneElse) {
                assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.REQUESTED, BookItemStatus.LOANED);
            } else {
                assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.AVAILABLE, BookItemStatus.LOANED);
            }
        }

        @ParameterizedTest
        @DisplayName("Should cancel a reservation if its status is still PENDING, USER requested and the reservation is theirs.")
        @CsvSource({
                "14, 5, 21, 540200000021, false"
        })
        void shouldCancelAReservationIfUserRequestedAndReservationBelongsToTheir(
                Long reservationId,
                Long memberId,
                Long bookItemId,
                String bookBarcode,
                Boolean isReservedBySomeoneElse
        ) {
            ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
            MemberDto memberBeforeResCanceling = findMemberById(memberId);

            client.testRequest(DELETE, "/reservations", reservationToCancel, user5, NO_CONTENT);
            client.testRequest(GET, "/reservations/" + reservationId, admin, OK)
                    .expectBody(ReservationResponse.class);

            MemberDto memberAfterResCanceling = findMemberById(memberId);
            assertThat(memberBeforeResCanceling.getTotalBooksReserved()).isEqualTo(memberAfterResCanceling.getTotalBooksReserved() + 1);

            BookItemDto bookItemAfterResCanceling = findBookItemById(bookItemId);
            if (isReservedBySomeoneElse) {
                assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.REQUESTED, BookItemStatus.LOANED);
            } else {
                assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.AVAILABLE, BookItemStatus.LOANED);
            }
        }

        @ParameterizedTest
        @DisplayName("Should not cancel a reservation if its status is READY")
        @CsvSource({
                "3, 1, 4, 540200000004",
                "6, 2, 7, 540200000007",
        })
        void shouldNotCancelAReservationIfStatusIsReady(
                Long reservationId,
                Long memberId,
                Long bookItemId,
                String bookBarcode
        ) {
            ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
            MemberDto memberBeforeResCanceling = findMemberById(memberId);
            BookItemDto bookItemBeforeResCanceling = findBookItemById(bookItemId);

            ReservationResponse reservationBeforeCanceling = client.testRequest(GET, "/reservations/" + reservationId, admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            ErrorMessage responseBody = client.testRequest(DELETE, "/reservations", reservationToCancel, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.RESERVATION_NOT_FOUND.getMessage()");

            ReservationResponse reservationAfterCanceling = client.testRequest(GET, "/reservations/" + reservationId, admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            MemberDto memberAfterResCanceling = findMemberById(memberId);
            BookItemDto bookItemAfterResCanceling = findBookItemById(bookItemId);

            assertThat(reservationBeforeCanceling).isEqualTo(reservationAfterCanceling);
            assertThat(memberBeforeResCanceling).isEqualTo(memberAfterResCanceling);
            assertThat(bookItemBeforeResCanceling).isEqualTo(bookItemAfterResCanceling);
        }

        @ParameterizedTest
        @DisplayName("Should not cancel a reservation if USER requested and the reservation is not theirs.")
        @CsvSource({
                "3, 1, 4, 540200000004",
                "4, 1, 5, 540200000005",
                "12, 3, 13, 540200000013",
                "13, 4, 1, 540200000001",
                "14, 5, 21, 540200000021",
                "15, 6, 3, 540200000003",
                "16, 6, 8, 540200000008"
        })
        void shouldNotCancelAReservationIfUserRequestedAndReservationIdIsNotTheir(
                Long reservationId,
                Long memberId,
                Long bookItemId,
                String bookBarcode
        ) {
            ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
            MemberDto memberBeforeResCanceling = findMemberById(memberId);
            BookItemDto bookItemBeforeResCanceling = findBookItemById(bookItemId);

            ReservationResponse reservationBeforeCanceling = client.testRequest(GET, "/reservations/" + reservationId, admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            ErrorMessage responseBody = client.testRequest(DELETE, "/reservations", reservationToCancel, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");

            ReservationResponse reservationAfterCanceling = client.testRequest(GET, "/reservations/" + reservationId, admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            MemberDto memberAfterResCanceling = findMemberById(memberId);
            BookItemDto bookItemAfterResCanceling = findBookItemById(bookItemId);

            assertThat(reservationBeforeCanceling).isEqualTo(reservationAfterCanceling);
            assertThat(memberBeforeResCanceling).isEqualTo(memberAfterResCanceling);
            assertThat(bookItemBeforeResCanceling).isEqualTo(bookItemAfterResCanceling);
        }

        @Test
        @DisplayName("Should not cancel a reservation if an unauthorized USER requested.")
        void shouldNotCancelAReservationIfUserIsNotAuthenticated() {
            ActionRequest reservationToCancel = createPostRequestBody(1L, "540200000004");
            ErrorMessage responseBody = client.testRequest(DELETE, "/reservations", reservationToCancel, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @ParameterizedTest
        @DisplayName("Should not cancel a reservation if ADMIN requested and user ID or book barcode is wrong.")
        @CsvSource({
                "1, 540200099999",
                "999, 54020000002",
        })
        void shouldNotCancelAReservationThatDoesNotExist(Long memberId, String bookBarcode) {
            ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
            ErrorMessage responseBody = client.testRequest(DELETE, "/reservations", reservationToCancel, admin, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.RESERVATION_NOT_FOUND.getMessage()");
        }

        @Test
        @DisplayName("Should not cancel a reservation if ADMIN requested and a request body is missing.")
        void shouldNotCancelAReservationIfRequestBodyIsEmpty() {
            ErrorMessage responseBody = client.testRequest(DELETE, "/reservations", admin, BAD_REQUEST)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.BODY_MISSING.getMessage()");
        }
    }

    private ActionRequest createPostRequestBody(Long userId, String bookBarcode) {
        return new ActionRequest(userId, bookBarcode);
    }

    private MemberDto findMemberById(Long memberId) {
        return client.testRequest(GET, "/members/" + memberId, admin, OK)
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();
    }

    private BookItemDto findBookItemById(Long bookId) {
        return client.testRequest(GET, "/book-items/" + bookId, admin, OK)
                .expectBody(BookItemDto.class)
                .returnResult().getResponseBody();
    }
}
