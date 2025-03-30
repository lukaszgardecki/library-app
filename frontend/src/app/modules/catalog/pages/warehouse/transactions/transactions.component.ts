import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { BasicSectionComponent } from '../../../components/sections/basic-section/basic-section.component';
import { Observable } from 'rxjs';
import { Page, Pageable } from '../../../../../shared/models/page';
import { WarehouseService } from '../../../core/services/warehouse.service';
import { BookItemRequestStatus } from '../../../shared/enums/book-item-request-status';
import { WarehouseBookItemRequestListView } from '../../../shared/models/book-item-request';
import { TableUpdateEvent } from '../../../shared/models/table-event.interface';
import { ConfirmModalDialogComponent } from "../../../components/confirm-modal-dialog/confirm-modal-dialog.component";
import { ReactiveFormsModule } from '@angular/forms';
import { CardInProgressComponent } from "./cards/card-in-progress/card-in-progress.component";
import { CardPendingComponent } from "./cards/card-pending/card-pending.component";

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [
    CommonModule, TranslateModule, BasicSectionComponent, ReactiveFormsModule,
    ConfirmModalDialogComponent, CardInProgressComponent, CardPendingComponent
],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent implements OnInit {
  pendingRequestsPage$: Observable<Page<WarehouseBookItemRequestListView>>;
  inProgressRequestsPage$: Observable<Page<WarehouseBookItemRequestListView>>;
  selectedPendingEl: WarehouseBookItemRequestListView | undefined;
  selectedInProgressEl: WarehouseBookItemRequestListView | undefined;
  isLoading = false;

  constructor(private warehouseService: WarehouseService) {}

  ngOnInit(): void {
      this.pendingRequestsPage$ = this.warehouseService.pendingRequests$;
      this.inProgressRequestsPage$ = this.warehouseService.inProgressRequests$;
      this.warehouseService.loadPageOfRequests(BookItemRequestStatus.PENDING,"", new Pageable())
      this.warehouseService.loadPageOfRequests(BookItemRequestStatus.IN_PROGRESS,"", new Pageable())
  }

  updatePendingRequests(event: TableUpdateEvent) {
    const pageable = new Pageable(event.page, event.size, event.sort);
    this.warehouseService.loadPageOfRequests(BookItemRequestStatus.PENDING, event.query, pageable)
  }

  updateInProgressRequests(event: TableUpdateEvent) {
    const pageable = new Pageable(event.page, event.size, event.sort);
    this.warehouseService.loadPageOfRequests(BookItemRequestStatus.IN_PROGRESS, event.query, pageable)
  }

  activePendingRequest(order: WarehouseBookItemRequestListView) {
    this.selectedPendingEl = this.selectedPendingEl==order ? undefined : order
  }
  activeInProgressRequest(order: WarehouseBookItemRequestListView) {
    this.selectedInProgressEl = this.selectedInProgressEl==order ? undefined : order
  }
  
  accept(bookItemRequest: WarehouseBookItemRequestListView) {
    this.warehouseService.moveToInProgress(bookItemRequest);
  }

  backToPending(bookItemRequest: WarehouseBookItemRequestListView) {
    this.warehouseService.moveToPending(bookItemRequest);
  }

  completeSelectedRequest() {
    if (this.selectedInProgressEl != undefined) {
      this.warehouseService.completeRequest(this.selectedInProgressEl);
    }
  }

  onScroll(event: any): void {
    const element = event.target;
    if (this.isLoading) return;
  
    if (element.scrollHeight - element.scrollTop <= element.clientHeight + 50) {
      this.isLoading = true;
      this.warehouseService.addNextPageToPendingRequests()?.add(() => {
        this.isLoading = false;
      })
    }
  }
}
