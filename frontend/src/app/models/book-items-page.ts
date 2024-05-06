import { HypermediaCollection } from "../shared/hypermedia-collection";
import { Page } from "../shared/page";
import { BookItem } from "./book-item";

export class BookItemsPage {
    page: Page;
    _embedded: {
        bookItemDtoList: Array<BookItem>
    };
    _links: HypermediaCollection;
}