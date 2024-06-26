export class Notification {
    id: number;
    createdAt: Date;
    subject: string;
    content: string;
    bookId: number;
    bookTitle: string;
    memberId: number;
    type: string;
    read: boolean;
    selected: boolean = false;
}