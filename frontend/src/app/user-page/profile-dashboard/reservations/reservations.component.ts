import { Component, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { Reservation } from '../../../models/reservation';
import { AuthenticationService } from '../../../services/authentication.service';
import { PdfService } from '../../../services/pdf.service';
import { ReservationService } from '../../../services/reservation.service';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrl: './reservations.component.css'
})
export class ReservationsComponent implements ProfileSetting {
  name: string = "Reservations";
  routerLink: string = "reservations";
  reservationService = inject(ReservationService);
  reservedItems: Array<Reservation>;
  authService = inject(AuthenticationService);
  pdfService = inject(PdfService);

  ngOnInit(): void {
    const userId = this.authService.currentUserId;
    this.reservationService.getPendingLendingsByUserId(userId).subscribe({
      next: reservationPage => {
        if (reservationPage._embedded) {
          const reservedItems = reservationPage._embedded.reservationResponseList
                                  .filter(res => res.bookItem.status === "LOANED");
          this.reservedItems = reservedItems;
        }
      }
    });
  }
  
  public saveAsPDF(): void {
    const data = document.getElementById('table');
    this.pdfService.saveAsPDF(data, "requested-items-pending");
  }
}
