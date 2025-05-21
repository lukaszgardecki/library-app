package com.example.fineservice.domain.ports.out;

import com.example.fineservice.domain.model.Fine;
import com.example.fineservice.domain.model.values.FineId;
import com.example.fineservice.domain.model.values.FineStatus;
import com.example.fineservice.domain.model.values.UserId;

import java.util.List;
import java.util.Optional;

public interface FineRepositoryPort {
    List<Fine> findAllByUserId(UserId userId);

    Optional<Fine> findById(FineId id);

    Fine save(Fine fineToSave);

    void setStatus(FineId id, FineStatus status);
}
