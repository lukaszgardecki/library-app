import { Component } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';

@Component({
  selector: 'app-on-site-items',
  templateUrl: './on-site-items.component.html',
  styleUrl: './on-site-items.component.css'
})
export class OnSiteItemsComponent implements ProfileSetting {
  name: string = "On-site items";
  routerLink: string = "on-site-items";
}
