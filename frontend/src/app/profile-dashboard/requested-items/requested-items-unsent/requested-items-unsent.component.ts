import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';

@Component({
  selector: 'app-requested-items-unsent',
  templateUrl: './requested-items-unsent.component.html',
  styleUrl: './requested-items-unsent.component.css'
})
export class RequestedItemsUnsentComponent implements ProfileSetting {
  name: string = "Unsent";
  routerLink: string = "requested-items/unsent";
}
