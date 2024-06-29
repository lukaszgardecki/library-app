import { DOCUMENT, Location } from '@angular/common';
import { Component, Inject, OnDestroy, OnInit, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-warehouse-page',
  templateUrl: './warehouse-page.component.html',
  styleUrl: './warehouse-page.component.css'
})
export class WarehousePageComponent implements OnInit, OnDestroy {
  ordersPending = [
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false},
    {selected: false}
  ];
  ordersInProgress: {selected: boolean}[] = [];

  constructor(
    private router: Router,
    private location: Location,
    private renderer: Renderer2,
    @Inject(DOCUMENT) private document: Document,
  ) {}

  ngOnInit(): void {
      this.renderer.setStyle(this.document.body, 'background-color', "#fff");
  }

  ngOnDestroy(): void {
    this.renderer.removeStyle(this.document.body, 'background-color');
  }

  active(order: any) {
    if (this.ordersPending.includes(order)) {
      this.ordersPending.filter(el => el !== order).forEach(el => el.selected = false);
    } else {
      this.ordersInProgress.filter(el => el !== order).forEach(el => el.selected = false);
    }
    
    order.selected = !order.selected;
  }
  
  accept(order: any) {
    const index = this.ordersPending.indexOf(order);
    if (index > -1) {
      this.ordersPending.splice(index, 1);
      order.selected = false;
      this.ordersInProgress.push(order);
    }
  }

  backToPending(order: any) {
    const index = this.ordersInProgress.indexOf(order);
    if (index > -1) {
      this.ordersInProgress.splice(index, 1);
      order.selected = false;
      this.ordersPending.push(order);
    }
  }

  complete(order: any) {
    const index = this.ordersInProgress.indexOf(order);
    if (index > -1) {
      this.ordersInProgress.splice(index, 1);
    }
  }

  exit() {
    this.router.navigate(['']);
  }
}
