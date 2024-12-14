package com.example.libraryapp.NEWinfrastructure.persistence.jpa.person;

import org.springframework.data.jpa.repository.JpaRepository;

interface JpaPersonRepository extends JpaRepository<PersonEntity, Long> {

}
