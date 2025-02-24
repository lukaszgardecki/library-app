package com.example.libraryapp.infrastructure.persistence.jpa.rack;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface JpaRackRepository extends JpaRepository<RackEntity, Long> {

    @Query("""
        SELECT r
        FROM RackEntity r
        WHERE r.locationIdentifier = :location
    """)
    Optional<RackEntity> findByLocationIdentifier(@Param("location") String location);
}
