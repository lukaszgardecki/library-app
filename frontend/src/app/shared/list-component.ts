import { Params } from "@angular/router";
import { Page } from "./page";

export interface ListComponent {
    page: Page;
    routeName: string;
    sortTypes: Array<SortType>;
    getAllByParams(queryParams?: Params): any;
    changeSize(size: number): any;
    sort(sort: any): any;
}

interface SortType {
    name: string;
    queryParam?: string | undefined;
}