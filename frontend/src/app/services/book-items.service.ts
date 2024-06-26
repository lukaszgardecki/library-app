import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BookItemsPage } from '../models/book-items-page';
import { ConfigService } from './config.service';

@Injectable({
  providedIn: 'root'
})
export class BookItemsService {

  private baseURL;

  constructor(
    private http: HttpClient,
    configService: ConfigService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/book-items`
  }

  getAllBookItems(page: number, size: number, sort: string): Observable<BookItemsPage> {
    const params = new HttpParams().set("page", page).set("size", size).set("sort", sort);
    return this.http.get<BookItemsPage>(this.baseURL, {params: params});
  }
}
