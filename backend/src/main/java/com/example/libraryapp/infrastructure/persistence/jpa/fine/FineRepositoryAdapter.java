package com.example.libraryapp.infrastructure.persistence.jpa.fine;

import com.example.libraryapp.domain.fine.model.Fine;
import com.example.libraryapp.domain.fine.model.FineStatus;
import com.example.libraryapp.domain.fine.ports.FineRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class FineRepositoryAdapter implements FineRepositoryPort {
    private final JpaFineRepository repository;

    @Override
    public List<Fine> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Optional<Fine> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    @Transactional
    public Fine save(Fine fineToSave) {
        return toModel(repository.save(toEntity(fineToSave)));
    }

    @Override
    @Transactional
    public void setStatus(Long id, FineStatus status) {
        repository.setStatus(id, status);
    }

    private FineEntity toEntity(Fine model) {
        return FineEntity.builder()
                .id(model.getId())
                .userId(model.getUserId())
                .loanId(model.getLoanId())
                .amount(model.getAmount())
                .status(model.getStatus())
                .build();
    }

    private Fine toModel(FineEntity entity) {
        return Fine.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .loanId(entity.getLoanId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .build();
    }
}
