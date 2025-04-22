package com.example.libraryapp.infrastructure.http;

import com.example.libraryapp.core.fine.FineFacade;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fines")
@RequiredArgsConstructor
class FineController {
    private final FineFacade fineFacade;

    @GetMapping("/users/{userId}/validation")
    public ResponseEntity<Void> validateUserForFines(@PathVariable Long userId) {
        fineFacade.validateUserForFines(new UserId(userId));
        return ResponseEntity.noContent().build();
    }
}
