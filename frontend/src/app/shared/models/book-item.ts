import { BookItemStatus } from "../enums/book-item-status";
import { Book } from "./book";

import { Rack, WarehouseItem } from "./rack";

export class BookItem {
    id: number;
    barcode: string;
    isReferenceOnly: boolean;
    borrowed: Date;
    dueDate: Date;
    price: BigInt;
    status: BookItemStatus;
    dateOfPurchase: Date;
    bookId: number;
    rackId: number;
    shelfId: number;
}

export class BookItemWithBook implements WarehouseItem {
    id: number;
    barcode: string;
    isReferenceOnly: boolean;
    borrowed: Date;
    dueDate: Date;
    price: BigInt;
    status: BookItemStatus;
    dateOfPurchase: Date;
    book: Book;
    rackId: number;
    rackName: string;
    shelfId: number;
    shelfName: string;
}
