import { CardStatus } from "../enums/card-status.enum";

export class LibraryCard {
    id: number;
    barcode: string;
    issuedAt: Date;
    status: CardStatus;
}