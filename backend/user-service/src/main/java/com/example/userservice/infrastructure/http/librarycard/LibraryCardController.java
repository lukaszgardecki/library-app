package com.example.userservice.infrastructure.http.librarycard;

import com.example.userservice.core.librarycard.LibraryCardFacade;
import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.infrastructure.http.librarycard.dto.LibraryCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
class LibraryCardController {
    private final LibraryCardFacade cardFacade;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER', 'USER')")
    ResponseEntity<LibraryCardDto> getLibraryCard(@PathVariable("id") Long cardId) {
        LibraryCardDto card = LibraryCardMapper.toDto(cardFacade.getLibraryCard(new LibraryCardId(cardId)));
        return ResponseEntity.ok(card);
    }

    @PostMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    ResponseEntity<Void> activateCard(@PathVariable("id") Long cardId) {
        cardFacade.activateLibraryCard(new LibraryCardId(cardId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    ResponseEntity<Void> blockCard(@PathVariable("id") Long cardId) {
        cardFacade.blockLibraryCard(new LibraryCardId(cardId));
        return ResponseEntity.ok().build();
    }
}
