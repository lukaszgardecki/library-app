import { Component, OnInit } from '@angular/core';
import { BooksService } from '../services/books.service';
import { Book } from '../models/book';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { BookItem } from '../models/book-item';
import { BookItemStatus } from '../shared/book-item-status';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.css'
})
export class BookDetailsComponent implements OnInit {
  book: Book;
  bookItems: Array<BookItem>;

  constructor(
    private bookService: BooksService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit(): void {
      this.book = new Book();
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
      case BookItemStatus.RESERVED:
        st = "Available"; break;
      case BookItemStatus.LOANED:
        st = "On loan"; break;
      default:
        st = "Unavailable";
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
    return bookItem.status === BookItemStatus.AVAILABLE || bookItem.status === BookItemStatus.RESERVED;
  }

  goBack() {
    this.location.back();
  }
}
