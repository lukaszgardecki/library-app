import { Component, OnInit, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { LendingService } from '../../../services/lending.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { Lending } from '../../../models/lending';
import { PdfService } from '../../../services/pdf.service';
import { TEXT } from '../../../shared/messages';

@Component({
  selector: 'app-borrowed-items',
  templateUrl: './borrowed-items.component.html',
  styleUrl: './borrowed-items.component.css'
})
export class BorrowedItemsComponent implements ProfileSetting, OnInit {
  TEXT = TEXT;
  name: string = TEXT.PROFILE_BORROWED_ITEMS_NAME;
  routerLink: string = "borrowed-items";
  lendings: Array<Lending>;
  lendingService = inject(LendingService);
  authService = inject(AuthenticationService);
  pdfService = inject(PdfService);

  ngOnInit(): void {
    const userId = this.authService.currentUserId;
    this.lendingService.getCurrentLendingsByUserId(userId).subscribe({
      next: lendingPage => {
        if (lendingPage._embedded) {
          this.lendings = lendingPage._embedded.lendingDtoList.filter(item => item.bookItem.isReferenceOnly === false);
        } 
      }
    });
  }

  public saveAsPDF(): void {
    const data = document.getElementById('table');
    this.pdfService.saveAsPDF(data, "borrowed-books");
  }
}