package com.example.libraryapp.domain.lending;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LendingRepository extends JpaRepository<Lending, Long> {

    List<Lending> findCheckoutsByBook_Id(Long bookId);
}
