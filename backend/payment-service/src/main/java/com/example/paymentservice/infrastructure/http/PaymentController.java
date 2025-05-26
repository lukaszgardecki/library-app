package com.example.paymentservice.infrastructure.http;

import com.example.paymentservice.core.PaymentFacade;
import com.example.paymentservice.domain.model.values.PaymentId;
import com.example.paymentservice.domain.model.values.UserId;
import com.example.paymentservice.infrastructure.http.dto.PaymentDto;
import com.example.paymentservice.infrastructure.http.dto.PaymentProcessRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/payments")
@RequiredArgsConstructor
class PaymentController {
    private final PaymentFacade paymentFacade;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER', 'USER') or #userId == principal")
    ResponseEntity<Page<PaymentDto>> getAllByUserId(
            @RequestParam(name = "user_id") Long userId,
            Pageable pageable
    ) {
        Page<PaymentDto> userPayments = paymentFacade.getAllByUserId(new UserId(userId), pageable)
                .map(PaymentMapper::toDto);
        return ResponseEntity.ok(userPayments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    ResponseEntity<PaymentDto> getPaymentById(@PathVariable("id") Long paymentId) {
        PaymentDto payment = PaymentMapper.toDto(paymentFacade.getPayment(new PaymentId(paymentId)));
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/process")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    ResponseEntity<PaymentDto> processPayment(@RequestBody PaymentProcessRequestDto payRequest) {
        PaymentDto payment = PaymentMapper.toDto(
                paymentFacade.processPayment(PaymentMapper.toModel(payRequest))
        );
        URI paymentURI = createURI(payment);
        return ResponseEntity.created(paymentURI).body(payment);
    }

    private URI createURI(PaymentDto payment) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/api/v1/payments/{id}")
                .buildAndExpand(payment.getId())
                .toUri();
    }
}
