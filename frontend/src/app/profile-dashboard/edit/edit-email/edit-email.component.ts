import { Component } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';

@Component({
  selector: 'app-edit-email',
  templateUrl: './edit-email.component.html',
  styleUrl: './edit-email.component.css'
})
export class EditEmailComponent implements ProfileSetting {
  name: string = "Change e-mail address"
  routerLink: string = "edit/email";
}
