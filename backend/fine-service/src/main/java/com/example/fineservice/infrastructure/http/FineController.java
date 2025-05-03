package com.example.fineservice.infrastructure.http;

import com.example.fineservice.core.FineFacade;
import com.example.fineservice.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fines")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
class FineController {
    private final FineFacade fineFacade;

    @GetMapping("/users/{userId}/validation")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER') or #userId == principal")
    ResponseEntity<Void> verifyUserForFines(@PathVariable Long userId) {
        fineFacade.verifyUserForFines(new UserId(userId));
        return ResponseEntity.noContent().build();
    }
}
