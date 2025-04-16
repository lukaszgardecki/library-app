import { Sort } from "./sort.interface";

export interface TableUpdateEvent {
    page: number;
    size: number;
    sort: Sort;
    query: string;
  }