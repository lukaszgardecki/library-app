import { HypermediaCollection } from "../shared/hypermedia-collection";
import { Page } from "../shared/page";
import { Notification } from "./notification";

export class NotificationsPage {
    page: Page;
    _embedded: {
        notificationDtoList: Array<Notification>;
    };
    _links: HypermediaCollection;
}