import { Component, OnInit } from '@angular/core';
import { ListComponent } from '../shared/list-component';
import { Page } from '../shared/page';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { BooksService } from '../services/books.service';
import { Book } from '../models/book';
import { HypermediaCollection } from '../shared/hypermedia-collection';
import { Pair } from '../shared/pair';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: [
    './book-list.component.css',
    '../../bootstrap-fragments.css'
  ]
})
export class BookListComponent implements ListComponent, OnInit {
  page: Page = new Page();
  routeName: string = "books";
  links: HypermediaCollection;
  books: Array<Book>;
  selectedSortTypeName: string = "Domyślnie";
  selectedPageSize: number;
  pageSizes = [10, 20, 50, 100];
  languages = new Array<Pair>;
  selectedLanguages = new Array<string>;
  sortTypes = [
    {name: this.selectedSortTypeName, queryParam: ""},
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
    this.route.queryParams.subscribe(params => {
      const validParams = this.validateParams(params);
      this.initializeValuesFromParams(validParams);
      this.getAllByParams(validParams);
    });
    this.getAllLanguages();
  }

  getAllByParams(queryParams?: Params) {
    this.booksService.getAllBooks(queryParams).subscribe(p => {
      this.page = p.page;
      this.books = p._embedded.bookDtoList;
      this.links = p._links;
    });
  }

  getAllLanguages() {
    this.booksService.getLanguageListCount().subscribe(list => {
      this.languages = list;
    });
  }

  isSelected(langName: string): boolean {
    return this.selectedLanguages.includes(langName);
  }

  getAllByLanguage(lang: string) {
    if (this.selectedLanguages.includes(lang)) {
      let index = this.selectedLanguages.indexOf(lang);
      this.selectedLanguages.splice(index, 1);
    } else {
      this.selectedLanguages.push(lang);
    }

    let queryParams =  { ...this.route.snapshot.queryParams };
    queryParams["lang"] = this.selectedLanguages;
    queryParams["page"] = 0;
    this.router.navigate([this.routeName], { queryParams: queryParams });
    this.getAllByParams(queryParams);
  }

  changeSize(size: number) {
    this.selectedPageSize = size;
    const queryParams = this.updateQueryParams("size", size);
    this.getAllByParams(queryParams);
  }

  sort(sort: any) {
    const selectedSortType = this.sortTypes.filter(t => t.queryParam===sort)[0];
    this.selectedSortTypeName = selectedSortType.name;
    const queryParams = this.updateQueryParams("sort", sort);
    this.getAllByParams(queryParams);
  }

  showDetails(bookId: number) {
    this.router.navigate(['books', bookId]);
  }

  private updateQueryParams(paramName: string, value: any): Params {
    const queryParams = { ...this.route.snapshot.queryParams };
    queryParams[paramName] = value;
    this.router.navigate([this.routeName], { queryParams: queryParams });
    return queryParams;
  }

  private initializeValuesFromParams(params: Params) {
    if ('size' in params) {
      this.selectedPageSize = +params['size'];
    }
    if ('lang' in params) {
      this.selectedLanguages = params['lang'];
    }
    if ('sort' in params) {
      const sortType = this.sortTypes.find(st => st.queryParam === params['sort']);
      if (sortType) {
        this.selectedSortTypeName = sortType.name;
      }
    }
  }

  private validateParams(params: Params): Params {
    const validatedParams = { ...params };

    if (!validatedParams['size'] || validatedParams['size'] < 0) {
      validatedParams['size'] = 20;
    }

    if (!validatedParams['page'] || validatedParams['page'] < 0) {
      validatedParams['page'] = 0;
    }

    const sortType = this.sortTypes.find(st => st.queryParam === validatedParams['sort']);
    if (!validatedParams['sort'] || !sortType) {
      validatedParams['sort'] = "";
    }
    return validatedParams;
  }
}
