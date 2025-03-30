import { BookItemFormat } from "../../../../shared/enums/book-item-format"
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
    bookItemFormat: BookItemFormat
    rackLocationId: string
}