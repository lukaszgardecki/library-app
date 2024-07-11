import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';
import { TEXT } from '../../../../shared/messages';

@Component({
  selector: 'app-fees-not-accounted',
  templateUrl: './fees-not-accounted.component.html',
  styleUrl: './fees-not-accounted.component.css'
})
export class FeesNotAccountedComponent implements ProfileSetting {
  TEXT = TEXT;
  name: string = TEXT.PROFILE_FEES_ACCOUNTED_NAME;
  routerLink: string = "fees/not-accounted";
}
