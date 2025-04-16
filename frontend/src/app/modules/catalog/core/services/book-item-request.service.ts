import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { Page, Pageable } from '../../../../shared/models/page';
import { BookItemRequest } from '../../shared/models/book-item-request';
import { Observable } from 'rxjs';
import { BookItemRequestStatus } from '../../shared/enums/book-item-request-status';

@Injectable({
  providedIn: 'root'
})
export class BookItemRequestService {
  private baseURL;

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/book-requests`;
  }

  getRequests(status: BookItemRequestStatus | null = null, query: string = "", pageable: Pageable = new Pageable()): Observable<Page<BookItemRequest>> {
    let params = this.createParams(status, query, pageable);
    return this.http.get<Page<BookItemRequest>>(`${this.baseURL}`, { params: params, withCredentials: true });
  }

  private createParams(status: BookItemRequestStatus | null, query: string | null, pageable: Pageable): HttpParams {
    let params = new HttpParams();
    const page = pageable.page;
    const size = pageable.size;
    const sort = pageable.sort;
    if (page !== null) { params = params.set("page", page); }
    if (size !== null) { params = params.set("size", size); }
    if (query !== null) { params = params.set("q", query); }
    if (status !== null) { params = params.set("status", status)}
    if (sort?.direction) {
        const sortParam = sort.columnKey;
        const sortValue = `${sortParam},${sort.direction}`;
        params = params.set("sort", sortValue);
    }
    return params;
  }
}
