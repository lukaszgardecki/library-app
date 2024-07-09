import { Component, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { Lending } from '../../../models/lending';
import { AuthenticationService } from '../../../services/authentication.service';
import { LendingService } from '../../../services/lending.service';
import { RequestBody } from '../../../shared/request-body';

@Component({
  selector: 'app-renewable-items',
  templateUrl: './renewable-items.component.html',
  styleUrl: './renewable-items.component.css'
})
export class RenewableItemsComponent implements ProfileSetting {
  name: string = "PROFILE.RENEWABLE_ITEMS.NAME";
  routerLink: string = "renewable-items";
  renewableLendings: Array<Lending>;
  lendingService = inject(LendingService);
  authService = inject(AuthenticationService);

  ngOnInit(): void {
    this.fetchCurrentRenawableLendingsByUserId();
  }

  renew(bookBarcode: string) {
    const userId = this.authService.currentUserId;
    this.lendingService.renewABook(new RequestBody(userId, bookBarcode)).subscribe({
      next: lending => {
        console.log(lending)
        this.fetchCurrentRenawableLendingsByUserId();
      }
    });
  }

  private fetchCurrentRenawableLendingsByUserId() {
    const userId = this.authService.currentUserId;
    this.lendingService.getCurrentRenewableLendingsByUserId(userId).subscribe({
      next: lendingPage => {
        if (lendingPage._embedded) {
          this.renewableLendings = lendingPage._embedded.lendingDtoList.filter(item => item.bookItem.isReferenceOnly === false);
        } 
      }
    });
  }
}
