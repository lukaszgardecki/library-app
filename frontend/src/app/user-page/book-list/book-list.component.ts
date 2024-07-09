import { Component, OnInit } from '@angular/core';
import { Size, SortType, Sortable } from '../../shared/sortable';
import { Params, Router } from '@angular/router';
import { BooksService } from '../../services/books.service';
import { Language } from '../../models/language';
import { Observable } from 'rxjs';
import { BooksPage } from '../../models/books-page';
import { Pageable } from '../../shared/pageable';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: [
    './book-list.component.css',
    '../../../bootstrap-fragments.css'
  ]
})
export class BookListComponent implements OnInit, Pageable, Sortable {
  booksPage$: Observable<BooksPage>;
  languages$: Observable<Language[]>;
  queryParams$: Observable<Params>;
  pageSizes: Size[] = [new Size(10), new Size(20), new Size(50), new Size(100)];
  sortTypes: SortType[] = [
    new SortType("BOOK_LIST.SORT_TYPE.DEFAULT", ""),
    new SortType("BOOK_LIST.SORT_TYPE.TITLE_ASC", "title,asc"),
    new SortType("BOOK_LIST.SORT_TYPE.TITLE_DESC", "title,desc"),
    new SortType("BOOK_LIST.SORT_TYPE.PUBLISHER_ASC", "publisher,asc"),
    new SortType("BOOK_LIST.SORT_TYPE.PUBLISHER_DESC", "publisher,desc"),
    new SortType("BOOK_LIST.SORT_TYPE.PAGES_ASC", "pages,asc"),
    new SortType("BOOK_LIST.SORT_TYPE.PAGES_DESC", "pages,desc")
  ];

  constructor(
    private booksService: BooksService,
    private router: Router
  ) { 
    this.booksService.refresh();
  }

  ngOnInit(): void {
    this.booksPage$ = this.booksService.booksPage$;
    this.languages$ = this.booksService.languages$;
    this.queryParams$ = this.booksService.queryParams$;

    this.selectSideMenuOptions();
  }

  loadPage(pageIndex: number): void {
    this.booksService.getBooksPage(pageIndex);
  }

  changeSize(size: Size): void {
    this.pageSizes.forEach(el => el.selected = false);
    size.selected = !size.selected;
    this.booksService.changeSize(size);
  }

  sort(type: SortType): void {
    this.sortTypes.forEach(el => el.selected = false);
    type.selected = !type.selected;
    this.booksService.sort(type);
  }

  getAllByLanguage(lang: Language) {
    lang.selected = !lang.selected;
    this.booksService.getBooksBySelectedLanguages();
  }

  showDetails(bookId: number) {
    this.router.navigate(['books', bookId]);
  }

  private selectSideMenuOptions() {
    this.queryParams$.subscribe({
      next: params => {
        const size = params["size"] || 20;
        const sort = params["sort"] || "";
        const langs: string[] = params["lang"] || [];
        
        this.pageSizes.filter(el => el.value == size).map(el => el.selected = true);
        this.sortTypes.filter(el => el.queryParam == sort).map(el => el.selected = true);
        this.languages$.subscribe(langList => {
          if (langs) {
            langList.forEach(lang => {
                lang.selected = langs.includes(lang.name)
            });
          }
        });
      }
    })
  }
}
