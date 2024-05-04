package com.example.libraryapp.web.servicedesk;

import com.example.libraryapp.domain.payment.PaymentService;
import com.example.libraryapp.domain.payment.dto.PaymentRequest;
import com.example.libraryapp.domain.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/payments")
@PreAuthorize("hasAnyRole('CASHIER', 'ADMIN')")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<PagedModel<PaymentResponse>> findAllPayments(Pageable pageable) {
        PagedModel<PaymentResponse> collectionModel = paymentService.findAllPayments(pageable);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> findPaymentById(@PathVariable("id") Long paymentId) {
        PaymentResponse payment = paymentService.findPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/pay-fine")
    public ResponseEntity<PaymentResponse> payAFine(@RequestBody PaymentRequest payRequest) {
        PaymentResponse paidFine = paymentService.payFine(payRequest);
        URI paymentURI = createURI(paidFine);
        return ResponseEntity.created(paymentURI).body(paidFine);
    }

    private URI createURI(PaymentResponse savedPayment) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/api/v1/payments/{id}")
                .buildAndExpand(savedPayment.getId())
                .toUri();
    }
}
