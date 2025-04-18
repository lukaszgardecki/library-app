package com.example.libraryapp.application.statistics;

import com.example.libraryapp.application.bookitemloan.BookItemLoanConfiguration;
import com.example.libraryapp.application.bookitemloan.BookItemLoanFacade;
import com.example.libraryapp.application.person.PersonConfiguration;
import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.user.UserConfiguration;
import com.example.libraryapp.application.user.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatisticsConfiguration {

    public StatisticsFacade statisticsFacade() {
        PersonFacade personFacade = new PersonConfiguration().personFacade();
        UserFacade userFacade = new UserConfiguration().userFacade();
        BookItemLoanFacade bookItemLoanFacade = new BookItemLoanConfiguration().bookItemLoanFacade();
        StatisticsService statisticsService = new StatisticsService(personFacade, userFacade, bookItemLoanFacade);
        return new StatisticsFacade(
                new GetTopGenresUseCase(statisticsService),
                new GetTopBorrowersUseCase(statisticsService),
                new GetTopCitiesByUserCountUseCase(statisticsService),
                new CountAllUsersUseCase(statisticsService),
                new CountUsersByAgeGroupsUseCase(statisticsService),
                new CountNewUsersInCurrentMonthUseCase(statisticsService),
                new CountActiveUsersInCurrentMonthUseCase(statisticsService),
                new CountTodayLoansUseCase(statisticsService),
                new CountMonthlyLoansLastYearUseCase(statisticsService),
                new CountDailyLoansLastWeekUseCase(statisticsService),
                new CountUserLoansPerMonthUseCase(statisticsService)
        );
    }

    @Bean
    public StatisticsFacade statisticsFacade(
            PersonFacade personFacade,
            UserFacade userFacade,
            BookItemLoanFacade bookItemLoanFacade
    ) {
        StatisticsService statisticsService = new StatisticsService(personFacade, userFacade, bookItemLoanFacade);
        return new StatisticsFacade(
                new GetTopGenresUseCase(statisticsService),
                new GetTopBorrowersUseCase(statisticsService),
                new GetTopCitiesByUserCountUseCase(statisticsService),
                new CountAllUsersUseCase(statisticsService),
                new CountUsersByAgeGroupsUseCase(statisticsService),
                new CountNewUsersInCurrentMonthUseCase(statisticsService),
                new CountActiveUsersInCurrentMonthUseCase(statisticsService),
                new CountTodayLoansUseCase(statisticsService),
                new CountMonthlyLoansLastYearUseCase(statisticsService),
                new CountDailyLoansLastWeekUseCase(statisticsService),
                new CountUserLoansPerMonthUseCase(statisticsService)
        );
    }
}
