import { BookItemRequestStatus } from "../../modules/catalog/shared/enums/book-item-request-status";
import { BookFormat } from "../enums/book-format";

export interface WarehouseItem {
    id: number;
}

export interface Rack extends WarehouseItem {
    name: string;
    location: string;
    createdDate: Date;
    updatedDate: Date;
    shelvesCount: number
}

export interface Shelf extends WarehouseItem {
    name: string;
    position: number;
    createdDate: Date;
    updatedDate: Date;
    bookItemsCount: number;
}

export interface WarehouseBookItemRequestListView extends WarehouseItem {
    status: BookItemRequestStatus
    creationDate: Date
    bookTitle: string
    barcode: String
    bookFormat: BookFormat
    rackLocation: string
}