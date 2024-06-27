import { Component } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { RequestedItemsUnsentComponent } from './requested-items-unsent/requested-items-unsent.component';
import { RequestedItemsPendingComponent } from './requested-items-pending/requested-items-pending.component';
import { RequestedItemsCompletedComponent } from './requested-items-completed/requested-items-completed.component';

@Component({
  selector: 'app-requested-items',
  templateUrl: './requested-items.component.html',
  styleUrl: './requested-items.component.css'
})
export class RequestedItemsComponent implements ProfileSetting {
  name: string = "Requested items";
  routerLink: string;
  elements?: ProfileSetting[] = [
    new RequestedItemsUnsentComponent(),
    new RequestedItemsPendingComponent(),
    new RequestedItemsCompletedComponent()
  ];
}
