package com.example.libraryapp.infrastructure.persistence.jpa.fine;

import com.example.libraryapp.domain.bookitemloan.model.LoanId;
import com.example.libraryapp.domain.fine.model.Fine;
import com.example.libraryapp.domain.fine.model.FineAmount;
import com.example.libraryapp.domain.fine.model.FineId;
import com.example.libraryapp.domain.fine.model.FineStatus;
import com.example.libraryapp.domain.fine.ports.FineRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
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
    public List<Fine> findAllByUserId(UserId userId) {
        return repository.findAllByUserId(userId.value()).stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Optional<Fine> findById(FineId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    @Transactional
    public Fine save(Fine fineToSave) {
        return toModel(repository.save(toEntity(fineToSave)));
    }

    @Override
    @Transactional
    public void setStatus(FineId id, FineStatus status) {
        repository.setStatus(id.value(), status);
    }

    private FineEntity toEntity(Fine model) {
        return FineEntity.builder()
                .id(model.getId() != null ? model.getId().value() : null)
                .userId(model.getUserId().value())
                .loanId(model.getLoanId().value())
                .amount(model.getAmount().value())
                .status(model.getStatus())
                .build();
    }

    private Fine toModel(FineEntity entity) {
        return Fine.builder()
                .id(new FineId(entity.getId()))
                .userId(new UserId(entity.getUserId()))
                .loanId(new LoanId(entity.getLoanId()))
                .amount(new FineAmount(entity.getAmount()))
                .status(entity.getStatus())
                .build();
    }
}
