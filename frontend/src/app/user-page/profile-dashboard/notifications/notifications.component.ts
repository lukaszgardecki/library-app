import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { NotificationService } from '../../../services/notification.service';
import { Notification } from '../../../models/notification';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Pageable } from '../../../shared/pageable';
import { NotificationsPage } from '../../../models/notifications-page';
import { TEXT } from '../../../shared/messages';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent implements ProfileSetting, OnInit, Pageable {
  TEXT = TEXT;
  name: string = TEXT.PROFILE_NOTIFICATIONS_NAME;
  routerLink: string = "notifications";
  notificationsPage$: Observable<NotificationsPage>;
  allAreSelected: boolean = false;
  @ViewChild("all_btn") selectAllBtn: ElementRef;
  notificationService = inject(NotificationService);
  router = inject(Router);

  ngOnInit() {
    this.notificationsPage$ = this.notificationService.notificationsPage$;
  }

  showDetails(notification: Notification) {
    this.notificationService.markNotificationAsRead(notification);
    this.router.navigate(['/userprofile/notifications', notification.id]);
  }

  deleteSelected() {
    this.notificationService.deleteAllSelected();

    if (this.allAreSelected) {
      this._unselectAll();
    }    
  }

  selectAll() {
    this.allAreSelected ? this._unselectAll() : this._selectAll();
  }

  loadPage(pageIndex: number): void {
    if (this.allAreSelected) {
      this._unselectAll();
    }  
    this.notificationService.getNotificationsPage(pageIndex);
  }

  private _unselectAll() {
    this.allAreSelected = false;
    this.notificationsPage$.subscribe(list => list._embedded.notificationDtoList.forEach(el => el.selected = false));
    this.selectAllBtn.nativeElement.innerText = TEXT.PROFILE_NOTIFICATIONS_BTN_SELECT_ALL
  }

  private _selectAll() {
    this.allAreSelected = true;
    this.notificationsPage$.subscribe(list => list._embedded.notificationDtoList.forEach(el => el.selected = true));
    this.selectAllBtn.nativeElement.innerText = TEXT.PROFILE_NOTIFICATIONS_BTN_UNSELECT_ALL
  }
}
