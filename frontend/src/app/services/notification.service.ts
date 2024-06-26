import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { NotificationsPage } from '../models/notifications-page';
import { Notification } from '../models/notification';
import { AuthenticationService } from './authentication.service';
import { WebsocketService } from './websocket.service';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private baseURL;
  private notificationsPageSubject = new BehaviorSubject<NotificationsPage>(new NotificationsPage());
  notificationsPage$ = this.notificationsPageSubject.asObservable();
  private currentNotificationSubject = new BehaviorSubject<Notification>(new Notification());

  constructor(
    private http: HttpClient,
    private configService: ConfigService,
    private authenticationService: AuthenticationService,
    private websocketService: WebsocketService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/notifications`;

    this.fetchNotificationsPage(0);
    this.subscribeUserNotifications();
  }

  getNotificationsPage(pageIndex: number = 0) {
    this.fetchNotificationsPage(pageIndex);
  }

  getSingleNotificationById(): Observable<Notification> {
    return this.currentNotificationSubject.asObservable()
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseURL}/${id}`, { withCredentials: true }).pipe(
      tap(() => {
        const currentPage = this.notificationsPageSubject.value;

        if (currentPage.page.totalPages > 1) {
          if (currentPage.page.number === currentPage.page.totalPages - 1 && currentPage._embedded.notificationDtoList.length === 1) {
              this.fetchNotificationsPage(currentPage.page.number - 1);
          } else {
              this.fetchNotificationsPage(currentPage.page.number);
          }
        } else {
          let currentNotifications = this.notificationsPageSubject.value._embedded.notificationDtoList;
          let updatedNotifications = currentNotifications.filter(notification => notification.id !== id);
          updatedNotifications = this.sortByDateDesc(updatedNotifications);
          currentPage._embedded.notificationDtoList = updatedNotifications;
          currentPage.page.totalElements = currentPage.page.totalElements - 1;
          currentPage.page.totalPages = Math.ceil(currentPage.page.totalElements / currentPage.page.size);

          this.notificationsPageSubject.next(currentPage);
        } 
      })
    );
  }

  deleteAllSelected() {
    const ids = this.notificationsPageSubject.value._embedded.notificationDtoList
        .filter(el => el.selected)
        .map(el => el.id);
    if (ids.length > 0) {
      
      this.http.delete<void>(`${this.baseURL}`, { body: ids, withCredentials: true }).subscribe({
        next: () => {
          const currentPageNum = this.notificationsPageSubject.value.page.number;

          const countToDelete = ids.length;
          const elementsOnPage = this.notificationsPageSubject.value._embedded.notificationDtoList.length;
          const allPageToDelete = countToDelete === elementsOnPage;
          const isLastPage = currentPageNum == this.notificationsPageSubject.value.page.totalPages - 1;
          const isPageBefore = currentPageNum > 0;

          let newPageNum = allPageToDelete && isPageBefore && isLastPage ? currentPageNum - 1 : currentPageNum;
          this.fetchNotificationsPage(newPageNum);
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

  private fetchNotificationsPage(pageIndex: number): void {
    const userId = this.authenticationService.currentUserId;
    let params = new HttpParams()
      .set("memberId", userId)
      .set("sort", "created_at,desc")
      .set("page", pageIndex);
    this.http.get<NotificationsPage>(`${this.baseURL}`, { params: params, withCredentials: true })
      .subscribe({
        next: notificationPage => {
          console.log(notificationPage);
          this.notificationsPageSubject.next(notificationPage);
        }
      });
  }

  private subscribeUserNotifications() {
    this.websocketService.notifications$.subscribe({
      next: notification => {
        this.addNotification(notification);
      },
      error: err => console.error('Error in notification subscription:', err)
    });
  }



  private addNotification(newNotification: Notification): void {
    const currentPage = this.notificationsPageSubject.value;
    
    if (currentPage.page.number == 0) {
      // add the new element, delete the last element
      let currentNotifications = currentPage._embedded.notificationDtoList;
      currentNotifications = currentNotifications.slice(0, currentNotifications.length-1);
      currentNotifications = [...currentNotifications, newNotification];
      currentPage.page.totalElements = currentPage.page.totalElements + 1;
      currentPage.page.totalPages = Math.ceil(currentPage.page.totalElements / currentPage.page.size);
      currentPage._embedded.notificationDtoList = this.sortByDateDesc(currentNotifications);
    }
    
    this.notificationsPageSubject.next(currentPage);
  }

  private sortByDateDesc(notifications: Notification[]) {
    return notifications.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
  }
}
