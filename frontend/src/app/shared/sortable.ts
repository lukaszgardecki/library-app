export interface Sortable {
    sortTypes: SortType[];
    pageSizes: Size[];

    changeSize(size: Size): void;
    sort(type: SortType): void;
}

export class SortType {
    name: string;
    queryParam?: string | undefined;
    selected: boolean;

    constructor(
        name: string,
        queryParam?: string | undefined
    ) {
        this.name = name;
        this.queryParam = queryParam;
        this.selected = false;
    }
}

export class Size {
    value: number;
    selected: boolean;

    constructor(value: number) {
        this.value = value;
        this.selected = false
    }
}