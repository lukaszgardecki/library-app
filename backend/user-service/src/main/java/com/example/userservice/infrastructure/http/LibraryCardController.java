package com.example.userservice.infrastructure.http;

import com.example.userservice.core.librarycard.LibraryCardFacade;
import com.example.userservice.domain.dto.librarycard.LibraryCardDto;
import com.example.userservice.domain.model.librarycard.LibraryCardId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/cards", produces = MediaType.APPLICATION_JSON_VALUE)
//@PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
@RequiredArgsConstructor
class LibraryCardController {
    private final LibraryCardFacade cardFacade;

    @GetMapping("/{id}")
    public ResponseEntity<LibraryCardDto> getLibraryCard(@PathVariable("id") Long cardId) {
        LibraryCardDto card = cardFacade.getLibraryCard(new LibraryCardId(cardId));
        return ResponseEntity.ok(card);
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activateCard(@PathVariable("id") Long cardId) {
        cardFacade.activateLibraryCard(new LibraryCardId(cardId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> blockCard(@PathVariable("id") Long cardId) {
        cardFacade.blockLibraryCard(new LibraryCardId(cardId));
        return ResponseEntity.ok().build();
    }
}
