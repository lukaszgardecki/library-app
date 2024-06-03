import { HypermediaCollection } from "../shared/hypermedia-collection";
import { Page } from "../shared/page";
import { Lending } from "./lending";

export class LendingsPage {
    page: Page;
    _embedded: {
        lendingDtoList: Array<Lending>;
    };
    _links: HypermediaCollection;
}