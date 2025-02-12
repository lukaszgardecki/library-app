package com.example.libraryapp.infrastructure.persistence.jpa.librarycard;

import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import com.example.libraryapp.domain.librarycard.model.LibraryCardStatus;
import com.example.libraryapp.domain.librarycard.ports.LibraryCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class LibraryCardRepositoryImpl implements LibraryCardRepository {
    private final JpaLibraryCardRepository repository;

    @Override
    @Transactional
    public LibraryCard save(LibraryCard cardToSave) {
        LibraryCardEntity savedCard = repository.save(toEntity(cardToSave));
        return toModel(savedCard);
    }

    @Override
    public Optional<LibraryCard> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    @Transactional
    public void changeStatusByUserId(LibraryCardStatus status, Long userId) {
        repository.changeStatusByUserId(status, userId);
    }

    private LibraryCardEntity toEntity(LibraryCard model) {
        return LibraryCardEntity.builder()
                .id(model.getId())
                .barcode(model.getBarcode())
                .issuedAt(model.getIssuedAt())
                .status(model.getStatus())
                .build();
    }

    private LibraryCard toModel(LibraryCardEntity entity) {
        return LibraryCard.builder()
                .id(entity.getId())
                .barcode(entity.getBarcode())
                .issuedAt(entity.getIssuedAt())
                .status(entity.getStatus())
                .build();
    }
}
