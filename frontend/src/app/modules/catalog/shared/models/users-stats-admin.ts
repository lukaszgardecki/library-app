import { UserTopBorrowersAdmin } from "./user-details";

export class Statistics {
    todayLoans: number;
    activeUsersThisMonth: number;
    newUsersThisMonth: number;
    usersCount: number;
    favGenres: Map<string, number> = new Map<string, number>();
    loansLastYearByMonth: Map<string, number> = new Map<string, number>();
    newLoansLastWeekByDay: Map<string, number> = new Map<string, number>();
    returnedLoansLastWeekByDay: Map<string, number> = new Map<string, number>();
    topBorrowers: Array<UserTopBorrowersAdmin> = new Array<UserTopBorrowersAdmin>();
    ageGroups: Map<string, number> = new Map<string, number>();
    topCities: Map<string, number> = new Map<string, number>();
}