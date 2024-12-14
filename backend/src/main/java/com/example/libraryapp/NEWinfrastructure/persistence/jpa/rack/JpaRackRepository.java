package com.example.libraryapp.NEWinfrastructure.persistence.jpa.rack;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.nio.channels.FileChannel;
import java.util.Optional;

interface JpaRackRepository extends JpaRepository<RackEntity, Long> {

    @Query("""
            SELECT r
            FROM RackEntity r
            WHERE r.locationIdentifier = :location
        """)
    Optional<RackEntity> findByLocationIdentifier(String location);
}
