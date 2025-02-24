package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.payment.model.Payment;
import com.example.libraryapp.domain.payment.ports.PaymentRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPaymentRepositoryAdapter implements PaymentRepositoryPort {
    private final ConcurrentHashMap<Long, Payment> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Page<Payment> findAllByUserId(Long userId, Pageable pageable) {
        List<Payment> filteredPayments = map.values().stream()
                .filter(payment -> payment.getUserId().equals(userId))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredPayments.size());
        List<Payment> pageContent = filteredPayments.subList(start, end);
        return new PageImpl<>(pageContent, pageable, filteredPayments.size());
    }


    @Override
    public Optional<Payment> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }


    @Override
    public Payment save(Payment payment) {
        if (payment.getId() == null) {
            payment.setId(++id);
        }
        return map.put(payment.getId(), payment);
    }

}
