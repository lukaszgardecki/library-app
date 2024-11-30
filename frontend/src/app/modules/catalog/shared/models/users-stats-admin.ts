import { UserTopBorrowersAdmin } from "./user-details";

export class UserStatsAdmin {
    todayLendings: number;
    usersCount: number;
    activeUsersThisMonth: number;
    newUsersThisMonth: number;
    favGenres: Map<string, number> = new Map<string, number>();
    lendingsLastYearByMonth: Array<number> = new Array<number>();
    newLendingsLastWeekByDay: Array<number> = new Array<number>();
    returnedLendingsLastWeekByDay: Array<number> = new Array<number>();
    topBorrowers: Array<UserTopBorrowersAdmin> = new Array<UserTopBorrowersAdmin>();
}