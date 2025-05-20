package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.ports.in.EventListenerPort;
import com.example.statisticsservice.domain.ports.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StatisticsConfiguration {

    @Bean
    public StatisticsFacade statisticsFacade(
            BorrowerRepositoryPort borrowerRepository,
            DailyStatsRepositoryPort dailyStatsRepository,
            CityRepositoryPort cityRepository,
            GenreRepositoryPort genreRepository,
            UserMonthlyLoansRepositoryPort userMonthlyLoansRepository
    ) {
        return new StatisticsFacade(
                new GetTopBorrowersUseCase(borrowerRepository),
                new GetTopCitiesUseCase(cityRepository),
                new GetTopGenresUseCase(genreRepository),
                new CountAllUsersUseCase(borrowerRepository),
                new CountNewUsersByMonthUseCase(dailyStatsRepository),
                new CountLoansByDateUseCase(dailyStatsRepository),
                new CountDailyNewLoansByDateBetweenUseCase(dailyStatsRepository),
                new CountDailyReturnedLoansLastWeekUseCase(dailyStatsRepository),
                new CountMonthlyLoansByDateBetweenUseCase(dailyStatsRepository),
                new CountMonthlyUserLoansByDateUseCase(userMonthlyLoansRepository),
                new CountActiveBorrowersByMonthUseCase(borrowerRepository),
                new CountUsersByAgeGroupsUseCase(borrowerRepository)
        );
    }

    @Bean
    EventListenerPort eventListenerService(
            BorrowerRepositoryPort borrowerRepository,
            CityRepositoryPort cityRepository,
            DailyStatsRepositoryPort dailyStatsRepository,
            GenreRepositoryPort genreRepository,
            UserMonthlyLoansRepositoryPort userMonthlyLoansRepository
    ) {
        return new EventListenerService(
                borrowerRepository, cityRepository, dailyStatsRepository, genreRepository, userMonthlyLoansRepository
        );
    }
}
