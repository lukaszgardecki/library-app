import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { WebsocketService } from './websocket.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { Reservation } from '../models/reservation';

@Injectable({
  providedIn: 'root'
})
export class WarehouseService {
  private baseURL;
  private pendingReservationsSubject = new BehaviorSubject<Reservation[]>([]);
  pendingReservations$ = this.pendingReservationsSubject.asObservable();

  constructor(
    private http: HttpClient,
    private configService: ConfigService,
    private websocketService: WebsocketService
  ) {
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/warehouse`;

    this.fetchPendingReservations();
    this.subscribePendingReservations();
  }

  completeReservation(reservationId: number): Observable<Reservation> {
    return this.http.post<Reservation>(`${this.baseURL}/reservations/${reservationId}/ready`, {}, { withCredentials: true });
  }

  private fetchPendingReservations(): void {
    this.http.get<Reservation[]>(`${this.baseURL}/reservations/pending`, { withCredentials: true })
      .subscribe({
        next: notifications => this.pendingReservationsSubject.next(notifications)
      });
  }

  private subscribePendingReservations() {
    this.websocketService.warehouseReservations$.subscribe({
      next: reservation => {
        let currentReservations = this.pendingReservationsSubject.value;
        currentReservations = [...currentReservations, reservation];
        this.pendingReservationsSubject.next(currentReservations);
      },
      error: err => console.error('Error in reservation subscription:', err)
    });
  }
}
