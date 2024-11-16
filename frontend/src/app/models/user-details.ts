import { CardStatus, LibraryCard } from "./library-card";

export class UserDetails {
    id: number;
    firstName: string;
    lastName: string;
    gender: Gender;
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
    status: AccountStatus;
    loanedItemsIds: Array<number>;
    reservedItemsIds: Array<number>;
}

export class UserDetailsAdmin {
    id: number;
    firstName: string;
    lastName: string;
    gender: Gender;
    streetAddress: string;
    zipCode: string;
    city: string;
    state: string;
    country: string;
    email: string;
    phoneNumber: string;
    pesel: string;
    nationality: string;
    dateOfBirth: string;
    fathersName: string;
    mothersName: string;
    card: LibraryCard;
    dateOfMembership: Date;
    totalBooksBorrowed: number;
    totalBooksReserved: number;
    charge: BigInt;
    status: AccountStatus;
    loanedItemsIds: Array<number>;
    reservedItemsIds: Array<number>;
    role: Role;
}

export class UserUpdate {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    streetAddress: string;
    zipCode: string;
    city: string;
    state: string;
    country: string;
    phone: string;
}

export class UserPreview {
    id: number;
    firstName: string;
    lastName: string;
    role: Role
}

export class UserListPreviewAdmin {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    dateOfMembership: Date;
    status: AccountStatus;
}

export class UserUpdateAdmin {
    firstName: string;
    lastName: string;
    email: string;
    streetAddress: string;
    zipCode: string;
    city: string;
    state: string;
    country: string;
    phone: string;
    gender: Gender;
    pesel: string;
    dateOfBirth: string; // ten format??
    nationality: string;
    fathersName: string;
    mothersName: string;
    accountStatus: AccountStatus;
    cardStatus: CardStatus;
    role: Role;
}

export enum AccountStatus {
    ACTIVE = "ACTIVE",
    INACTIVE = "INACTIVE",
    CLOSED = "CLOSED",
    SUSPENDED = "SUSPENDED",
    PENDING = "PENDING"
}

export enum Role {
    USER = "USER",
    ADMIN = "ADMIN",
    CASHIER = "CASHIER",
    WAREHOUSE = "WAREHOUSE"
}

export enum Gender {
    MALE = "MALE",
    FEMALE = "FEMALE",
    OTHER = "OTHER"
}
