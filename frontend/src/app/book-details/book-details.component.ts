import { Component, OnInit } from '@angular/core';
import { BooksService } from '../services/books.service';
import { Book } from '../models/book';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.css'
})
export class BookDetailsComponent implements OnInit {
  book: Book;

  constructor(
    private bookService: BooksService,
    private route: ActivatedRoute,
    private router: Router,
    private location: Location
  ) { }

  ngOnInit(): void {
      this.book = new Book();
      const id = this.route.snapshot.params['id'];

      this.bookService.getBookById(id).subscribe(data => {
        this.book = data;
      });
  }

  goBack() {
    this.location.back();
  }
}
