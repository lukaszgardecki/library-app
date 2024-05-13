package com.example.libraryapp.domain.lending;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LendingRepository extends JpaRepository<Lending, Long> {

    List<Lending> findAllByMemberId(Long memberId);

    @Query(value =
            """
            select * from Lending
            where book_item_id = :bookItemId
            and status = 'CURRENT'
            LIMIT 1
            """
            , nativeQuery = true)
    Optional<Lending> findCurrentLendingByBookItemId(@Param(value = "bookItemId") Long bookItemId);
}
