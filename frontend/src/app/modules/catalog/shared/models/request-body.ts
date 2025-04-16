export class RequestBody {
    constructor(private memberId: number, private bookBarcode: string) {
        this.memberId = memberId;
        this.bookBarcode = bookBarcode;
    }
}