package com.example.libraryapp.application.bookitemloan;

import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class GetTopSubjectsWithLoansCountUseCase {
    private final BookItemLoanService bookItemLoanService;

    Map<String, Long> execute(int limit) {
        List<Object[]> results = bookItemLoanService.getTopSubjectsWithLoansCount(limit);
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Long) result[1],
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }
}
