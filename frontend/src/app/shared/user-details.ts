import { LibraryCard } from "./library-card";

export class UserDetails {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    card: LibraryCard;
    dateOfMembership: Date;
    totalBooksBorrowed: number;
    totalBooksReserved: number;
    charge: BigInt;
}