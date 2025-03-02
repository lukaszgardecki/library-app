package com.example.libraryapp.adapter;

import com.example.libraryapp.application.librarycard.LibraryCardFacade;
import com.example.libraryapp.domain.librarycard.dto.LibraryCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
@RequiredArgsConstructor
class LibraryCardController {
    private final LibraryCardFacade cardFacade;

    @GetMapping("/{id}")
    public ResponseEntity<LibraryCardDto> getLibraryCard(@PathVariable("id") Long cardId) {
        LibraryCardDto card = cardFacade.getLibraryCard(cardId);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activateCard(@PathVariable("id") Long cardId) {
        cardFacade.activateLibraryCard(cardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> blockCard(@PathVariable("id") Long cardId) {
        cardFacade.blockLibraryCard(cardId);
        return ResponseEntity.ok().build();
    }
}
