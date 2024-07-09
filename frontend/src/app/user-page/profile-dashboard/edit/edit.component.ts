import { Component } from '@angular/core';
import { EditPasswordComponent } from './edit-password/edit-password.component';
import { EditEmailComponent } from './edit-email/edit-email.component';
import { EditPhoneNumberComponent } from './edit-phone-number/edit-phone-number.component';
import { ProfileSetting } from '../profile-dashboard.component';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.css'
})
export class EditComponent implements ProfileSetting {
  name: string = "PROFILE.EDIT.NAME"
  routerLink: string;
  elements?: ProfileSetting[] = [
    new EditPasswordComponent(),
    new EditEmailComponent(), 
    new EditPhoneNumberComponent()
  ];
}
