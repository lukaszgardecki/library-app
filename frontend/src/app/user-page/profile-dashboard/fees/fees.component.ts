import { Component } from '@angular/core';
import { FeesNotAccountedComponent } from './fees-not-accounted/fees-not-accounted.component';
import { FeesAccountedComponent } from './fees-accounted/fees-accounted.component';
import { ProfileSetting } from '../profile-dashboard.component';

@Component({
  selector: 'app-fees',
  templateUrl: './fees.component.html',
  styleUrl: './fees.component.css'
})
export class FeesComponent implements ProfileSetting {
  name: string = "PROFILE.FEES.NAME";
  routerLink: string;
  elements?: ProfileSetting[] = [
    new FeesNotAccountedComponent(),
    new FeesAccountedComponent()
  ];
}
