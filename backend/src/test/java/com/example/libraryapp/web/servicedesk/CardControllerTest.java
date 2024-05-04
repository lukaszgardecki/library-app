package com.example.libraryapp.web.servicedesk;

import com.example.libraryapp.domain.card.dto.CardDto;
import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.web.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class CardControllerTest extends BaseTest {

    @Test
    void shouldReturnAllLibraryCardsIfCashierRequested() {
        client.get()
                .uri("/api/v1/cards")
                .header(HttpHeaders.AUTHORIZATION, cashierToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.cardDtoList.length()").isEqualTo(8)
                .jsonPath("$.page.size").isEqualTo(20)
                .jsonPath("$.page.totalElements").isEqualTo(8)
                .jsonPath("$.page.totalPages").isEqualTo(1)
                .jsonPath("$.page.number").isEqualTo(0);
    }

    @Test
    void shouldReturnAllLibraryCardsIfAdminRequested() {
        client.get()
                .uri("/api/v1/cards")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.cardDtoList.length()").isEqualTo(8)
                .jsonPath("$.page.size").isEqualTo(20)
                .jsonPath("$.page.totalElements").isEqualTo(8)
                .jsonPath("$.page.totalPages").isEqualTo(1)
                .jsonPath("$.page.number").isEqualTo(0);
    }

    @Test
    void shouldNotReturnAllLibraryCardsIfUserRequested() {
        client.get()
                .uri("/api/v1/cards")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllLibraryCardsIfWarehouseRequested() {
        client.get()
                .uri("/api/v1/cards")
                .header(HttpHeaders.AUTHORIZATION, warehouseToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllLibraryCardsIfUnauthenticatedUserRequested() {
        client.get()
                .uri("/api/v1/cards")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldReturnASingleLibraryCardIfCashierRequested() {
        long cardId = 7L;
        CardDto returnedCard = client.get()
                .uri("/api/v1/cards/" + cardId)
                .header(HttpHeaders.AUTHORIZATION, cashierToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CardDto.class)
                .returnResult().getResponseBody();
        assertThat(returnedCard.getId()).isEqualTo(cardId);
        assertThat(returnedCard.getBarcode()).isEqualTo("540100000007");
        assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-27 16:07:32", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(returnedCard.isActive()).isEqualTo(true);
    }

    @Test
    void shouldReturnASingleLibraryCardIfAdminRequested() {
        long cardId = 7L;
        CardDto returnedCard = client.get()
                .uri("/api/v1/cards/" + cardId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CardDto.class)
                .returnResult().getResponseBody();
        assertThat(returnedCard.getId()).isEqualTo(cardId);
        assertThat(returnedCard.getBarcode()).isEqualTo("540100000007");
        assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-27 16:07:32", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(returnedCard.isActive()).isEqualTo(true);
    }

    @Test
    void shouldNotReturnASingleLibraryCardIfUserRequested() {
        long cardId = 7L;
        client.get()
                .uri("/api/v1/cards/" + cardId)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnASingleLibraryCardIfWarehouseRequested() {
        long cardId = 7L;
        client.get()
                .uri("/api/v1/cards/" + cardId)
                .header(HttpHeaders.AUTHORIZATION, warehouseToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnASingleLibraryCardIfUnauthenticatedUserRequested() {
        long cardId = 7L;
        client.get()
                .uri("/api/v1/cards/" + cardId)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldActivateCardIfCashierRequested() {
        long cardId = 6L;
        CardDto returnedCard = client.post()
                .uri("/api/v1/cards/" + cardId + "/activate")
                .header(HttpHeaders.AUTHORIZATION, cashierToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CardDto.class)
                .returnResult().getResponseBody();
        assertThat(returnedCard.getId()).isEqualTo(cardId);
        assertThat(returnedCard.getBarcode()).isEqualTo("540100000006");
        assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-26 15:06:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(returnedCard.isActive()).isEqualTo(true);
    }

    @Test
    void shouldActivateCardIfAdminRequested() {
        long cardId = 6L;
        CardDto returnedCard = client.post()
                .uri("/api/v1/cards/" + cardId + "/activate")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CardDto.class)
                .returnResult().getResponseBody();
        assertThat(returnedCard.getId()).isEqualTo(cardId);
        assertThat(returnedCard.getBarcode()).isEqualTo("540100000006");
        assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-26 15:06:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(returnedCard.isActive()).isEqualTo(true);
    }

    @Test
    void shouldNotActivateCardIfUserRequested() {
        long cardId = 6L;
        client.post()
                .uri("/api/v1/cards/" + cardId + "/activate")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotActivateCardIfWarehouseRequested() {
        long cardId = 6L;
        client.post()
                .uri("/api/v1/cards/" + cardId + "/activate")
                .header(HttpHeaders.AUTHORIZATION, warehouseToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotActivateCardIfUnauthenticatedUserRequested() {
        long cardId = 6L;
        client.post()
                .uri("/api/v1/cards/" + cardId + "/activate")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldDeactivateCardIfCashierRequested() {
        long cardId = 5L;
        CardDto returnedCard = client.post()
                .uri("/api/v1/cards/" + cardId + "/deactivate")
                .header(HttpHeaders.AUTHORIZATION, cashierToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CardDto.class)
                .returnResult().getResponseBody();
        assertThat(returnedCard.getId()).isEqualTo(cardId);
        assertThat(returnedCard.getBarcode()).isEqualTo("540100000005");
        assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-25 14:05:56", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(returnedCard.isActive()).isEqualTo(false);
    }

    @Test
    void shouldDeactivateCardIfAdminRequested() {
        long cardId = 5L;
        CardDto returnedCard = client.post()
                .uri("/api/v1/cards/" + cardId + "/deactivate")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CardDto.class)
                .returnResult().getResponseBody();
        assertThat(returnedCard.getId()).isEqualTo(cardId);
        assertThat(returnedCard.getBarcode()).isEqualTo("540100000005");
        assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-25 14:05:56", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(returnedCard.isActive()).isEqualTo(false);
    }

    @Test
    void shouldNotDeactivateCardIfUserRequested() {
        long cardId = 5L;
        client.post()
                .uri("/api/v1/cards/" + cardId + "/deactivate")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotDeactivateCardIfWarehouseRequested() {
        long cardId = 5L;
        client.post()
                .uri("/api/v1/cards/" + cardId + "/deactivate")
                .header(HttpHeaders.AUTHORIZATION, warehouseToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotDeactivateCardIfUnauthenticatedUserRequested() {
        long cardId = 5L;
        client.post()
                .uri("/api/v1/cards/" + cardId + "/deactivate")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }
}
