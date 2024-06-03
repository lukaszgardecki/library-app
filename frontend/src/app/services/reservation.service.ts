import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { ReservationsPage } from '../models/reservations-page';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private baseURL;

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/reservations`;
  }

  getPendingLendingsByUserId(id: number): Observable<ReservationsPage> {
    return this.http.get<ReservationsPage>(`${this.baseURL}?memberId=${id}`, { withCredentials: true });
  }
}
