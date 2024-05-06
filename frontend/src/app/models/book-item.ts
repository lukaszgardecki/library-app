import { Book } from "./book";
import { BookItemFormat } from "../shared/book-item-format";
import { BookItemStatus } from "../shared/book-item-status";
import { Rack } from "./rack";

export class BookItem {
    id: number;
    barcode: string;
    isReferenceOnly: boolean;
    borrowed: Date;
    dueDate: Date;
    price: BigInt;
    format: BookItemFormat;
    status: BookItemStatus;
    dateOfPurchase: Date;
    publicationDate: Date;
    book: Book;
    rack: Rack;
}
