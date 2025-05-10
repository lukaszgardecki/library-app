package com.example.statisticsservice.domain.ports.out;

import com.example.statisticsservice.domain.integration.Subject;
import com.example.statisticsservice.domain.model.genre.Genre;

import java.util.List;

public interface GenreRepositoryPort {

    List<Genre> findAllOrderByLoansDesc(int limit);

    void incrementLoansCount(Subject subject);

}
