import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Loan } from '../../shared/models/lending';
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

  getCurrentLoanListPreviewsByUserId(id: number, query: string = "", pageable: Pageable = new Pageable()): Observable<Page<Loan>> {
    let params = this.createParams(query, pageable);
    params = params.set("userId", id)
    params = params.set("status", "CURRENT")
    return this.http.get<Page<Loan>>(`${this.baseURL}/list`, { params: params, withCredentials: true });
  }

  getCurrentRenewableLoansByUserId(id: number): Observable<Page<Loan>> {
    return this.http.get<Page<Loan>>(`${this.baseURL}?userId=${id}&status=CURRENT&renewable=true`, { withCredentials: true });
  }

  renewABook(requestBody: RequestBody): Observable<Loan> {
    return this.http.post<Loan>(`${this.baseURL}/renew`, requestBody, { withCredentials: true })
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
          // const sortParam = ['firstName', 'lastName'].includes(sort.columnKey) ? `${sort.columnKey}` : sort.columnKey;
          const sortParam = sort.columnKey;
          const sortValue = `${sortParam},${sort.direction}`;
          params = params.set("sort", sortValue);
      }
  
      return params;
    }
}
