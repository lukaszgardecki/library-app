import { Component, OnInit, inject } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';
import { ReservationService } from '../../../../services/reservation.service';
import { Reservation } from '../../../../models/reservation';
import { AuthenticationService } from '../../../../services/authentication.service';
import { PdfService } from '../../../../services/pdf.service';
import { TEXT } from '../../../../shared/messages';

@Component({
  selector: 'app-requested-items-pending',
  templateUrl: './requested-items-pending.component.html',
  styleUrl: './requested-items-pending.component.css'
})
export class RequestedItemsPendingComponent implements ProfileSetting, OnInit {
  TEXT = TEXT;
  name: string = TEXT.PROFILE_REQUESTED_ITEMS_PENDING_NAME;
  routerLink: string = "requested-items/pending";
  reservationService = inject(ReservationService);
  pendingReservations: Array<Reservation>;
  authService = inject(AuthenticationService);
  pdfService = inject(PdfService);

  ngOnInit(): void {
    const userId = this.authService.currentUserId;
    this.reservationService.getPendingLendingsByUserId(userId).subscribe({
      next: reservationPage => {
        if (reservationPage._embedded) {
          this.pendingReservations = reservationPage._embedded.reservationResponseList;
        }
      }
    });
  }

  public saveAsPDF(): void {
    const data = document.getElementById('table');
    this.pdfService.saveAsPDF(data, "requested-items-pending");
  }
}
