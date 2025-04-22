package com.example.libraryapp.infrastructure.persistence.jpa.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface JpaBookRepository extends JpaRepository<BookEntity, Long> {

    @Query("SELECT b FROM BookEntity b WHERE b.language IN :languages")
    Page<BookEntity> findByLanguages(List<String> languages, Pageable pageable);
}
