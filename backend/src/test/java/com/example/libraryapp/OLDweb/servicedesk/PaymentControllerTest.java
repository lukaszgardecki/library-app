package com.example.libraryapp.OLDweb.servicedesk;

import com.example.libraryapp.OLDweb.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;

public class PaymentControllerTest extends BaseTest {

    @Nested
    @DisplayName("Tests for GET endpoints")
    class GetPaymentsTests {
        @Test
        @DisplayName("Should return all payments if CASHIER requested.")
        void shouldReturnAllPaymentsIfCashierRequested() {
            client.testRequest(GET, "/payments", cashier, OK)
                    .expectBody()
                    .jsonPath("$._embedded.paymentResponseList.length()").isEqualTo(4)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(4)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return all payments if ADMIN requested.")
        void shouldReturnAllPaymentsIfAdminRequested() {
            client.testRequest(GET, "/payments", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.paymentResponseList.length()").isEqualTo(4)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(4)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return all user's payments if ADMIN requested.")
        void shouldReturnAllUsersPaymentsIfAdminRequested() {
            client.testRequest(GET, "/payments?memberId=1", admin, OK)
                    .expectBody()
                    .jsonPath("$._embedded.paymentResponseList.length()").isEqualTo(2)
                    .jsonPath("$.page.size").isEqualTo(20)
                    .jsonPath("$.page.totalElements").isEqualTo(2)
                    .jsonPath("$.page.totalPages").isEqualTo(1)
                    .jsonPath("$.page.number").isEqualTo(0);
        }

        @Test
        @DisplayName("Should not return all payments if USER requested.")
        void shouldNotReturnAllPaymentsIfUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/payments", user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return all payments if WAREHOUSE requested.")
        void shouldNotReturnAllPaymentsIfWarehouseRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/payments", warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return all payments if an unauthorized USER requested.")
        void shouldNotReturnAllPaymentsIfUnauthenticatedUserRequested() {
            ErrorMessage responseBody = client.testRequest(GET, "/payments", UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }

        @Test
        @DisplayName("Should return a single payment if CASHIER requested.")
        void shouldReturnASinglePaymentIfCashierRequested() {
            long paymentId = 1L;
            PaymentResponse returnedPayment = client.testRequest(GET, "/payments/" + paymentId, cashier, OK)
                    .expectBody(PaymentResponse.class)
                    .returnResult().getResponseBody();

            assertThat(returnedPayment.getId()).isEqualTo(paymentId);
            assertThat(returnedPayment.getAmount()).isEqualTo("24.34");
            assertThat(returnedPayment.getCreationDate()).isEqualTo(LocalDateTime.parse("2023-03-30 13:33:55", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            assertThat(returnedPayment.getMemberId()).isEqualTo(1L);
            assertThat(returnedPayment.getMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
            assertThat(returnedPayment.getStatus()).isEqualTo(PaymentStatus.FAILED);
            assertThat(returnedPayment.getDescription()).isEqualTo(PaymentDescription.FINE_LATE_RETURN.getDescription());
        }

        @Test
        @DisplayName("Should return a single payment if ADMIN requested.")
        void shouldReturnASinglePaymentIfAdminRequested() {
            long paymentId = 1L;
            PaymentResponse returnedPayment = client.testRequest(GET, "/payments/" + paymentId, admin, OK)
                    .expectBody(PaymentResponse.class)
                    .returnResult().getResponseBody();

            assertThat(returnedPayment.getId()).isEqualTo(paymentId);
            assertThat(returnedPayment.getAmount()).isEqualTo("24.34");
            assertThat(returnedPayment.getCreationDate()).isEqualTo(LocalDateTime.parse("2023-03-30 13:33:55", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            assertThat(returnedPayment.getMemberId()).isEqualTo(1L);
            assertThat(returnedPayment.getMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
            assertThat(returnedPayment.getStatus()).isEqualTo(PaymentStatus.FAILED);
            assertThat(returnedPayment.getDescription()).isEqualTo(PaymentDescription.FINE_LATE_RETURN.getDescription());
        }

        @Test
        @DisplayName("Should not return a single payment if USER requested.")
        void shouldNotReturnASinglePaymentIfUserRequested() {
            long paymentId = 1L;
            ErrorMessage responseBody = client.testRequest(GET, "/payments/" + paymentId, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return a single payment if WAREHOUSE requested.")
        void shouldNotReturnASinglePaymentIfWarehouseRequested() {
            long paymentId = 1L;
            ErrorMessage responseBody = client.testRequest(GET, "/payments/" + paymentId, warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not return a single payment if an unauthorized USER requested.")
        void shouldNotReturnASinglePaymentIfUnauthenticatedUserRequested() {
            long paymentId = 1L;
            ErrorMessage responseBody = client.testRequest(GET, "/payments/" + paymentId, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }
    }

    @Nested
    @DisplayName("Tests for POST endpoints")
    class PayFeesTests {
        @Test
        @DisplayName("Should pay a fine if CASHIER requested.")
        void shouldPayAFineIfCashierRequested() {
            PaymentRequest paymentToSave = createPaymentRequest();
            EntityExchangeResult<PaymentResponse> response = client.testRequest(POST, "/payments/pay-fine", paymentToSave, cashier, CREATED)
                    .expectBody(PaymentResponse.class)
                    .returnResult();

            PaymentResponse returnedPayment = response.getResponseBody();
            assertThat(returnedPayment.getId()).isNotNull();
            assertThat(returnedPayment.getAmount()).isEqualTo(paymentToSave.getAmount());
            assertThat(returnedPayment.getMemberId()).isEqualTo(paymentToSave.getMemberId());
            assertThat(returnedPayment.getCreationDate()).isNotNull();
            assertThat(returnedPayment.getStatus()).isNotNull();
            assertThat(returnedPayment.getMethod()).isEqualTo(paymentToSave.getPaymentMethod());
            assertThat(returnedPayment.getDescription()).isEqualTo(paymentToSave.getPaymentDescription().getDescription());

            PaymentResponse payment = client.testRequest(GET, extractURI(response), admin, OK)
                    .expectBody(PaymentResponse.class)
                    .returnResult().getResponseBody();

            assertThat(payment).isEqualTo(returnedPayment);

            MemberDto member = client.testRequest(GET, "/members/8", admin, OK)
                    .expectBody(MemberDto.class)
                    .returnResult().getResponseBody();
            assertThat(member.getCharge()).isEqualTo(new BigDecimal("0.00"));
        }

        @Test
        @DisplayName("Should pay a fine if ADMIN requested.")
        void shouldPayAFineIfAdminRequested() {
            PaymentRequest paymentToSave = createPaymentRequest();
            EntityExchangeResult<PaymentResponse> response = client.testRequest(POST, "/payments/pay-fine", paymentToSave, admin, CREATED)
                    .expectBody(PaymentResponse.class)
                    .returnResult();

            PaymentResponse returnedPayment = response.getResponseBody();
            assertThat(returnedPayment.getId()).isNotNull();
            assertThat(returnedPayment.getAmount()).isEqualTo(paymentToSave.getAmount());
            assertThat(returnedPayment.getMemberId()).isEqualTo(paymentToSave.getMemberId());
            assertThat(returnedPayment.getCreationDate()).isNotNull();
            assertThat(returnedPayment.getStatus()).isNotNull();
            assertThat(returnedPayment.getMethod()).isEqualTo(paymentToSave.getPaymentMethod());
            assertThat(returnedPayment.getDescription()).isEqualTo(paymentToSave.getPaymentDescription().getDescription());

            PaymentResponse payment = client.testRequest(GET, extractURI(response), admin, OK)
                    .expectBody(PaymentResponse.class)
                    .returnResult().getResponseBody();

            assertThat(payment).isEqualTo(returnedPayment);

            MemberDto member = client.testRequest(GET, "/members/8", admin, OK)
                    .expectBody(MemberDto.class)
                    .returnResult().getResponseBody();
            assertThat(member.getCharge()).isEqualTo(new BigDecimal("0.00"));
        }

        @Test
        @DisplayName("Should not pay a fine if USER requested.")
        void shouldNotPayAFineIfUserRequested() {
            PaymentRequest paymentToSave = createPaymentRequest();
            ErrorMessage responseBody = client.testRequest(POST, "/payments/pay-fine", paymentToSave, user, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not pay a fine if WAREHOUSE requested.")
        void shouldNotPayAFineIfWarehouseRequested() {
            PaymentRequest paymentToSave = createPaymentRequest();
            ErrorMessage responseBody = client.testRequest(POST, "/payments/pay-fine", paymentToSave, warehouse, FORBIDDEN)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.FORBIDDEN.getMessage()");
        }

        @Test
        @DisplayName("Should not pay a fine if an unauthorized USER requested.")
        void shouldNotPayAFineIfUnauthenticatedUserRequested() {
            PaymentRequest paymentToSave = createPaymentRequest();
            ErrorMessage responseBody = client.testRequest(POST, "/payments/pay-fine", paymentToSave, UNAUTHORIZED)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();
            assertThat(responseBody.getMessage()).isEqualTo("Message.ACCESS_DENIED.getMessage()");
        }
    }

    private PaymentRequest createPaymentRequest() {
        return PaymentRequest.builder()
                .memberId(8L)
                .amount(new BigDecimal("1.23"))
                .paymentDescription(PaymentDescription.FINE_LATE_RETURN)
                .paymentMethod(PaymentMethod.CASH)
                .build();
    }
}
