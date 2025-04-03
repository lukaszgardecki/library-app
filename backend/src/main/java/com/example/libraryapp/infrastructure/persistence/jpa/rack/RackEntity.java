package com.example.libraryapp.infrastructure.persistence.jpa.rack;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private String location;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private int shelvesCount;
}
