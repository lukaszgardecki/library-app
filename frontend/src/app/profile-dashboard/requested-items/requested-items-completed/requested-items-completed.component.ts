import { Component, OnInit, inject } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';
import { Reservation } from '../../../models/reservation';
import { AuthenticationService } from '../../../services/authentication.service';
import { PdfService } from '../../../services/pdf.service';
import { ReservationService } from '../../../services/reservation.service';

@Component({
  selector: 'app-requested-items-completed',
  templateUrl: './requested-items-completed.component.html',
  styleUrl: './requested-items-completed.component.css'
})
export class RequestedItemsCompletedComponent implements ProfileSetting, OnInit {
  name: string = "Completed";
  routerLink: string = "completed";
  reservationService = inject(ReservationService);
  completedReservations: Array<Reservation>;
  authService = inject(AuthenticationService);
  pdfService = inject(PdfService);

  ngOnInit(): void {
    const userId = this.authService.currentUserId;
    this.reservationService.getPendingLendingsByUserId(userId).subscribe({
      next: reservationPage => {
        if (reservationPage._embedded) {
          this.completedReservations = reservationPage._embedded.reservationResponseList.filter(res => res.status === "READY");
        }
      }
    });
  }

  public saveAsPDF(): void {
    const data = document.getElementById('table');
    this.pdfService.saveAsPDF(data, "requested-items-completed");
  }
}
