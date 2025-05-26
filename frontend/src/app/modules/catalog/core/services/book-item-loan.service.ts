import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BookItemLoan } from '../../shared/models/book-item-loan';
import { ConfigService } from './config.service';
import { RequestBody } from '../../shared/models/request-body';
import { Page, Pageable } from '../../../../shared/models/page';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  private baseURL;

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) {
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/loans`;
  }

  getCurrentLoanListPreviewsByUserId(id: number, query: string = "", pageable: Pageable = new Pageable()): Observable<Page<BookItemLoan>> {
    let params = this.createParams(query, pageable);
    params = params.set("user_id", id)
    params = params.set("status", "CURRENT")
    return this.http.get<Page<BookItemLoan>>(`${this.baseURL}/current`, { params: params, withCredentials: true });
  }

  getCurrentRenewableLoansByUserId(id: number): Observable<Page<BookItemLoan>> {
    return this.http.get<Page<BookItemLoan>>(`${this.baseURL}?user_id=${id}&status=CURRENT&renewable=true`, { withCredentials: true });
  }

  renewABook(requestBody: RequestBody): Observable<BookItemLoan> {
    return this.http.post<BookItemLoan>(`${this.baseURL}/renew`, requestBody, { withCredentials: true })
  }

  private createParams(query: string | null, pageable: Pageable): HttpParams {
    let params = new HttpParams();
    const page = pageable.page;
    const size = pageable.size;
    const sort = pageable.sort;
    if (page !== null) { params = params.set("page", page); }
    if (size !== null) { params = params.set("size", size); }
    if (query !== null) { params = params.set("q", query); }
    if (sort?.direction) {
        const sortParam = sort.columnKey;
        const sortValue = `${sortParam},${sort.direction}`;
        params = params.set("sort", sortValue);
    }
    return params;
  }
}
