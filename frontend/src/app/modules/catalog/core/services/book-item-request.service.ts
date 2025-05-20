import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { Page, Pageable } from '../../../../shared/models/page';
import { BookItemRequest } from '../../shared/models/book-item-request';
import { Observable } from 'rxjs';
import { BookItemRequestStatus } from '../../shared/enums/book-item-request-status';
import { WarehouseBookItemRequestListView } from '../../../../shared/models/rack';

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

  getRequests(options: { status?: BookItemRequestStatus, query?: string, pageable?: Pageable}): Observable<Page<BookItemRequest>> {
    let params = this.createParams(options.status, options.query, options.pageable);
    return this.http.get<Page<BookItemRequest>>(`${this.baseURL}`, { params: params, withCredentials: true });
  }

  getWarehouseRequestsPage(options: { status?: BookItemRequestStatus, query?: string, pageable?: Pageable}): Observable<Page<WarehouseBookItemRequestListView>> {
    let params = this.createParams(options.status, options.query, options.pageable);
    return this.http.get<Page<WarehouseBookItemRequestListView>>(`${this.baseURL}/warehouse/list`, { params: params, withCredentials: true });
  }

  private createParams(status?: BookItemRequestStatus, query?: string, pageable?: Pageable): HttpParams {
    let params = new HttpParams();
    if (query) { params = params.set("q", query); }
    if (status) { params = params.set("status", status)}
    if (pageable) {
      if (pageable.page) { params = params.set("page", pageable.page)}
      if (pageable.size) { params = params.set("size", pageable.size)}
      if (pageable.sort?.direction) {
        const sortParam = pageable.sort.columnKey;
        const sortValue = `${sortParam},${pageable.sort.direction}`;
        params = params.set("sort", sortValue);
      }
    }
    return params;
  }
}
