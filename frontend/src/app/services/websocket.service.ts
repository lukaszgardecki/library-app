import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Client} from '@stomp/stompjs';
import { Notification } from '../models/notification';
import { AuthenticationService } from './authentication.service';
import { Reservation } from '../models/reservation';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private serverUrl = 'http://localhost:8080/ws';
  private stompClient: Client;
  private notificationsSubject = new Subject<Notification>();
  private warehouseReservationsSubject = new Subject<Reservation>();

  public notifications$ = this.notificationsSubject.asObservable();
  public warehouseReservations$ = this.warehouseReservationsSubject.asObservable();

  constructor(private authService: AuthenticationService) {
    this.authService.isLoggedIn$.subscribe({
      next: isLoggedIn => {
        if (isLoggedIn) {
          this.connect();
        } else {
          this.disconnect();
        }
      }
    });
  }

  private connect() {
    this.stompClient = this.createStompClient();
    this.stompClient.activate();
  }

  private disconnect() {
    const userId = this.authService.currentUserId;
    this.stompClient.unsubscribe(`/user/${userId}/queue/notifications`);
    this.stompClient.deactivate();
  }

  private createStompClient(): Client {
    return new Client({
      brokerURL: this.serverUrl,
      debug: function (str) {
        // console.log(str);
      },
      reconnectDelay: 200,
      heartbeatIncoming: 0,
      heartbeatOutgoing: 20000,
      onConnect: () => {
        this.subscribeUserNotifications();

        if (this.authService.hasUserPermissionToWarehouse()) {
          this.subscribeWarehouseReservations();
        }

      }
    });
  }

  private subscribeUserNotifications(): void {
    const userId = this.authService.currentUserId;
    this.stompClient.subscribe(`/user/${userId}/queue/notifications`, message => {
      const notification = JSON.parse(message.body) as Notification;
      this.notificationsSubject.next(notification);
    });
  }

  private subscribeWarehouseReservations(): void {
    this.stompClient.subscribe(`/queue/warehouse`, message => {
      const reservation = JSON.parse(message.body) as Reservation;
      this.warehouseReservationsSubject.next(reservation);
    });
  }
}
