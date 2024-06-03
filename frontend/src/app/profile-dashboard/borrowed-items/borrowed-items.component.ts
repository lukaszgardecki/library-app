import { Component, OnInit, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { LendingService } from '../../services/lending.service';
import { AuthenticationService } from '../../services/authentication.service';
import { Lending } from '../../models/lending';

@Component({
  selector: 'app-borrowed-items',
  templateUrl: './borrowed-items.component.html',
  styleUrl: './borrowed-items.component.css'
})
export class BorrowedItemsComponent implements ProfileSetting, OnInit {
  name: string = "Borrowed items";
  routerLink: string = "borrowed-items";
  borrowedItems: Array<Lending>;
  lendingService = inject(LendingService);
  authService = inject(AuthenticationService);

  ngOnInit(): void {
    const userId = this.authService.currentUserId;
    this.lendingService.getLendingByUserId(userId).subscribe({
      next: items => this.borrowedItems = items._embedded.lendingDtoList
    });
    console.log(this.borrowedItems);
  }
}
