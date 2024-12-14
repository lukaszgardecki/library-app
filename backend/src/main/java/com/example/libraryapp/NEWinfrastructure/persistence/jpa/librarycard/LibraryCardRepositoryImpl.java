package com.example.libraryapp.NEWinfrastructure.persistence.jpa.librarycard;

import com.example.libraryapp.NEWdomain.user.model.LibraryCard;
import com.example.libraryapp.NEWdomain.user.ports.LibraryCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
