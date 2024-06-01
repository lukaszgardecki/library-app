import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';

@Component({
  selector: 'app-edit-phone-number',
  templateUrl: './edit-phone-number.component.html',
  styleUrl: './edit-phone-number.component.css'
})
export class EditPhoneNumberComponent implements ProfileSetting {
  name: string = "Change phone number"
  routerLink: string = "phone";
}
