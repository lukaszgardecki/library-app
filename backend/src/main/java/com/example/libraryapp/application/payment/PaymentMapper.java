package com.example.libraryapp.application.payment;

import com.example.libraryapp.domain.payment.dto.PaymentCardDetailsDto;
import com.example.libraryapp.domain.payment.dto.PaymentDto;
import com.example.libraryapp.domain.payment.dto.PaymentProcessRequestDto;
import com.example.libraryapp.domain.payment.model.Payment;
import com.example.libraryapp.domain.payment.model.PaymentCardDetails;
import com.example.libraryapp.domain.payment.model.PaymentProcessRequest;

class PaymentMapper {

    static Payment toModel(PaymentDto dto) {
        return new Payment(
                dto.getId(),
                dto.getAmount(),
                dto.getCreationDate(),
                dto.getUserId(),
                dto.getDescription(),
                dto.getMethod(),
                dto.getStatus()
        );
    }

    static PaymentDto toDto(Payment model) {
        return new PaymentDto(
                model.getId(),
                model.getAmount(),
                model.getCreationDate(),
                model.getUserId(),
                model.getDescription(),
                model.getMethod(),
                model.getStatus()
        );
    }

    static PaymentProcessRequest toModel(PaymentProcessRequestDto dto) {
        return new PaymentProcessRequest(
                dto.getAmount(),
                dto.getUserId(),
                dto.getDescription(),
                dto.getMethod(),
                toModel(dto.getCardPaymentDetails())
        );
    }

    static PaymentProcessRequestDto toDto(PaymentProcessRequest model) {
        return new PaymentProcessRequestDto(
                model.getAmount(),
                model.getUserId(),
                model.getDescription(),
                model.getMethod(),
                toDto(model.getCardPaymentDetails())
        );
    }

    static PaymentCardDetails toModel(PaymentCardDetailsDto dto) {
        return new PaymentCardDetails(
                dto.getCardNumber(),
                dto.getCardHolderName(),
                dto.getExpirationDate(),
                dto.getCvv()
        );
    }

    static PaymentCardDetailsDto toDto(PaymentCardDetails model) {
        return new PaymentCardDetailsDto(
                model.getCardNumber(),
                model.getCardHolderName(),
                model.getExpirationDate(),
                model.getCvv()
        );
    }
}
