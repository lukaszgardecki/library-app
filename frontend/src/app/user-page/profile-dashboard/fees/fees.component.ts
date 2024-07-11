import { Component } from '@angular/core';
import { FeesNotAccountedComponent } from './fees-not-accounted/fees-not-accounted.component';
import { FeesAccountedComponent } from './fees-accounted/fees-accounted.component';
import { ProfileSetting } from '../profile-dashboard.component';
import { TEXT } from '../../../shared/messages';

@Component({
  selector: 'app-fees',
  templateUrl: './fees.component.html',
  styleUrl: './fees.component.css'
})
export class FeesComponent implements ProfileSetting {
  TEXT = TEXT;
  name: string = TEXT.PROFILE_FEES_NAME;
  routerLink: string;
  elements?: ProfileSetting[] = [
    new FeesNotAccountedComponent(),
    new FeesAccountedComponent()
  ];
}
