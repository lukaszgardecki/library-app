package com.example.statisticsservice.domain.model.city;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityStats {
    private String name;
    private int users;
}
