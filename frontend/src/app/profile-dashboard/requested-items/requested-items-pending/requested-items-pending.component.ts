import { Component, OnInit, inject } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';
import { ReservationService } from '../../../services/reservation.service';
import { Reservation } from '../../../models/reservation';
import { AuthenticationService } from '../../../services/authentication.service';
import { PdfService } from '../../../services/pdf.service';

@Component({
  selector: 'app-requested-items-pending',
  templateUrl: './requested-items-pending.component.html',
  styleUrl: './requested-items-pending.component.css'
})
export class RequestedItemsPendingComponent implements ProfileSetting, OnInit {
  name: string = "Pending";
  routerLink: string = "pending";
  reservationService = inject(ReservationService);
  pendingReservations: Array<Reservation>;
  authService = inject(AuthenticationService);
  pdfService = inject(PdfService);

  ngOnInit(): void {
    const userId = this.authService.currentUserId;
    this.reservationService.getPendingLendingsByUserId(userId).subscribe({
      next: reservationPage => {
        if (reservationPage._embedded) {
          this.pendingReservations = reservationPage._embedded.reservationResponseList.filter(res => res.status === "PENDING");
        }
      }
    });
  }

  public saveAsPDF(): void {
    const data = document.getElementById('table');
    this.pdfService.saveAsPDF(data, "requested-items-pending");
  }
}
