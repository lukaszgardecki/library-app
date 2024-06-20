import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { NotificationService } from '../../services/notification.service';
import { Notification } from '../../models/notification';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent implements ProfileSetting, OnInit {
  name: string = "Notifications";
  routerLink: string = "notifications";
  notifications$: Observable<Notification[]>;
  listIsEmpty: boolean;
  allAreSelected: boolean = false;
  @ViewChild("selectAll") selectAllBtn: ElementRef;
  notificationService = inject(NotificationService);
  router = inject(Router);

  ngOnInit() {
    this.notifications$ = this.notificationService.getNotifications();
    this.notifications$.subscribe({
      next: list => this.listIsEmpty = list.length === 0
    })
  }

  showDetails(notification: Notification) {
    this.notificationService.markNotificationAsRead(notification);
    this.router.navigate(['/userprofile/notifications', notification.id]);
  }

  deleteSelected() {
    this.notificationService.deleteAllSelected();
  }

  selectAll(event: Event) {
    const button = (event.target as HTMLButtonElement);
    this.notifications$.subscribe({
      next: list => {
        if (this.allAreSelected) {
          list.forEach(el => el.selected = false);
          this.allAreSelected = false;
          button.textContent = "Select all"
        } else {
          list.forEach(el => el.selected = true);
          this.allAreSelected = true;
          button.textContent = "Unselect all"
        }
      }
    });
  }
}
