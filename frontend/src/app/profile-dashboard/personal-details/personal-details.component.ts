import { Component } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';

@Component({
  selector: 'app-personal-details',
  templateUrl: './personal-details.component.html',
  styleUrl: './personal-details.component.css'
})
export class PersonalDetailsComponent implements ProfileSetting {
  name: string = "Personal details";
  routerLink: string = "details";
}
