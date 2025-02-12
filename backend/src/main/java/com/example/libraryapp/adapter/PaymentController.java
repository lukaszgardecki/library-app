package com.example.libraryapp.adapter;

import com.example.libraryapp.application.payment.PaymentFacade;
import com.example.libraryapp.domain.payment.dto.PaymentDto;
import com.example.libraryapp.domain.payment.dto.PaymentProcessRequestDto;
import com.example.libraryapp.infrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.libraryapp.domain.user.model.Role.ADMIN;
import static com.example.libraryapp.domain.user.model.Role.CASHIER;

@RestController
@RequestMapping(value = "/api/v1/payments")
@RoleAuthorization({CASHIER, ADMIN})
@RequiredArgsConstructor
class PaymentController {
    private final PaymentFacade paymentFacade;

    @GetMapping
    public ResponseEntity<Page<PaymentDto>> getAllByUserId(
            @RequestParam Long userId,
            Pageable pageable
    ) {
        Page<PaymentDto> userPayments = paymentFacade.getAllByUserId(userId, pageable);
        return ResponseEntity.ok(userPayments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable("id") Long paymentId) {
        PaymentDto payment = paymentFacade.getPayment(paymentId);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentDto> processPayment(@RequestBody PaymentProcessRequestDto payRequest) {
        PaymentDto payment = paymentFacade.processPayment(payRequest);
        URI paymentURI = createURI(payment);
        return ResponseEntity.created(paymentURI).body(payment);
    }

    private URI createURI(PaymentDto savedPayment) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/api/v1/payments/{id}")
                .buildAndExpand(savedPayment.getId())
                .toUri();
    }
}
