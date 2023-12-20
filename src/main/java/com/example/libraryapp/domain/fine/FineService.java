package com.example.libraryapp.domain.fine;

import com.example.libraryapp.domain.exception.fine.UnsettledFineException;
import com.example.libraryapp.domain.lending.LendingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class FineService {
    public static final BigDecimal DAY_FINE = new BigDecimal("0.2");
    private final LendingRepository lendingRepository;

//    public void count(LocalDate dueDate, LocalDate now) {
//
//        // TODO: 29.11.2023 tu coś nie gra. NALICZ OPŁATĘ ZA JEDNĄ KONKRETNĄ KSIĄZKĘ!
//
//        double totalFine = lendingRepository.findAllByMemberId(memberId).stream()
//                .filter(len -> len.getStatus() == LendingStatus.CURRENT)
//                .filter(len -> now.isAfter(len.getDueDate()))
//                .mapToDouble(len -> countFine(len.getDueDate(), now))
//                .sum();
//        validateTotalFee(totalFine);
//    }

//    public void checkBookForFee(LocalDate dueDate) {
//        LocalDate now = LocalDate.now();
//        double totalFee = countFine(dueDate, now);
//        validateTotalFee(totalFee);
//    }

//    public double payFine(String bookBarcode) {
//        Lending lending = lendingRepository.findCurrentLendingByBarcode(bookBarcode)
//                .orElseThrow();
//        LocalDate now = LocalDate.now();
//        LocalDate dueDate = lending.getDueDate();
//        double fine = countFine(dueDate, now);
//        lending.setDueDate(LocalDate.now());
//        return fine;
//    }

    public BigDecimal countFine(LocalDate dueDate, LocalDate now) {
        BigDecimal diffDays;
        if (now.isAfter(dueDate)) {
            diffDays = BigDecimal.valueOf(getDaysBetween(dueDate, now));
        } else {
            diffDays = BigDecimal.ZERO;
        }
        return diffDays.multiply(DAY_FINE);
    }

    private void validateTotalFee(double totalFee) {
        if (totalFee > 0.0) throw new UnsettledFineException();
    }

    private long getDaysBetween(LocalDate dueDate, LocalDate now) {
        return ChronoUnit.DAYS.between(dueDate, now);
    }
}
