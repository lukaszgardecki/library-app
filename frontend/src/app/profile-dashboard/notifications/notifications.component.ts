import { Component } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent implements ProfileSetting {
  name: string = "Notifications";
  routerLink: string = "notifications";
}
