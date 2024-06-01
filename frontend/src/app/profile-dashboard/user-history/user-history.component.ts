import { Component } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';

@Component({
  selector: 'app-user-history',
  templateUrl: './user-history.component.html',
  styleUrl: './user-history.component.css'
})
export class UserHistoryComponent implements ProfileSetting {
  name: string = "User history";
  routerLink: string = "history";
}
