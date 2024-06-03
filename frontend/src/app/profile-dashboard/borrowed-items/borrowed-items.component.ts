import { Component, OnInit, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { LendingService } from '../../services/lending.service';
import { AuthenticationService } from '../../services/authentication.service';
import { Lending } from '../../models/lending';
import { PdfService } from '../../services/pdf.service';

@Component({
  selector: 'app-borrowed-items',
  templateUrl: './borrowed-items.component.html',
  styleUrl: './borrowed-items.component.css'
})
export class BorrowedItemsComponent implements ProfileSetting, OnInit {
  name: string = "Borrowed items";
  routerLink: string = "borrowed-items";
  lendings: Array<Lending>;
  lendingService = inject(LendingService);
  authService = inject(AuthenticationService);
  pdfService = inject(PdfService);

  ngOnInit(): void {
    const userId = this.authService.currentUserId;
    this.lendingService.getLendingsByUserId(userId).subscribe({
      next: items => this.lendings = items._embedded.lendingDtoList
    });
    console.log(this.lendings);
  }

  public saveAsPDF(): void {
    const data = document.getElementById('table');
    this.pdfService.saveAsPDF(data, "borrowed-books");
  }
}