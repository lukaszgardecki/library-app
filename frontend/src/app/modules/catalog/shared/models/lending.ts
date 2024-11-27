import { BookItem } from "../../../../shared/models/book-item";
import { LendingStatus } from "../enums/lending-status.enum";
import { UserDetails } from "./user-details";

export class Lending {
    id: number;
    creationDate: Date;
    dueDate: Date;
    returnDate: Date;
    status: LendingStatus;
    member: UserDetails;
    bookItem: BookItem;
}