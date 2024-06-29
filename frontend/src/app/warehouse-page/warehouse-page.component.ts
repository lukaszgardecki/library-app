import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnDestroy, OnInit, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation';
import { WarehouseService } from '../services/warehouse.service';

@Component({
  selector: 'app-warehouse-page',
  templateUrl: './warehouse-page.component.html',
  styleUrl: './warehouse-page.component.css'
})
export class WarehousePageComponent implements OnInit, OnDestroy {
  pendingReservations$: Observable<Reservation[]>;
  inProgressReservations: Reservation[] = [];

  constructor(
    private warehouseService: WarehouseService,
    private router: Router,
    private renderer: Renderer2,
    @Inject(DOCUMENT) private document: Document,
  ) {}

  ngOnInit(): void {
      this.renderer.setStyle(this.document.body, 'background-color', "#fff");
      this.pendingReservations$ = this.warehouseService.pendingReservations$;
  }

  ngOnDestroy(): void {
    this.renderer.removeStyle(this.document.body, 'background-color');
  }

  active(order: Reservation) {
    if (this.inProgressReservations.includes(order)) {
      this.inProgressReservations.filter(el => el !== order).forEach(el => el.selected = false);
    } else {
      this.pendingReservations$.subscribe({
        next: notifications => {
          notifications.filter(el => el !== order).forEach(el => el.selected = false);
        }
      })
    }
    order.selected = !order.selected;
  }
  
  accept(order: Reservation) {
    this.pendingReservations$.subscribe({
      next: notifications => {
        const index = notifications.indexOf(order);
        if (index > -1) {
          notifications.splice(index, 1);
          order.selected = false;
          this.inProgressReservations.push(order);
        }
      }
    });
  }

  backToPending(order: Reservation) {
    this.pendingReservations$.subscribe({
      next: notifications => {
        const index = this.inProgressReservations.indexOf(order);
        if (index > -1) {
          this.inProgressReservations.splice(index, 1);
          order.selected = false;
          notifications.push(order);
        }
      }
    });
  }

  complete(order: Reservation) {
    this.warehouseService.completeReservation(order.id).subscribe({
      next: reservation => {
        const index = this.inProgressReservations.indexOf(order);
        if (index > -1) {
          this.inProgressReservations.splice(index, 1);
        }   
      }
    });
  }

  exit() {
    this.router.navigate(['']);
  }
}
