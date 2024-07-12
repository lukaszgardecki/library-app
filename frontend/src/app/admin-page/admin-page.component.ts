import { Component } from '@angular/core';
import { ProfileSetting } from '../user-page/profile-dashboard/profile-dashboard.component';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrl: './admin-page.component.css'
})
export class AdminPageComponent {

  options: ProfileSetting[] = [];
}
