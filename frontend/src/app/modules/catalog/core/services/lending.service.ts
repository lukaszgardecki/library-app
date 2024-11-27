import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Lending } from '../../shared/models/lending';
import { LendingsPage } from '../../shared/models/lendings-page';
import { ConfigService } from './config.service';
import { RequestBody } from '../../shared/models/request-body';

@Injectable({
  providedIn: 'root'
})
export class LendingService {
  private baseURL;

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/lendings`;
  }

  getCurrentLendingsByUserId(id: number): Observable<LendingsPage> {
    return this.http.get<LendingsPage>(`${this.baseURL}?memberId=${id}&status=CURRENT`, { withCredentials: true });
  }

  getCurrentRenewableLendingsByUserId(id: number): Observable<LendingsPage> {
    return this.http.get<LendingsPage>(`${this.baseURL}?memberId=${id}&status=CURRENT&renewable=true`, { withCredentials: true });
  }

  renewABook(requestBody: RequestBody): Observable<Lending> {
    return this.http.post<Lending>(`${this.baseURL}/renew`, requestBody, { withCredentials: true })
  }
}
