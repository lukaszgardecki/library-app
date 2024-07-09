import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';

@Component({
  selector: 'app-fees-not-accounted',
  templateUrl: './fees-not-accounted.component.html',
  styleUrl: './fees-not-accounted.component.css'
})
export class FeesNotAccountedComponent implements ProfileSetting {
  name: string = "PROFILE.FEES.NOT_ACCOUNTED.NAME"
  routerLink: string = "fees/not-accounted";
}
