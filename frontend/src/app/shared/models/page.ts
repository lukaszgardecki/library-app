import { Sort } from "../../modules/catalog/shared/models/sort.interface";

export class Page<T> {
    content: T[] = [];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
}

export class Pageable {
  page: number = 0;
  size: number = 0;
  sort: Sort | null = null;

  constructor(page: number = 0, size: number = 0, sort: Sort | null = null) {
    this.page = page;
    this.size = size;
    this.sort = sort;
  }
}