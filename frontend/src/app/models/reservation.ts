import { UserDetails } from "../shared/user-details";
import { BookItem } from "./book-item";

export class Reservation {
    id: number;
    creationDate: Date;
    status: string;
    member: UserDetails;
    bookItem: BookItem;
}