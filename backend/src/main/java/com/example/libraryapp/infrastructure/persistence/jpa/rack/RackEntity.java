package com.example.libraryapp.infrastructure.persistence.jpa.rack;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rack")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class RackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locationIdentifier;
}
