import { BookFormat as BookFormat } from "../../../../shared/enums/book-format"
import { BookItemRequestStatus } from "../enums/book-item-request-status"

export class BookItemRequest {
    id: number
    creationDate: Date
    status: BookItemRequestStatus
    userId: number
    bookItemId: number
}

export interface WarehouseBookItemRequestListView {
    requestId: number
    status: BookItemRequestStatus
    creationDate: Date
    bookTitle: string
    barcode: String
    bookFormat: BookFormat
    rackLocationId: string
}