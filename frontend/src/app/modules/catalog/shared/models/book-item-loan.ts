import { BookItemLoanStatus as LoanStatus } from "../enums/book-item-loan-status";

export class BookItemLoan {
    id: number;
    creationDate: Date;
    dueDate: Date;
    returnDate: Date;
    status: LoanStatus;
    userId: number;
    bookId: number;
    bookItemId: number;
}