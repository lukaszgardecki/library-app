package com.example.libraryapp.web.servicedesk;

import com.example.libraryapp.domain.exception.ErrorMessage;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.member.dto.MemberDto;
import com.example.libraryapp.domain.payment.PaymentDescription;
import com.example.libraryapp.domain.payment.PaymentMethod;
import com.example.libraryapp.domain.payment.PaymentStatus;
import com.example.libraryapp.domain.payment.dto.PaymentRequest;
import com.example.libraryapp.domain.payment.dto.PaymentResponse;
import com.example.libraryapp.web.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentControllerTest extends BaseTest {

    @Test
    void shouldReturnAllPaymentsIfCashierRequested() {
        client.get()
                .uri("/api/v1/payments")
                .header(HttpHeaders.AUTHORIZATION, cashierToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.paymentResponseList.length()").isEqualTo(4)
                .jsonPath("$.page.size").isEqualTo(20)
                .jsonPath("$.page.totalElements").isEqualTo(4)
                .jsonPath("$.page.totalPages").isEqualTo(1)
                .jsonPath("$.page.number").isEqualTo(0);
    }

    @Test
    void shouldReturnAllPaymentsIfAdminRequested() {
        client.get()
                .uri("/api/v1/payments")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.paymentResponseList.length()").isEqualTo(4)
                .jsonPath("$.page.size").isEqualTo(20)
                .jsonPath("$.page.totalElements").isEqualTo(4)
                .jsonPath("$.page.totalPages").isEqualTo(1)
                .jsonPath("$.page.number").isEqualTo(0);
    }

    @Test
    void shouldNotReturnAllPaymentsIfUserRequested() {
        client.get()
                .uri("/api/v1/payments")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllPaymentsIfWarehouseRequested() {
        client.get()
                .uri("/api/v1/payments")
                .header(HttpHeaders.AUTHORIZATION, warehouseToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnAllPaymentsIfUnauthenticatedUserRequested() {
        client.get()
                .uri("/api/v1/payments")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldReturnASinglePaymentIfCashierRequested() {
        long paymentId = 1L;
        PaymentResponse returnedPayment = client.get()
                .uri("/api/v1/payments/" + paymentId)
                .header(HttpHeaders.AUTHORIZATION, cashierToken)
                .exchange()
                .expectStatus().isOk()
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
    void shouldReturnASinglePaymentIfAdminRequested() {
        long paymentId = 1L;
        PaymentResponse returnedPayment = client.get()
                .uri("/api/v1/payments/" + paymentId)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
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
    void shouldNotReturnASinglePaymentIfUserRequested() {
        long paymentId = 1L;
        client.get()
                .uri("/api/v1/payments/" + paymentId)
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnASinglePaymentIfWarehouseRequested() {
        long paymentId = 1L;
        client.get()
                .uri("/api/v1/payments/" + paymentId)
                .header(HttpHeaders.AUTHORIZATION, warehouseToken)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotReturnASinglePaymentIfUnauthenticatedUserRequested() {
        long paymentId = 1L;
        client.get()
                .uri("/api/v1/payments/" + paymentId)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldPayAFineIfCashierRequested() {
        PaymentRequest paymentToSave = createPaymentRequest();
        EntityExchangeResult<PaymentResponse> response = client.post()
                .uri("/api/v1/payments/pay-fine")
                .header(HttpHeaders.AUTHORIZATION, cashierToken)
                .body(BodyInserters.fromValue(paymentToSave))
                .exchange()
                .expectStatus().isCreated()
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

        PaymentResponse payment = client.get()
                .uri(response.getResponseHeaders().getLocation())
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PaymentResponse.class)
                .returnResult().getResponseBody();

        assertThat(payment).isEqualTo(returnedPayment);

        MemberDto member = client.get()
                .uri("/api/v1/members/8")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();
        assertThat(member.getCharge()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void shouldPayAFineIfAdminRequested() {
        PaymentRequest paymentToSave = createPaymentRequest();
        EntityExchangeResult<PaymentResponse> response = client.post()
                .uri("/api/v1/payments/pay-fine")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(BodyInserters.fromValue(paymentToSave))
                .exchange()
                .expectStatus().isCreated()
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

        PaymentResponse payment = client.get()
                .uri(response.getResponseHeaders().getLocation())
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PaymentResponse.class)
                .returnResult().getResponseBody();

        assertThat(payment).isEqualTo(returnedPayment);

        MemberDto member = client.get()
                .uri("/api/v1/members/8")
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberDto.class)
                .returnResult().getResponseBody();
        assertThat(member.getCharge()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void shouldNotPayAFineIfUserRequested() {
        PaymentRequest paymentToSave = createPaymentRequest();
        client.post()
                .uri("/api/v1/payments/pay-fine")
                .header(HttpHeaders.AUTHORIZATION, userToken)
                .body(BodyInserters.fromValue(paymentToSave))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotPayAFineIfWarehouseRequested() {
        PaymentRequest paymentToSave = createPaymentRequest();
        client.post()
                .uri("/api/v1/payments/pay-fine")
                .header(HttpHeaders.AUTHORIZATION, warehouseToken)
                .body(BodyInserters.fromValue(paymentToSave))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
    }

    @Test
    void shouldNotPayAFineIfUnauthenticatedUserRequested() {
        PaymentRequest paymentToSave = createPaymentRequest();
        client.post()
                .uri("/api/v1/payments/pay-fine")
                .body(BodyInserters.fromValue(paymentToSave))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class);
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
