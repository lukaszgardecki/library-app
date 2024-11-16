export class LibraryCard {
    id: number;
    barcode: string;
    issuedAt: Date;
    status: CardStatus;
}

export enum CardStatus {
    ACTIVE = "ACTIVE",
    INACTIVE = "INACTIVE",
    LOST = "LOST"
}