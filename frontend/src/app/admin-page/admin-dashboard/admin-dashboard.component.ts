import { Component } from '@angular/core';
import { ProfileSetting } from '../../user-page/profile-dashboard/profile-dashboard.component';
import { TEXT } from '../../shared/messages';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements ProfileSetting {
  TEXT = TEXT;
  name: string = TEXT.ADMIN_DASHBOARD_NAME;
  routerLink: string = ".";
}
