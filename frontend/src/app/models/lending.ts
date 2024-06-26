import { UserDetails } from "./user-details";
import { BookItem } from "./book-item";

export class Lending {
    id: number;
    creationDate: Date;
    dueDate: Date;
    returnDate: Date;
    status: string;
    member: UserDetails;
    bookItem: BookItem;
}