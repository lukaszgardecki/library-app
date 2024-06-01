import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';

@Component({
  selector: 'app-requested-items-completed',
  templateUrl: './requested-items-completed.component.html',
  styleUrl: './requested-items-completed.component.css'
})
export class RequestedItemsCompletedComponent implements ProfileSetting {
  name: string = "Completed";
  routerLink: string = "completed";
}
