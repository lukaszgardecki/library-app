import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BooksPage } from '../models/books-page';
import { Book } from '../models/book';
import { Pair } from '../shared/pair';
import { Params } from '@angular/router';
import { BookItem } from '../models/book-item';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private baseURL = "http://localhost:8080/api/v1/books"

  constructor(private http: HttpClient) { }

  getAllBooks(queryParams?: Params): Observable<BooksPage> {
    return this.http.get<BooksPage>(this.baseURL, { params: queryParams });
  }

  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.baseURL}/${id}`);
  }

  getBookItemsByBookId(id: number): Observable<BookItem[]> {
    return this.http.get<BookItem[]>(`${this.baseURL}/${id}/book-items`)
  }

  getLanguageListCount(): Observable<Pair[]>{
    return this.http.get<Pair[]>(`${this.baseURL}/languages/count`)
  }
}
