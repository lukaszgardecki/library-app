package com.example.libraryapp.domain.payment;

import com.example.libraryapp.domain.config.assembler.PaymentModelAssembler;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.payment.PaymentNotFoundException;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.domain.member.MemberRepository;
import com.example.libraryapp.domain.payment.dto.PaymentRequest;
import com.example.libraryapp.domain.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final PaymentModelAssembler paymentModelAssembler;
    private final PagedResourcesAssembler<Payment> pagedResourcesAssembler;

    public PagedModel<PaymentResponse> findAllPayments(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(payments, paymentModelAssembler);
    }

    public PaymentResponse findPaymentById(Long paymentId) {
        Payment payment = findPayment(paymentId);
        return paymentModelAssembler.toModel(payment);
    }

    @Transactional
    public PaymentResponse payFine(PaymentRequest paymentRequest) {
        Member member = findMember(paymentRequest.getMemberId());
        PaymentStatus status = PaymentProcessor.process(paymentRequest);

        Payment paymentToSave = Payment.builder()
                .amount(paymentRequest.getAmount())
                .creationDate(LocalDateTime.now())
                .member(member)
                .method(paymentRequest.getPaymentMethod())
                .status(status)
                .description(paymentRequest.getPaymentDescription().getDescription())
                .build();

        Payment savedPayment = paymentRepository.save(paymentToSave);
        updateMemberFees(member, savedPayment);
        return paymentModelAssembler.toModel(savedPayment);
    }

    private void updateMemberFees(Member member, Payment payment) {
        BigDecimal charge = member.getCharge();
        BigDecimal amount = payment.getAmount();
        member.setCharge(charge.subtract(amount));
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    private Payment findPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }
}
