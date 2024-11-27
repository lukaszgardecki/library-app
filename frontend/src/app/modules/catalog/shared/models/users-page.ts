import { HypermediaCollection } from "../../../../shared/models/hypermedia-collection";
import { Page } from "../../../../shared/models/page";
import { UserListPreviewAdmin } from "./user-details";

export class UsersPage {
    page: Page;
    _embedded: {
        memberListPreviewDtoAdminList: Array<UserListPreviewAdmin>;
    };
    _links: HypermediaCollection;
}