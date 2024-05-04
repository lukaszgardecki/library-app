package com.example.libraryapp.web;

import com.example.libraryapp.domain.bookItem.BookItemStatus;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.ActionRequest;
import com.example.libraryapp.management.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationControllerTest extends BaseTest {

    @Test
    void shouldReturnAllReservationsIfAdminRequested() {
        client.get()
                .uri("/api/v1/reservations")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.reservationResponseList.length()").isEqualTo(16);

        client.get()
                .uri("/api/v1/reservations?memberId=2")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.reservationResponseList.length()").isEqualTo(2);
    }

    @Test
    void shouldReturnPageOf3ReservationsIfAdminRequested() {
        client.get()
                .uri("/api/v1/reservations?page=1&size=3")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.reservationResponseList.length()").isEqualTo(3)
                .jsonPath("$.page.size").isEqualTo(3)
                .jsonPath("$.page.totalElements").isEqualTo(16)
                .jsonPath("$.page.totalPages").isEqualTo(6)
                .jsonPath("$.page.number").isEqualTo(1);
    }

    @Test
    void shouldReturnAllUsersReservationsIfUserRequestedAndDoesOwnThisData() {
        client.get()
                .uri("/api/v1/reservations?memberId=2")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.page.totalElements").isEqualTo(2);
    }

    @Test
    void shouldNotReturnMembersReservationsIfMemberIdDoesNotExist() {
        client.get()
                .uri("/api/v1/reservations?memberId=99999999")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);

        client.get()
                .uri("/api/v1/reservations?memberId=badrequest")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotReturnAllUsersReservationsIfUserRequestedAndDoesNotOwnThisData(Long memberId) {
        client.get()
                .uri("/api/v1/reservations?memberId=" + memberId)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllUsersReservationsIfUserRequested() {
        client.get()
                .uri("/api/v1/reservations")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllReservationsIfUserIsNotAuthenticated() {
        client.get()
                .uri("/api/v1/reservations")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);

        client.get()
                .uri("/api/v1/reservations?memberId=1")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
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
        ReservationResponse returnedReservation = client.get()
                .uri("/api/v1/reservations/" + reservationId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
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
    void shouldReturnAnExistingReservationIfUserRequestedAndDoesOwnThisData() {
        ReservationResponse returnedReservation = client.get()
                .uri("/api/v1/reservations/5")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReservationResponse.class)
                .returnResult().getResponseBody();

        MemberDto member = findMemberById(2L);
        BookItemDto book = findBookItemById(6L);

        assertThat(returnedReservation.getId()).isEqualTo(5L);
        assertThat(returnedReservation.getCreationDate()).isEqualTo(LocalDate.parse("2023-02-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(returnedReservation.getStatus()).isEqualTo(ReservationStatus.COMPLETED);
        assertThat(returnedReservation.getMember()).isEqualTo(member);
        assertThat(returnedReservation.getBookItem()).isEqualTo(book);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "7", "8", "9", "10"
    })
    void shouldNotReturnAnExistingReservationIfUserRequestedAndDoesNotOwnThisData(Long reservationId) {
        client.get()
                .uri("/api/v1/reservations/" + reservationId)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "57", "510", "99999"
    })
    void shouldNotReturnReservationThatDoesNotExist(Long reservationId) {
        client.get()
                .uri("/api/v1/reservations/" + reservationId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"
    })
    void shouldNotReturnAnExistingReservationIfUserIsNotAuthenticated(Long reservationId) {
        client.get()
                .uri("/api/v1/reservations/" + reservationId)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldMakeAReservationIfUserIsAuthenticated() {
        ActionRequest reservationToSave = createPostRequestBody(2L, "540200000002");
        MemberDto user = findMemberById(2L);
        BookItemDto bookAfterReservation = findBookItemById(2L);

        EntityExchangeResult<ReservationResponse> response = client.post()
                .uri("/api/v1/reservations")
                .body(BodyInserters.fromValue(reservationToSave))
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ReservationResponse.class)
                .returnResult();

        ReservationResponse returnedReservation = client.get()
                .uri(response.getResponseHeaders().getLocation())
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReservationResponse.class)
                .returnResult().getResponseBody();

        assertThat(returnedReservation.getId()).isNotNull();
        assertThat(returnedReservation.getCreationDate()).isNotNull();
        assertThat(returnedReservation.getStatus()).isEqualTo(ReservationStatus.PENDING);
        assertThat(returnedReservation.getMember()).isEqualTo(user);
        assertThat(returnedReservation.getBookItem()).isEqualTo(bookAfterReservation);
    }

    @Test
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndBookIdDoesNotExist() {
        ActionRequest reservationToSave = createPostRequestBody(2L, "540200099999");
        client.post()
                .uri("/api/v1/reservations")
                .body(BodyInserters.fromValue(reservationToSave))
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndUserIdDoesNotExist() {
        ActionRequest reservationToSave = createPostRequestBody(999999999L, "540200000002");
        client.post()
                .uri("/api/v1/reservations")
                .body(BodyInserters.fromValue(reservationToSave))
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotMakeAReservationIfUserHasMaxTotalBooksReserved() {
        ActionRequest reservationToSave = createPostRequestBody(7L, "540200000001");

        for (int i = 1; i <= Constants.MAX_BOOKS_RESERVED_BY_USER; i++) {
            client.post()
                    .uri("/api/v1/reservations")
                    .body(BodyInserters.fromValue(reservationToSave))
                    .header(HttpHeaders.AUTHORIZATION, adminToken)
                    .exchange()
                    .expectStatus().isCreated();

            reservationToSave = createPostRequestBody(7L, "54020000000" + (i + 1));
        }

        client.post()
                .uri("/api/v1/reservations")
                .body(BodyInserters.fromValue(reservationToSave))
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "3", "4", "5", "6"
    })
    void shouldNotMakeAReservationIfUserIsAuthenticatedButUserIdIsNotTheir(Long userId) {
        ActionRequest reservationToSave = createPostRequestBody(userId, "540200000002");
        client.post()
                .uri("/api/v1/reservations")
                .body(BodyInserters.fromValue(reservationToSave))
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "540200000061",
            "540200000062"
    })
    void shouldNotMakeAReservationIfUserIsAuthenticatedAndBookItemIsLost(String bookBarcode) {
        ActionRequest reservationToSave = createPostRequestBody(2L, bookBarcode);
        client.post()
                .uri("/api/v1/reservations")
                .body(BodyInserters.fromValue(reservationToSave))
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotMakeAReservationIfUserIsNotAuthenticated() {
        ActionRequest reservationToSave = createPostRequestBody(2L, "540200000002");
        client.post()
                .uri("/api/v1/reservations")
                .body(BodyInserters.fromValue(reservationToSave))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotMakeAReservationIfRequestBodyIsEmpty() {
        client.post()
                .uri("/api/v1/reservations")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 1, 4, 540200000004, false",
            "4, 1, 5, 540200000005, false",
            "6, 2, 7, 540200000007, false",
            "12, 3, 13, 540200000013, false",
            "13, 4, 1, 540200000001, false",
            "14, 5, 21, 540200000021, false",
            "15, 6, 3, 540200000003, false",
            "16, 6, 8, 540200000008, false",
    })
    void shouldCancelAReservationIfAdminRequested(
            Long reservationId,
            Long memberId,
            Long bookItemId,
            String bookBarcode,
            Boolean isReservedBySomeoneElse
    ) {
        ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
        MemberDto memberBeforeResCanceling = findMemberById(memberId);

        client.method(HttpMethod.DELETE)
                .uri("/api/v1/reservations")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(reservationToCancel))
                .exchange()
                .expectStatus().isNoContent();

        ReservationResponse returnedReservation = client.get()
                .uri("/api/v1/reservations/" + reservationId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReservationResponse.class)
                .returnResult().getResponseBody();

        assertThat(returnedReservation.getStatus()).isEqualTo(ReservationStatus.CANCELED);

        MemberDto memberAfterResCanceling = findMemberById(memberId);
        assertThat(memberBeforeResCanceling.getTotalBooksReserved()).isEqualTo(memberAfterResCanceling.getTotalBooksReserved() + 1);

        BookItemDto bookItemAfterResCanceling = findBookItemById(bookItemId);
        if (isReservedBySomeoneElse) {
            assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.RESERVED, BookItemStatus.LOANED);
        } else {
            assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.AVAILABLE, BookItemStatus.LOANED);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "6, 2, 7, 540200000007, false"
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

        client.method(HttpMethod.DELETE)
                .uri("/api/v1/reservations")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(reservationToCancel))
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/api/v1/reservations/" + reservationId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReservationResponse.class);

        MemberDto memberAfterResCanceling = findMemberById(memberId);
        assertThat(memberBeforeResCanceling.getTotalBooksReserved()).isEqualTo(memberAfterResCanceling.getTotalBooksReserved() + 1);

        BookItemDto bookItemAfterResCanceling = findBookItemById(bookItemId);
        if (isReservedBySomeoneElse) {
            assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.RESERVED, BookItemStatus.LOANED);
        } else {
            assertThat(bookItemAfterResCanceling.getStatus()).isIn(BookItemStatus.AVAILABLE, BookItemStatus.LOANED);
        }
    }

    @ParameterizedTest
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

        ReservationResponse reservationBeforeCanceling = client.get()
                .uri("/api/v1/reservations/" + reservationId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReservationResponse.class)
                .returnResult().getResponseBody();

        client.method(HttpMethod.DELETE)
                .uri("/api/v1/reservations")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(reservationToCancel))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);

        ReservationResponse reservationAfterCanceling = client.get()
                .uri("/api/v1/reservations/" + reservationId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReservationResponse.class)
                .returnResult().getResponseBody();

        MemberDto memberAfterResCanceling = findMemberById(memberId);
        BookItemDto bookItemAfterResCanceling = findBookItemById(bookItemId);

        assertThat(reservationBeforeCanceling).isEqualTo(reservationAfterCanceling);
        assertThat(memberBeforeResCanceling).isEqualTo(memberAfterResCanceling);
        assertThat(bookItemBeforeResCanceling).isEqualTo(bookItemAfterResCanceling);
    }

    @Test
    void shouldNotCancelAReservationIfUserIsNotAuthenticated() {
        ActionRequest reservationToCancel = createPostRequestBody(1L, "540200000004");
        client.method(HttpMethod.DELETE)
                .uri("/api/v1/reservations")
                .body(BodyInserters.fromValue(reservationToCancel))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 540200099999",
            "999, 54020000002",
    })
    void shouldNotCancelAReservationThatDoesNotExist(Long memberId, String bookBarcode) {
        ActionRequest reservationToCancel = createPostRequestBody(memberId, bookBarcode);
        client.method(HttpMethod.DELETE)
                .uri("/api/v1/reservations")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(reservationToCancel))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotCancelAReservationIfRequestBodyIsEmpty() {
        client.method(HttpMethod.DELETE)
                .uri("/api/v1/reservations")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class);
    }

    private ActionRequest createPostRequestBody(Long userId, String bookBarcode) {
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
