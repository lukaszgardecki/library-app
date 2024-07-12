import { Component } from '@angular/core';
import { ProfileSetting } from '../../user-page/profile-dashboard/profile-dashboard.component';
import { TEXT } from '../../shared/messages';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements ProfileSetting {
  TEXT = TEXT;
  name: string = TEXT.ADMIN_USERS_NAME;
  routerLink: string = "users";
  
}
