import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { BooksPage } from '../models/books-page';
import { Book } from '../models/book';
import { Language } from '../models/language';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { BookItem } from '../models/book-item';
import { ConfigService } from './config.service';
import { Size, SortType } from '../shared/sortable';

@Injectable({
  providedIn: 'root'
})
export class BooksService {
  private baseURL;
  private routeName: string = "books";
  private booksPageSubject = new BehaviorSubject<BooksPage>(new BooksPage());
  private languagesSubject = new BehaviorSubject<Language[]>([]);
  private queryParamsSubject = new BehaviorSubject<Params>(new HttpParams());
  booksPage$ = this.booksPageSubject.asObservable();
  languages$ = this.languagesSubject.asObservable();
  queryParams$ = this.queryParamsSubject.asObservable();

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private configService: ConfigService
  ) {
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/books`;
  }

  refresh(): void {
    this.route.queryParams.subscribe(params => {
      const validParams = this.validateParams(params);
      this.queryParamsSubject.next(validParams);
    });
    this.fetchBooks();
    this.fetchLanguages();
  }

  getBooksPage(pageIndex: number = 0): void {
    this.updateParam("page", pageIndex);
    this.fetchBooks();
  }

  getLanguages(): void {
    this.fetchLanguages();
  }

  changeSize(size: Size) {
    this.updateParam("size", size.value);
    this.updateParam("page", 0);
    this.fetchBooks();
  }

  sort(type: SortType) {
    this.updateParam("sort", type.queryParam);
    this.fetchBooks();
  }

  getBooksBySelectedLanguages() {
    const selectedLangs = this.languagesSubject.value.filter(lang => lang.selected).map(lang => lang.name);
    this.updateParam("lang", selectedLangs);
    this.updateParam("page", 0);
    this.fetchBooks();
  }

  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.baseURL}/${id}`, { withCredentials: true });
  }

  getBookItemsByBookId(id: number): Observable<BookItem[]> {
    return this.http.get<BookItem[]>(`${this.baseURL}/${id}/book-items`, { withCredentials: true })
  }

  private fetchBooks(): void {
    const queryParams = this.queryParamsSubject.value;
    this.http.get<BooksPage>(this.baseURL, { params: queryParams }).subscribe({
      next: booksPage => {
        if (booksPage._embedded) {
          this.booksPageSubject.next(booksPage);
        }
      }
    });
  }

  private fetchLanguages(): void {
    this.http.get<Language[]>(`${this.baseURL}/languages/count`).subscribe({
      next: languages => this.languagesSubject.next(languages)
    });
  }
  
  private validateParams(params: Params): Params {
    const validatedParams = { ...params };

    if (validatedParams['size'] <= 0) {
      validatedParams['size'] = 20;
    }

    if (validatedParams['page'] < 0) {
      validatedParams['page'] = 0;
    }
    return validatedParams;
  }

  private updateParam(paramName: string, value: any) {
    let params = this.queryParamsSubject.value;
    params[paramName] = value;
    this.queryParamsSubject.next(params);
    const newParams = this.queryParamsSubject.value;

    this.router.navigate([this.routeName], { queryParams: newParams });
  }
}
