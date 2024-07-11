import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';
import { TEXT } from '../../../../shared/messages';

@Component({
  selector: 'app-requested-items-unsent',
  templateUrl: './requested-items-unsent.component.html',
  styleUrl: './requested-items-unsent.component.css'
})
export class RequestedItemsUnsentComponent implements ProfileSetting {
  TEXT = TEXT;
  name: string = TEXT.PROFILE_REQUESTED_ITEMS_UNSENT_NAME;
  routerLink: string = "requested-items/unsent";
}
