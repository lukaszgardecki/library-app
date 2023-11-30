package com.example.libraryapp.domain.lending;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LendingRepository extends JpaRepository<Lending, Long> {

//    List<Lending> findCheckoutsByBook_Id(Long bookId);

    List<Lending> findAllByMemberId(Long memberId);

    @Query(value =
            """
            select * from Lending
            where book_barcode = :bookBarcode
            and status = 'CURRENT'
            """
            , nativeQuery = true)
    Optional<Lending> findCurrentLendingByBarcode(@Param(value = "bookBarcode") String bookBarcode);
}
