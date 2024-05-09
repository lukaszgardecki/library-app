import { HypermediaObject } from "../shared/hypermedia-object";

export class Book {
    id: number;
    title: string;
    subject: string;
    publisher: string;
    isbn: string;
    language: string;
    pages: number;
    _links: HypermediaObject;
}