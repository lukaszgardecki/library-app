package com.example.libraryapp.web.werehouse;

import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.domain.reservation.ReservationStatus;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.web.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseControllerTest extends BaseTest {

    @Test
    void shouldReturnPendingReservationsIfWarehouseRequested() {
        client.get()
                .uri("/api/v1/warehouse/reservations/pending")
                .header(HttpHeaders.AUTHORIZATION, warehouseToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.reservationResponseList.length()").isEqualTo(4)
                .jsonPath("$.page.size").isEqualTo(4)
                .jsonPath("$.page.totalElements").isEqualTo(4)
                .jsonPath("$.page.totalPages").isEqualTo(1)
                .jsonPath("$.page.number").isEqualTo(0);
    }

    @Test
    void shouldNotReturnPendingReservationsIfUserRequested() {
        client.get()
                .uri("/api/v1/warehouse/reservations/pending")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnPendingReservationsIfUnauthenticatedUserRequested() {
        client.get()
                .uri("/api/v1/warehouse/reservations/pending")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "13", "14", "15", "16"
    })
    void shouldCompleteTheReservationIfWarehouseRequested(Long reservationId) {
        ReservationResponse updatedReservation = client.post()
                .uri("/api/v1/warehouse/reservations/" + reservationId + "/ready")
                .header(HttpHeaders.AUTHORIZATION, warehouseToken)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ReservationResponse.class)
                .returnResult().getResponseBody();

        ReservationResponse reservationGet = client.get()
                .uri("/api/v1/reservations/" + reservationId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReservationResponse.class)
                .returnResult().getResponseBody();

        assertThat(updatedReservation.getStatus()).isEqualTo(reservationGet.getStatus());
        assertThat(updatedReservation.getStatus()).isEqualTo(ReservationStatus.READY);
        assertThat(reservationGet.getStatus()).isEqualTo(ReservationStatus.READY);
    }

    @ParameterizedTest
    @CsvSource({
            "13", "14", "15", "16"
    })
    void shouldNotCompleteTheReservationIfUserRequested(Long reservationId) {
        client.post()
                .uri("/api/v1/warehouse/reservations/" + reservationId + "/ready")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "13", "14", "15", "16"
    })
    void shouldNotCompleteTheReservationIfUnauthenticatedUserRequested(Long reservationId) {
        client.post()
                .uri("/api/v1/warehouse/reservations/" + reservationId + "/ready")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @ParameterizedTest
    @CsvSource({
            "111", "500", "15555", "0000000000"
    })
    void shouldNotCompleteTheReservationIfWarehouseRequestedAndIdDoesNotExist(Long reservationId) {
        client.post()
                .uri("/api/v1/warehouse/reservations/" + reservationId + "/ready")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }
}
