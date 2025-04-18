package com.example.libraryapp.application.payment;

import com.example.libraryapp.domain.payment.dto.PaymentCardDetailsDto;
import com.example.libraryapp.domain.payment.dto.PaymentDto;
import com.example.libraryapp.domain.payment.dto.PaymentProcessRequestDto;
import com.example.libraryapp.domain.payment.model.*;
import com.example.libraryapp.domain.user.model.UserId;

class PaymentMapper {

    static Payment toModel(PaymentDto dto) {
        return new Payment(
                new PaymentId(dto.getId()),
                new PaymentAmount(dto.getAmount()),
                new PaymentCreationDate(dto.getCreationDate()),
                new UserId(dto.getUserId()),
                new PaymentDescription(dto.getDescription()),
                dto.getMethod(),
                dto.getStatus()
        );
    }

    static PaymentDto toDto(Payment model) {
        return new PaymentDto(
                model.getId().value(),
                model.getAmount().value(),
                model.getCreationDate().value(),
                model.getUserId().value(),
                model.getDescription().value(),
                model.getMethod(),
                model.getStatus()
        );
    }

    static PaymentProcessRequest toModel(PaymentProcessRequestDto dto) {
        return new PaymentProcessRequest(
                new PaymentAmount(dto.getAmount()),
                new UserId(dto.getUserId()),
                new PaymentDescription(dto.getDescription()),
                dto.getMethod(),
                toModel(dto.getCardPaymentDetails())
        );
    }

    static PaymentProcessRequestDto toDto(PaymentProcessRequest model) {
        return new PaymentProcessRequestDto(
                model.getAmount().value(),
                model.getUserId().value(),
                model.getDescription().value(),
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
