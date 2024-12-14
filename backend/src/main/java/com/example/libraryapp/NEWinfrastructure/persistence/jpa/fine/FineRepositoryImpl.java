package com.example.libraryapp.NEWinfrastructure.persistence.jpa.fine;

import com.example.libraryapp.NEWdomain.fine.model.Fine;
import com.example.libraryapp.NEWdomain.fine.ports.FineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class FineRepositoryImpl implements FineRepository {
    private final JpaFineRepository repository;

    @Override
    public List<Fine> getAllByUserId(Long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Fine save(Fine fineToSave) {
        return toModel(repository.save(toEntity(fineToSave)));
    }


    private FineEntity toEntity(Fine model) {
        return FineEntity.builder()
                .id(model.getId())
                .userId(model.getUserId())
                .loanId(model.getLoanId())
                .amount(model.getAmount())
                .paid(model.getPaid())
                .build();
    }

    private Fine toModel(FineEntity entity) {
        return Fine.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .loanId(entity.getLoanId())
                .amount(entity.getAmount())
                .paid(entity.getPaid())
                .build();
    }
}
