import { HypermediaCollection } from "../shared/hypermedia-collection";
import { Page } from "../shared/page";
import { Reservation } from "./reservation";

export class ReservationsPage {
    page: Page;
    _embedded: {
        reservationResponseList: Array<Reservation>;
    };
    _links: HypermediaCollection;
}