package com.example.libraryapp.infrastructure.persistence.jpa.person;

import com.example.libraryapp.domain.person.model.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "person")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class PersonEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String firstName;
        private String lastName;
        @Enumerated(EnumType.STRING)
        private Gender gender;
        private String pesel;
        private LocalDate dateOfBirth;
        private String nationality;
        private String fathersName;
        private String mothersName;
        @Embedded
        private AddressEntity address;
        private String phone;
}
