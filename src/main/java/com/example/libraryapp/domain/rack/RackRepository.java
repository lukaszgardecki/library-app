package com.example.libraryapp.domain.rack;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RackRepository extends JpaRepository<Rack, Long> {

    Page<Rack> findAllByLocationIdentifierContainsIgnoreCase(String locationIdentifier, Pageable pageable);
}
