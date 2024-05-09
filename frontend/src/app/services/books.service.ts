import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BookItemsPage } from '../models/book-items-page';
import { BooksPage } from '../models/books-page';
import { Book } from '../models/book';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private baseURL = "http://localhost:8080/api/v1/books"

  constructor(private http: HttpClient) { }

  getAllBooks(page: number, size: number, sort: string): Observable<BooksPage> {
    const params = new HttpParams().set("page", page).set("size", size).set("sort", sort);
    return this.http.get<BooksPage>(this.baseURL, {params: params});
  }

  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.baseURL}/${id}`);
  }
}
