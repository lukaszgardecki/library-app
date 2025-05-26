package com.example.statisticsservice.infrastructure.persistence.jpa.genres;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "genres")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class GenreEntity {
    @Id
    private String name;
    private int loans;
}
