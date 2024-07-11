import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { ReservationsPage } from '../models/reservations-page';
import { Observable } from 'rxjs';
import { RequestBody } from '../shared/request-body';
import { AuthenticationService } from './authentication.service';
import { Reservation } from '../models/reservation';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private baseURL;

  constructor(
    private http: HttpClient,
    private configService: ConfigService,
    private authService: AuthenticationService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/reservations`;
  }

  getPendingLendingsByUserId(id: number): Observable<ReservationsPage> {
    return this.http.get<ReservationsPage>(`${this.baseURL}?memberId=${id}&status=PENDING`, { withCredentials: true });
  }

  getReadyLendingsByUserId(id: number): Observable<ReservationsPage> {
    return this.http.get<ReservationsPage>(`${this.baseURL}?memberId=${id}&status=READY`, { withCredentials: true });
  }

  makeAReservation(bookBarcode: string): Observable<Reservation> {
    const userId = this.authService.currentUserId;
    const requestBody = new RequestBody(userId, bookBarcode);
    return this.http.post<Reservation>(this.baseURL, requestBody, { withCredentials: true });
  }
}
