import { HypermediaCollection } from "../../../../shared/models/hypermedia-collection";
import { Page } from "../../../../shared/models/page";
import { Lending } from "./lending";

export class LendingsPage {
    page: Page;
    _embedded: {
        lendingDtoList: Array<Lending>;
    };
    _links: HypermediaCollection;
}