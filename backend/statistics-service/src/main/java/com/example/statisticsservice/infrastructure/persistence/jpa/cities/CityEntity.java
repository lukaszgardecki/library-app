package com.example.statisticsservice.infrastructure.persistence.jpa.cities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "cities")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CityEntity {
    @Id
    private String name;
    private int users;
}
