import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Notification } from '../../../../models/notification';
import { NotificationService } from '../../../../services/notification.service';

@Component({
  selector: 'app-notification-details',
  templateUrl: './notification-details.component.html',
  styleUrl: './notification-details.component.css'
})
export class NotificationDetailsComponent implements OnInit {
  notification: Notification;

  constructor(
    private notificationService: NotificationService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.notificationService.getSingleNotificationById().subscribe({
      next: notification => {
        this.notification = notification;
      }
    });
  }

  goBack() {
    this.location.back();
  }

  delete(id: number) {
    this.notificationService.deleteById(id).subscribe({
      next: () => this.goBack()
    });
  }
}
