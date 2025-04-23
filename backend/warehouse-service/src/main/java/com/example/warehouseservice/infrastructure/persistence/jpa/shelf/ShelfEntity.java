package com.example.warehouseservice.infrastructure.persistence.jpa.shelf;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shelf")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ShelfEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int position;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer bookItemsCount;
    private Long rackId;
}
