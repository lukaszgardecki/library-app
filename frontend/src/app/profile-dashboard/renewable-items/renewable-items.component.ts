import { Component } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';

@Component({
  selector: 'app-renewable-items',
  templateUrl: './renewable-items.component.html',
  styleUrl: './renewable-items.component.css'
})
export class RenewableItemsComponent implements ProfileSetting {
  name: string = "Renewable items";
  routerLink: string = "renewable-items";
}
