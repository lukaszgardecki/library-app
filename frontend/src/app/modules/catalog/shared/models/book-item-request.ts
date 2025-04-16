import { BookItemRequestStatus } from "../enums/book-item-request-status"

export class BookItemRequest {
    id: number
    creationDate: Date
    status: BookItemRequestStatus
    userId: number
    bookItemId: number
}

