import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BookItemsPage } from '../models/book-items-page';

@Injectable({
  providedIn: 'root'
})
export class BookItemsService {

  private baseURL = "http://localhost:8080/api/v1/book-items"

  constructor(private http: HttpClient) { }

  getAllBookItems(page: number, size: number, sort: string): Observable<BookItemsPage> {
    const params = new HttpParams().set("page", page).set("size", size).set("sort", sort);
    return this.http.get<BookItemsPage>(this.baseURL, {params: params});
  }
}
