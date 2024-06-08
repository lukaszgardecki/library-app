import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';

@Component({
  selector: 'app-fees-not-accounted',
  templateUrl: './fees-not-accounted.component.html',
  styleUrl: './fees-not-accounted.component.css'
})
export class FeesNotAccountedComponent implements ProfileSetting {
  name: string = "Not accounted fees"
  routerLink: string = "fees/not-accounted";
}
