export class UserStatsAdmin {
    todayLendings: number;
    usersCount: number;
    activeUsersThisMonth: number;
    newUsersThisMonth: number;
    favGenres: Map<string, number> = new Map<string, number>();
    lendingsPerMonthWithinAYear: Array<number> = new Array<number>();
}