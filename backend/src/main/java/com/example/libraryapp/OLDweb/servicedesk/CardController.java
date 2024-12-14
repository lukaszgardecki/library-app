package com.example.libraryapp.OLDweb.servicedesk;

import com.example.libraryapp.OLDdomain.card.CardService;
import com.example.libraryapp.OLDdomain.card.dto.CardDto;
import com.example.libraryapp.NEWinfrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.libraryapp.OLDdomain.member.Role.ADMIN;
import static com.example.libraryapp.OLDdomain.member.Role.CASHIER;

@RestController
@RequestMapping(value = "/api/v1/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@RoleAuthorization({ADMIN, CASHIER})
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping
    public ResponseEntity<PagedModel<CardDto>> getAllCards(Pageable pageable) {
        PagedModel<CardDto> allCards = cardService.findAllCards(pageable);
        return ResponseEntity.ok(allCards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getSingleCard(@PathVariable("id") Long cardId) {
        CardDto card = cardService.findCardById(cardId);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<CardDto> activateCard(@PathVariable("id") Long cardId) {
        CardDto activatedCard = cardService.activateCard(cardId);
        return ResponseEntity.ok(activatedCard);
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<CardDto> blockCard(@PathVariable("id") Long cardId) {
        CardDto activatedCard = cardService.blockCard(cardId);
        return ResponseEntity.ok(activatedCard);
    }
}
