package com.example.libraryapp.infrastructure.persistence.jpa.librarycard;

import com.example.libraryapp.domain.librarycard.model.*;
import com.example.libraryapp.domain.librarycard.ports.LibraryCardRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class LibraryCardRepositoryAdapter implements LibraryCardRepositoryPort {
    private final JpaLibraryCardRepository repository;

    @Override
    @Transactional
    public LibraryCard save(LibraryCard cardToSave) {
        LibraryCardEntity savedCard = repository.save(toEntity(cardToSave));
        return toModel(savedCard);
    }

    @Override
    public Optional<LibraryCard> findById(LibraryCardId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    @Transactional
    public void changeStatusById(LibraryCardStatus status, LibraryCardId cardId) {
        repository.changeStatusByUserId(status, cardId.value());
    }

    private LibraryCardEntity toEntity(LibraryCard model) {
        return LibraryCardEntity.builder()
                .id(model.getId() != null ? model.getId().value() : null)
                .barcode(model.getBarcode() != null ? model.getBarcode().value() : null)
                .issuedAt(model.getIssuedAt().value())
                .status(model.getStatus())
                .userId(model.getUserId().value())
                .build();
    }

    private LibraryCard toModel(LibraryCardEntity entity) {
        return LibraryCard.builder()
                .id(new LibraryCardId(entity.getId()))
                .barcode(new LibraryCardBarcode(entity.getBarcode()))
                .issuedAt(new IssuedDate(entity.getIssuedAt()))
                .status(entity.getStatus())
                .userId(new UserId(entity.getUserId()))
                .build();
    }
}
