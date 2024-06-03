import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from './config.service';
import { LendingsPage } from '../models/lendings-page';

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

  getLendingByUserId(id: number): Observable<LendingsPage> {
    return this.http.get<LendingsPage>(`${this.baseURL}?memberId=${id}`, { withCredentials: true });
  }
}
