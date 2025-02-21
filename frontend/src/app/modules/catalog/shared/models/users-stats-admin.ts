import { UserTopBorrowersAdmin } from "./user-details";

export class Statistics {
    todayLoans: number;
    usersCount: number;
    activeUsersThisMonth: number;
    newUsersThisMonth: number;
    favGenres: Map<string, number> = new Map<string, number>();
    loansLastYearByMonth: Array<number> = new Array<number>();
    newLoansLastWeekByDay: Array<number> = new Array<number>();
    returnedLoansLastWeekByDay: Array<number> = new Array<number>();
    topBorrowers: Array<UserTopBorrowersAdmin> = new Array<UserTopBorrowersAdmin>();
    ageGroups: Map<string, number> = new Map<string, number>();
    topCities: Map<string, number> = new Map<string, number>();
}