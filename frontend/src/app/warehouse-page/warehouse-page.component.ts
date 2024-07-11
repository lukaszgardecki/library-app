import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnDestroy, OnInit, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation';
import { WarehouseService } from '../services/warehouse.service';
import { TEXT } from '../shared/messages';

@Component({
  selector: 'app-warehouse-page',
  templateUrl: './warehouse-page.component.html',
  styleUrl: './warehouse-page.component.css'
})
export class WarehousePageComponent implements OnInit, OnDestroy {
  TEXT = TEXT;
  pendingReservations$: Observable<Reservation[]>;
  inProgressReservations$: Observable<Reservation[]>;

  constructor(
    private warehouseService: WarehouseService,
    private router: Router,
    private renderer: Renderer2,
    @Inject(DOCUMENT) private document: Document,
  ) {}

  ngOnInit(): void {
      this.renderer.setStyle(this.document.body, 'background-color', "#fff");
      this.pendingReservations$ = this.warehouseService.pendingReservations$;
      this.inProgressReservations$ = this.warehouseService.inProgressReservations$;
  }

  ngOnDestroy(): void {
    this.renderer.removeStyle(this.document.body, 'background-color');
  }

  active(order: Reservation) {
    this.warehouseService.select(order);
  }
  
  accept(order: Reservation) {
    this.warehouseService.moveToInProgress(order);
  }

  backToPending(order: Reservation) {
    this.warehouseService.backToPendingReservations(order);
  }

  complete(order: Reservation) {
    this.warehouseService.complete(order);
  }

  exit() {
    this.router.navigate(['']);
  }
}
