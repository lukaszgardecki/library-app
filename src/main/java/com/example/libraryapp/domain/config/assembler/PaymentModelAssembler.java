package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.payment.Payment;
import com.example.libraryapp.domain.payment.PaymentMapper;
import com.example.libraryapp.domain.payment.dto.PaymentResponse;
import com.example.libraryapp.web.servicedesk.PaymentController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class PaymentModelAssembler extends RepresentationModelAssemblerSupport<Payment, PaymentResponse> {

    public PaymentModelAssembler() {
        super(PaymentController.class, PaymentResponse.class);
    }

    @Override
    @NonNull
    public PaymentResponse toModel(@NonNull Payment payment) {
        PaymentResponse paymentDto = PaymentMapper.map(payment);
        paymentDto.add(linkTo(methodOn(PaymentController.class).findPaymentById(payment.getId())).withSelfRel());
        paymentDto.add(linkTo(methodOn(PaymentController.class).findAllPayments(null)).withRel(IanaLinkRelations.COLLECTION));
        return paymentDto;
    }

    @Override
    @NonNull
    public CollectionModel<PaymentResponse> toCollectionModel(@NonNull Iterable<? extends Payment> entities) {
        CollectionModel<PaymentResponse> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(PaymentController.class).findAllPayments(null)).withSelfRel());
        return collectionModel;
    }
}
