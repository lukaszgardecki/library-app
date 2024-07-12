import { HypermediaCollection } from "../shared/hypermedia-collection";
import { Page } from "../shared/page";
import { UserDetails } from "./user-details";

export class UsersPage {
    page: Page;
    _embedded: {
        memberDtoList: Array<UserDetails>;
    };
    _links: HypermediaCollection;
}