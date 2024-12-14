package com.example.libraryapp.NEWdomain.fine.ports;

import com.example.libraryapp.NEWdomain.fine.model.Fine;

import java.util.List;

public interface FineRepository {
    List<Fine> getAllByUserId(Long userId);

    Fine save(Fine fineToSave);
}
