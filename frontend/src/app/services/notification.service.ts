import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { BehaviorSubject, Observable, catchError, filter, find, map, of, switchMap, tap, throwError } from 'rxjs';
import { NotificationsPage } from '../models/notifications-page';
import { Notification } from '../models/notification';
import { AuthenticationService } from './authentication.service';
import { WebsocketService } from './websocket.service';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private baseURL;
  private notificationsSubject = new BehaviorSubject<Notification[]>([]);
  notifications$ = this.notificationsSubject.asObservable();
  private currentNotificationSubject = new BehaviorSubject<Notification>(new Notification());

  constructor(
    private http: HttpClient,
    private configService: ConfigService,
    private authenticationService: AuthenticationService,
    private websocketService: WebsocketService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/notifications`;

    this.refresh();
    this.subscribeUserNotifications();
  }

  getNotifications(): Observable<Notification[]> {
    return this.notifications$;
  }

  refresh(): void {
    const userId = this.authenticationService.currentUserId;
    this.refreshNotifications(userId);
  }

  getSingleNotificationById(): Observable<Notification> {
    return this.currentNotificationSubject.asObservable()
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseURL}/${id}`, { withCredentials: true }).pipe(
      tap(() => {
        const currentNotifications = this.notificationsSubject.value;
        const updatedNotifications = currentNotifications.filter(notification => notification.id !== id);
        this.publishUpdatedNotifications(updatedNotifications);
      })
    );
  }

  deleteAllSelected() {
    const currentNotifications = this.notificationsSubject.value;
    if (currentNotifications.length > 0) {
      const ids = currentNotifications
        .filter(el => el.selected)
        .map(el => el.id)
      this.http.delete<void>(`${this.baseURL}`, { body: ids, withCredentials: true }).subscribe({
        next: () => {
          const updatedNotifications = currentNotifications.filter(el => !ids.includes(el.id));
          this.publishUpdatedNotifications(updatedNotifications);
        }
      });
    }
  }

  markNotificationAsRead(notification: Notification) {
    this.currentNotificationSubject.next(notification);
    if (notification.read === false) {
      this.http.post<void>(`${this.baseURL}/${notification.id}`, {}, { withCredentials: true }).subscribe({
        next: () =>  notification.read = true
      });
    }
  }

  private refreshNotifications(id: number): void {
    this.http.get<NotificationsPage>(`${this.baseURL}`, { params: { memberId: id }, withCredentials: true })
        .pipe(
          tap(notificationPage => {
            this.publishUpdatedNotifications(notificationPage._embedded.notificationDtoList);
          })
        ).subscribe();
  }

  private subscribeUserNotifications() {
    this.websocketService.notifications$.subscribe({
      next: notification => {
        this.addNotification(notification);
      },
      error: err => console.error('Error in notification subscription:', err)
    });
  }

  private publishUpdatedNotifications(list: Notification[]): void {
    const sortedNotifications = this.sortByDateDesc(list);
    this.notificationsSubject.next(sortedNotifications);
  }

  private addNotification(newNotification: Notification): void {
    const currentNotifications = this.notificationsSubject.value;
    const updatedNotifications = [...currentNotifications, newNotification];
    const sortedNotifications = this.sortByDateDesc(updatedNotifications);
    this.notificationsSubject.next(sortedNotifications);
  }

  private sortByDateDesc(notifications: Notification[]) {
    return notifications.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
  }
}
