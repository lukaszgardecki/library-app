import { Component, OnInit } from '@angular/core';
import { ListComponent } from '../shared/list-component';
import { Page } from '../shared/page';
import { ActivatedRoute, Router } from '@angular/router';
import { BooksService } from '../services/books.service';
import { Book } from '../models/book';
import { HypermediaCollection } from '../shared/hypermedia-collection';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.css'
})
export class BookListComponent implements ListComponent, OnInit {
  page: Page = new Page();
  routeName: string = "books";
  links: HypermediaCollection;
  books: Array<Book>;
  sortTypes = [
    {name: "---", queryParam: undefined},
    {name: "Tytuł rosnąco", queryParam: "title,asc"},
    {name: "Tytuł malejąco", queryParam: "title,desc"},
    {name: "Wydawca rosnąco", queryParam: "publisher,asc"},
    {name: "Wydawca malejąco", queryParam: "publisher,desc"},
    {name: "Strony rosnąco", queryParam: "pages,asc"},
    {name: "Strony malejąco", queryParam: "pages,desc"}
  ];

  constructor(
    private booksService: BooksService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const pageNo = this.route.snapshot.queryParams["page"];
    const pageSize = this.route.snapshot.queryParams["size"];
    this.getAll(pageNo, pageSize);
  }

  getAll(page: number, size: number, sort: string="asc") {
    this.booksService.getAllBooks(page, size, sort).subscribe(p => {
      this.page = p.page;
      this.books = p._embedded.bookDtoList;
      this.links = p._links;
    });
  }

  showDetails(bookId: number) { 
    this.router.navigate(['books', bookId]);
  }
}
