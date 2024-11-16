import { HypermediaCollection } from "../shared/hypermedia-collection";
import { Page } from "../shared/page";
import { UserListPreviewAdmin } from "./user-details";

export class UsersPage {
    page: Page;
    _embedded: {
        memberListPreviewDtoAdminList: Array<UserListPreviewAdmin>;
    };
    _links: HypermediaCollection;
}