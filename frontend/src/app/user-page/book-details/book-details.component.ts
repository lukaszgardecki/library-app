import { Component, OnInit } from '@angular/core';
import { BooksService } from '../../services/books.service';
import { Book } from '../../models/book';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { BookItem } from '../../models/book-item';
import { BookItemStatus } from '../../shared/book-item-status';
import { ReservationService } from '../../services/reservation.service';
import { AuthenticationService } from '../../services/authentication.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.css'
})
export class BookDetailsComponent implements OnInit {
  book: Book = new Book();
  bookItems: Array<BookItem> = [];

  constructor(
    private bookService: BooksService,
    private reservationService: ReservationService,
    private authService: AuthenticationService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit(): void {
      const id = this.route.snapshot.params['id'];

      this.bookService.getBookById(id).subscribe(data => {
        this.book = data;
      });

      this.bookService.getBookItemsByBookId(id).subscribe(data => {
        this.bookItems = data.filter(b => b.status != BookItemStatus.LOST);
      });
  }

  getStatusLabel(status: BookItemStatus): string {
    let st = "";
    switch(status) {
      case BookItemStatus.AVAILABLE:
        st = "BOOK_DETAILS.STATUS.AVAILABLE"; break;
      case BookItemStatus.LOANED:
        st = "BOOK_DETAILS.STATUS.LOANED"; break;
      default:
        st = "BOOK_DETAILS.STATUS.UNAVAILABLE";
    }
    return st;
  }

  getLoanableItems(): BookItem[] {
    return this.bookItems.filter(item => item.isReferenceOnly === false);
  }

  getOnSiteItems(): BookItem[] {
    return this.bookItems.filter(item => item.isReferenceOnly === true)
  }

  isAvailable(bookItem: BookItem): boolean {
    return bookItem.status === BookItemStatus.AVAILABLE;
  }

  goBack() {
    this.location.back();
  }

  makeAReservation(bookItem: BookItem) {
    this.reservationService.makeAReservation(bookItem.barcode).subscribe({
      next: reservation => {
        bookItem.status = reservation.bookItem.status;
        this.authService.addToReservedItemsIds(bookItem.id);
      }
    });
  }

  isAuthenticated(): Observable<boolean> {
    return this.authService.isLoggedIn$;
  }

  hasUserBorrowed(bookItem: BookItem): Observable<boolean> {
    return this.authService.hasUserBorrowed(bookItem);
  }

  hasUserReserved(bookItem: BookItem): Observable<boolean> {
    return this.authService.hasUserReserved(bookItem);
  }
}
