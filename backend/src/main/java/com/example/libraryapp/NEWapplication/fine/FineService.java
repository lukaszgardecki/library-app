package com.example.libraryapp.NEWapplication.fine;

import com.example.libraryapp.NEWdomain.fine.model.Fine;
import com.example.libraryapp.NEWdomain.fine.ports.FineRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
class FineService {
    private final FineRepository fineRepository;

    List<Fine> getAllByUserId(Long userId) {
        return fineRepository.getAllByUserId(userId);
    }

    public void processFineForBookReturn(LocalDateTime returnDate, LocalDateTime dueDate, Long userId, Long bookItemLoanId) {
        BigDecimal fine = FineCalculator.calculateFine(returnDate, dueDate);
        if (fine.compareTo(BigDecimal.ZERO) > 0) {
            Fine fineToSave = Fine.builder()
                    .amount(fine)
                    .userId(userId)
                    .loanId(bookItemLoanId)
                    .paid(false)
                    .build();
            fineRepository.save(fineToSave);
        }
    }
}
