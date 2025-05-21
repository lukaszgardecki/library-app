package com.example.libraryapp.OLDweb.servicedesk;

import com.example.libraryapp.OLDdomain.card.CardStatus;
import com.example.libraryapp.OLDdomain.card.dto.CardDto;
import com.example.libraryapp.OLDdomain.exception.ErrorMessage;
import com.example.libraryapp.OLDweb.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;

public class CardControllerTest extends BaseTest {

    @Nested
    @DisplayName("Tests for GET endpoints")
    class GetCardsTests {
        @Test
        @DisplayName("Should return all library cards if CASHIER requested.")
        void shouldReturnAllLibraryCardsIfCashierRequested() {
            client.testRequest(GET, "/cards", cashier, OK)
                    .expectBody()
                    .jsonPath("$._embedded.cardDtoList.length()").isEqualTo(8)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(8)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return all library cards if ADMIN requested.")
        void shouldReturnAllLibraryCardsIfAdminRequested() {
            client.testRequest(GET, "/cards", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.cardDtoList.length()").isEqualTo(8)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(8)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);
        }

        @Test
        @DisplayName("Should not return all library cards if USER requested.")
        void shouldNotReturnAllLibraryCardsIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/cards", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return all library cards if WAREHOUSE requested.")
        void shouldNotReturnAllLibraryCardsIfWarehouseRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/cards", warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return all library cards if an unauthorized USER requested.")
        void shouldNotReturnAllLibraryCardsIfUnauthenticatedUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/cards", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @Test
        @DisplayName("Should return a single library card if CASHIER requested.")
        void shouldReturnASingleLibraryCardIfCashierRequested() {
            long cardId = 7L;
            CardDto returnedCard = client.testRequest(GET, "/cards/" + cardId, cashier, OK)
                    .expectBody(CardDto.class)
                    .returnResult().getResponseBody();
            assertThat(returnedCard.getId()).isEqualTo(cardId);
            assertThat(returnedCard.getBarcode()).isEqualTo("540100000007");
            assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-27 16:07:32", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            assertThat(returnedCard.getStatus()).isEqualTo(CardStatus.ACTIVE);
        }

        @Test
        @DisplayName("Should return a single library card if ADMIN requested.")
        void shouldReturnASingleLibraryCardIfAdminRequested() {
            long cardId = 7L;
            CardDto returnedCard = client.testRequest(GET, "/cards/" + cardId, admin, OK)
                    .expectBody(CardDto.class)
                    .returnResult().getResponseBody();
            assertThat(returnedCard.getId()).isEqualTo(cardId);
            assertThat(returnedCard.getBarcode()).isEqualTo("540100000007");
            assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-27 16:07:32", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            assertThat(returnedCard.getStatus()).isEqualTo(CardStatus.ACTIVE);
        }

        @Test
        @DisplayName("Should not return a single library card if USER requested.")
        void shouldNotReturnASingleLibraryCardIfUserRequested() {
            long cardId = 7L;
            ErrorMessage responseBody = client.testRequest(GET, "/cards/" + cardId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return a single library card if WAREHOUSE requested.")
        void shouldNotReturnASingleLibraryCardIfWarehouseRequested() {
            long cardId = 7L;
            ErrorMessage responseBody = client.testRequest(GET, "/cards/" + cardId, warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return a single library card if an unauthorized USER requested.")
        void shouldNotReturnASingleLibraryCardIfUnauthenticatedUserRequested() {
            long cardId = 7L;
            ErrorMessage responseBody = client.testRequest(GET, "/cards/" + cardId, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for POST endpoints")
    class PostCardsTests {
        @Test
        @DisplayName("Should activate a library card if CASHIER requested.")
        void shouldActivateCardIfCashierRequested() {
            long cardId = 6L;
            CardDto returnedCard = client.testRequest(POST, "/cards/" + cardId + "/activate", cashier, OK)
                    .expectBody(CardDto.class)
                    .returnResult().getResponseBody();
            assertThat(returnedCard.getId()).isEqualTo(cardId);
            assertThat(returnedCard.getBarcode()).isEqualTo("540100000006");
            assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-26 15:06:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            assertThat(returnedCard.getStatus()).isEqualTo(CardStatus.ACTIVE);
        }

        @Test
        @DisplayName("Should activate a library card if ADMIN requested.")
        void shouldActivateCardIfAdminRequested() {
            long cardId = 6L;
            CardDto returnedCard = client.testRequest(POST, "/cards/" + cardId + "/activate", admin, OK)
                    .expectBody(CardDto.class)
                    .returnResult().getResponseBody();
            assertThat(returnedCard.getId()).isEqualTo(cardId);
            assertThat(returnedCard.getBarcode()).isEqualTo("540100000006");
            assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-26 15:06:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            assertThat(returnedCard.getStatus()).isEqualTo(CardStatus.ACTIVE);
        }

        @Test
        @DisplayName("Should not activate a library card if USER requested.")
        void shouldNotActivateCardIfUserRequested() {
            long cardId = 6L;
            ErrorMessage responseBody = client.testRequest(POST, "/cards/" + cardId + "/activate", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not activate a library card if WAREHOUSE requested.")
        void shouldNotActivateCardIfWarehouseRequested() {
            long cardId = 6L;
            ErrorMessage responseBody = client.testRequest(POST, "/cards/" + cardId + "/activate", warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not activate a library card if an unauthorized USER requested.")
        void shouldNotActivateCardIfUnauthenticatedUserRequested() {
            long cardId = 6L;
            ErrorMessage responseBody = client.testRequest(POST, "/cards/" + cardId + "/activate", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @Test
        @DisplayName("Should deactivate a library card if CASHIER requested.")
        void shouldDeactivateCardIfCashierRequested() {
            long cardId = 5L;
            CardDto returnedCard = client.testRequest(POST, "/cards/" + cardId + "/deactivate", cashier, OK)
                    .expectBody(CardDto.class)
                    .returnResult().getResponseBody();
            assertThat(returnedCard.getId()).isEqualTo(cardId);
            assertThat(returnedCard.getBarcode()).isEqualTo("540100000005");
            assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-25 14:05:56", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            assertThat(returnedCard.getStatus()).isEqualTo(CardStatus.INACTIVE);
        }

        @Test
        @DisplayName("Should deactivate a library card if ADMIN requested.")
        void shouldDeactivateCardIfAdminRequested() {
            long cardId = 5L;
            CardDto returnedCard = client.testRequest(POST, "/cards/" + cardId + "/deactivate", admin, OK)
                    .expectBody(CardDto.class)
                    .returnResult().getResponseBody();
            assertThat(returnedCard.getId()).isEqualTo(cardId);
            assertThat(returnedCard.getBarcode()).isEqualTo("540100000005");
            assertThat(returnedCard.getIssuedAt()).isEqualTo(LocalDateTime.parse("2023-05-25 14:05:56", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            assertThat(returnedCard.getStatus()).isEqualTo(CardStatus.INACTIVE);
        }

        @Test
        @DisplayName("Should not deactivate a library card if USER requested.")
        void shouldNotDeactivateCardIfUserRequested() {
            long cardId = 5L;
            ErrorMessage responseBody = client.testRequest(POST, "/cards/" + cardId + "/deactivate", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not deactivate a library card if WAREHOUSE requested.")
        void shouldNotDeactivateCardIfWarehouseRequested() {
            long cardId = 5L;
            ErrorMessage responseBody = client.testRequest(POST, "/cards/" + cardId + "/deactivate", warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not deactivate a library card if an unauthorized USER requested.")
        void shouldNotDeactivateCardIfUnauthenticatedUserRequested() {
            long cardId = 5L;
            ErrorMessage responseBody = client.testRequest(POST, "/cards/" + cardId + "/deactivate", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }
    }
}
