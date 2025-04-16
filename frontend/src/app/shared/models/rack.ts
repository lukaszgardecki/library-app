import { BookItemRequestStatus } from "../../modules/catalog/shared/enums/book-item-request-status";
import { BookFormat } from "../enums/book-format";

export interface WarehouseItem {
    id: number;
}

export class Rack implements WarehouseItem {
    id: number;
    name: string;
    createdDate: Date;
    updatedDate: Date;
    shelvesCount: number
}

export class RackToSave {
    name: string;
}

export class Shelf implements WarehouseItem {
    id: number;
    name: string;
    position: number;
    createdDate: Date;
    updatedDate: Date;
    bookItemsCount: number;
}

export class ShelfToSave {
    name: string;
    rackId: number;
}

export interface WarehouseBookItemRequestListView extends WarehouseItem {
    status: BookItemRequestStatus
    creationDate: Date
    bookTitle: string
    barcode: String
    bookFormat: BookFormat
    rackName: string
    shelfName: string
}