import { Component } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrl: './reservations.component.css'
})
export class ReservationsComponent implements ProfileSetting {
  name: string = "Reservations";
  routerLink: string = "reservations";
}
