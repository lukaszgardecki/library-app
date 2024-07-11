import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';
import { TEXT } from '../../../../shared/messages';

@Component({
  selector: 'app-fees-accounted',
  templateUrl: './fees-accounted.component.html',
  styleUrl: './fees-accounted.component.css'
})
export class FeesAccountedComponent implements ProfileSetting {
  TEXT = TEXT;
  name: string = TEXT.PROFILE_FEES_NOT_ACCOUNTED_NAME;
  routerLink: string = "fees/accounted";
}
