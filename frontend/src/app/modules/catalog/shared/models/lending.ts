import { LendingStatus as LoanStatus } from "../enums/loan-status.enum";

export class Loan {
    id: number;
    creationDate: Date;
    dueDate: Date;
    returnDate: Date;
    status: LoanStatus;
    userId: number;
    bookId: number;
    bookItemId: number;
}