import { HypermediaCollection } from "../shared/hypermedia-collection";
import { HypermediaObject } from "../shared/hypermedia-object";
import { Page } from "../shared/page";

export class BooksPage {
    page: Page;
    _embedded: {
        bookPreviewDtoList: Array<BookPreview>;
    };
    _links: HypermediaCollection;
}

export interface BookPreview {
    id: number;
    title: string;
    subject: string;
    publisher: string;
    _links: HypermediaObject;
}