import { BookItemStatus } from "../enums/book-item-status";

import { WarehouseItem } from "./rack";

export class BookItem implements WarehouseItem {
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
