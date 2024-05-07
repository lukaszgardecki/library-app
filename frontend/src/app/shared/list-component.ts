import { Page } from "./page";

export interface ListComponent {
    page: Page;
    routeName: string;
    sortTypes: Array<SortType>;
    getAll(page: number, size: number, sort: string): any;
}

interface SortType {
    name: string;
    queryParam?: string | undefined;
  }