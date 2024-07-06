import { LibraryCard } from "./library-card";

export class UserDetails {
    id: number;
    firstName: string;
    lastName: string;
    gender: string;
    address: string;
    email: string;
    phoneNumber: string;
    pesel: string;
    nationality: string;
    dateOfBirth: string;
    parentsNames: string;
    card: LibraryCard;
    dateOfMembership: Date;
    totalBooksBorrowed: number;
    totalBooksReserved: number;
    charge: BigInt;
    status: string;
    loanedItemsIds: Array<number>;
    reservedItemsIds: Array<number>;
}
