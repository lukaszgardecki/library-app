package com.example.libraryapp.NEWinfrastructure.persistence.jpa.fine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface JpaFineRepository extends JpaRepository<FineEntity, Long> {

    List<FineEntity> findAllByUserId(Long userId);
}
