import { HypermediaCollection } from "../shared/hypermedia-collection";
import { Page } from "../shared/page";
import { Action } from "./action";

export class ActionsPage {
    page: Page;
    _embedded: {
        actionDtoList: Array<Action>;
    };
    _links: HypermediaCollection;
}