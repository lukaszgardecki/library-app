import { HypermediaCollection } from "../shared/hypermedia-collection";
import { Page } from "../shared/page";
import { Book } from "./book";

export class BooksPage {
    page: Page;
    _embedded: {
        bookDtoList: Array<Book>;
    };
    _links: HypermediaCollection;
}