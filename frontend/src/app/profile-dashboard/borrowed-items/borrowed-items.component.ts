import { Component } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';

@Component({
  selector: 'app-borrowed-items',
  templateUrl: './borrowed-items.component.html',
  styleUrl: './borrowed-items.component.css'
})
export class BorrowedItemsComponent implements ProfileSetting {
  name: string = "Borrowed items";
  routerLink: string = "borrowed-items";
}
