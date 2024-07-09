import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';

@Component({
  selector: 'app-fees-accounted',
  templateUrl: './fees-accounted.component.html',
  styleUrl: './fees-accounted.component.css'
})
export class FeesAccountedComponent implements ProfileSetting {
  name: string = "PROFILE.FEES.ACCOUNTED.NAME"
  routerLink: string = "fees/accounted";
}
