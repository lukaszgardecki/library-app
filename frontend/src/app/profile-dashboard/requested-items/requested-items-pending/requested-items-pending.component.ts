import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';

@Component({
  selector: 'app-requested-items-pending',
  templateUrl: './requested-items-pending.component.html',
  styleUrl: './requested-items-pending.component.css'
})
export class RequestedItemsPendingComponent implements ProfileSetting {
  name: string = "Pending";
  routerLink: string = "pending";
}
