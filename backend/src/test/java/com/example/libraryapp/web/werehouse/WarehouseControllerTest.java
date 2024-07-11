package com.example.libraryapp.web.werehouse;

import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.Message;
import com.example.libraryapp.web.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;

public class WarehouseControllerTest extends BaseTest {

    @Nested
    @DisplayName("Tests for GET endpoints")
    class GetWarehouseReservationsTests {
        @Test
        @DisplayName("Should return all pending reservations if WAREHOUSE requested.")
        void shouldReturnPendingReservationsIfWarehouseRequested() {
            client.testRequest(GET, "/warehouse/reservations/pending", warehouse, OK)
                    .expectBodyList(ReservationResponse.class)
                    .hasSize(4);
        }

        @Test
        @DisplayName("Should return all pending reservations if ADMIN requested.")
        void shouldReturnPendingReservationsIfAdminRequested() {
            client.testRequest(GET, "/warehouse/reservations/pending", admin, OK)
                    .expectBodyList(ReservationResponse.class)
                    .hasSize(4);
        }

        @Test
        @DisplayName("Should not return all pending reservations if USER requested.")
        void shouldNotReturnPendingReservationsIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/warehouse/reservations/pending", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN.getMessage());
        }

        @Test
        @DisplayName("Should not return all pending reservations if an unauthorized USER requested.")
        void shouldNotReturnPendingReservationsIfUnauthenticatedUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/warehouse/reservations/pending", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests for POST endpoints")
    class PostWarehouseReservationsTests {
        @ParameterizedTest
        @DisplayName("Should complete a pending reservation if WAREHOUSE requested.")
        @CsvSource({
                "13", "14", "15", "16"
        })
        void shouldCompleteTheReservationIfWarehouseRequested(Long reservationId) {
            ReservationResponse updatedReservation = client.testRequest(POST, "/warehouse/reservations/" + reservationId + "/ready", warehouse, CREATED)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            ReservationResponse reservationGet = client.testRequest(GET, "/reservations/" + reservationId, admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            assertThat(updatedReservation.getStatus()).isEqualTo(reservationGet.getStatus());
            assertThat(updatedReservation.getStatus()).isEqualTo(ReservationStatus.READY);
            assertThat(reservationGet.getStatus()).isEqualTo(ReservationStatus.READY);
        }

        @ParameterizedTest
        @DisplayName("Should complete a pending reservation if ADMIN requested.")
        @CsvSource({
                "13", "14", "15", "16"
        })
        void shouldCompleteTheReservationIfAdminRequested(Long reservationId) {
            ReservationResponse updatedReservation = client.testRequest(POST, "/warehouse/reservations/" + reservationId + "/ready", admin, CREATED)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            ReservationResponse reservationGet = client.testRequest(GET, "/reservations/" + reservationId, admin, OK)
                    .expectBody(ReservationResponse.class)
                    .returnResult().getResponseBody();

            assertThat(updatedReservation.getStatus()).isEqualTo(reservationGet.getStatus());
            assertThat(updatedReservation.getStatus()).isEqualTo(ReservationStatus.READY);
            assertThat(reservationGet.getStatus()).isEqualTo(ReservationStatus.READY);
        }

        @ParameterizedTest
        @DisplayName("Should not complete a pending reservation if USER requested.")
        @CsvSource({
                "13", "14", "15", "16"
        })
        void shouldNotCompleteTheReservationIfUserRequested(Long reservationId) {
            ErrorMessage responseBody = client.testRequest(POST, "/warehouse/reservations/" + reservationId + "/ready", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.FORBIDDEN.getMessage());
        }

        @ParameterizedTest
        @DisplayName("Should not complete a pending reservation if an unauthorized USER requested.")
        @CsvSource({
                "13", "14", "15", "16"
        })
        void shouldNotCompleteTheReservationIfUnauthenticatedUserRequested(Long reservationId) {
            ErrorMessage responseBody = client.testRequest(POST, "/warehouse/reservations/" + reservationId + "/ready", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.ACCESS_DENIED.getMessage());
        }

        @ParameterizedTest
        @DisplayName("Should not complete a pending reservation if WAREHOUSE requested and its ID doesn't exist.")
        @CsvSource({
                "111", "500", "15555", "0000000000"
        })
        void shouldNotCompleteTheReservationIfWarehouseRequestedAndIdDoesNotExist(Long reservationId) {
            ErrorMessage responseBody = client.testRequest(POST, "/warehouse/reservations/" + reservationId + "/ready", warehouse, NOT_FOUND)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo(Message.RESERVATION_NOT_FOUND_ID.getMessage(reservationId));
        }
    }
}
