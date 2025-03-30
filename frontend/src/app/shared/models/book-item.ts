import { BookFormat } from "../enums/book-format";
import { BookItemStatus } from "../enums/book-item-status";
import { Book } from "./book";

import { Rack } from "./rack";

export class BookItem {
    id: number;
    barcode: string;
    isReferenceOnly: boolean;
    borrowed: Date;
    dueDate: Date;
    price: BigInt;
    format: BookFormat;
    status: BookItemStatus;
    dateOfPurchase: Date;
    publicationDate: Date;
    book: Book;
    rack: Rack;
}
