import { Component, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { Lending } from '../../../models/lending';
import { AuthenticationService } from '../../../services/authentication.service';
import { LendingService } from '../../../services/lending.service';
import { PdfService } from '../../../services/pdf.service';

@Component({
  selector: 'app-on-site-items',
  templateUrl: './on-site-items.component.html',
  styleUrl: './on-site-items.component.css'
})
export class OnSiteItemsComponent implements ProfileSetting {
  name: string = "On-site items";
  routerLink: string = "on-site-items";

  onSiteLendings: Array<Lending>;
  lendingService = inject(LendingService);
  authService = inject(AuthenticationService);
  pdfService = inject(PdfService);

  ngOnInit(): void {
    const userId = this.authService.currentUserId;
    this.lendingService.getCurrentLendingsByUserId(userId).subscribe({
      next: lendingPage => {
        if (lendingPage._embedded) {
          this.onSiteLendings = lendingPage._embedded.lendingDtoList.filter(item => item.bookItem.isReferenceOnly === true);
        } 
      }
    });
  }

  public saveAsPDF(): void {
    const data = document.getElementById('table');
    this.pdfService.saveAsPDF(data, "on-site-items");
  }
}
