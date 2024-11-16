import { Component } from '@angular/core';
import { PersonalDetailsComponent } from './personal-details/personal-details.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { BorrowedItemsComponent } from './borrowed-items/borrowed-items.component';
import { OnSiteItemsComponent } from './on-site-items/on-site-items.component';
import { RequestedItemsComponent } from './requested-items/requested-items.component';
import { ReservationsComponent } from './reservations/reservations.component';
import { RenewableItemsComponent } from './renewable-items/renewable-items.component';
import { UserHistoryComponent } from './user-history/user-history.component';
import { FeesComponent } from './fees/fees.component';
import { EditComponent } from './edit/edit.component';

@Component({
  selector: 'app-profile-dashboard',
  templateUrl: './profile-dashboard.component.html',
  styleUrl: './profile-dashboard.component.css'
})
export class ProfileDashboardComponent {

  options: ProfileSetting[] = [
    new PersonalDetailsComponent(),
    new NotificationsComponent(),
    new BorrowedItemsComponent(),
    new OnSiteItemsComponent(),
    new RequestedItemsComponent(),
    new ReservationsComponent(),
    new RenewableItemsComponent(),
    new UserHistoryComponent(),
    new FeesComponent(),
    new EditComponent()
  ];
}
export interface ProfileSetting {
  name: string;
  routerLink: string;
  elements?: ProfileSetting[];
}