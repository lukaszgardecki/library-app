import { BookFormat } from "../enums/book-format";

export class Book {
    id: number;
    title: string;
    subject: string;
    publisher: string;
    ISBN: string;
    language: string;
    pages: number;
    format: BookFormat;
    publicationDate: Date;
}