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
  private inProgressReservationsSubject = new BehaviorSubject<Reservation[]>([]);
  pendingReservations$ = this.pendingReservationsSubject.asObservable();
  inProgressReservations$ = this.inProgressReservationsSubject.asObservable();

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

  select(reservation: Reservation): void {
    const inProgressReservations = this.inProgressReservationsSubject.value;
    const pendingReservation = this.pendingReservationsSubject.value;

    if (inProgressReservations.includes(reservation)) {
      inProgressReservations.filter(el => el !== reservation).forEach(el => el.selected = false);
    } else {
      pendingReservation.filter(el => el !== reservation).forEach(el => el.selected = false);
    }
    reservation.selected = !reservation.selected;
  }

  moveToInProgress(reservation: Reservation): void {
    const inProgressReservations = this.inProgressReservationsSubject.value;
    const pendingReservation = this.pendingReservationsSubject.value;
    const index = pendingReservation.indexOf(reservation);
      if (index > -1) {
        pendingReservation.splice(index, 1);
        reservation.selected = false;
        inProgressReservations.push(reservation);
      }
  }

  backToPendingReservations(reservation: Reservation): void {
    const inProgressReservations = this.inProgressReservationsSubject.value;
    const pendingReservation = this.pendingReservationsSubject.value;
    const index = inProgressReservations.indexOf(reservation);
      if (index > -1) {
        inProgressReservations.splice(index, 1);
        reservation.selected = false;
        pendingReservation.push(reservation);
      }
  }

  complete(reservation: Reservation): void {
    const inProgressReservations = this.inProgressReservationsSubject.value;
    this.completeReservation(reservation.id).subscribe({
      next: res => {
        const index = inProgressReservations.indexOf(reservation);
        if (index > -1) {
          inProgressReservations.splice(index, 1);
        }   
      }
    });
  }

  private completeReservation(reservationId: number): Observable<Reservation> {
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
