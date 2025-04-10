import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { BasicSectionComponent } from '../../../components/sections/basic-section/basic-section.component';
import { Observable } from 'rxjs';
import { Page, Pageable } from '../../../../../shared/models/page';
import { WarehouseService } from '../../../core/services/warehouse.service';
import { BookItemRequestStatus } from '../../../shared/enums/book-item-request-status';
import { WarehouseBookItemRequestListView } from "../../../../../shared/models/rack";
import { TableUpdateEvent } from '../../../shared/models/table-event.interface';
import { ModalDialogComponent } from "../../../components/modal-dialog/modal-dialog.component";
import { ReactiveFormsModule } from '@angular/forms';
import { CardInProgressComponent } from "./cards/card-in-progress/card-in-progress.component";
import { CardPendingComponent } from "./cards/card-pending/card-pending.component";
import { ToastContainerComponent } from '../../../components/toasts/toast-container/toast-container.component';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [
    CommonModule, TranslateModule,
    BasicSectionComponent,
    ReactiveFormsModule,
    ModalDialogComponent,
    CardInProgressComponent,
    CardPendingComponent,
    ToastContainerComponent
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
  @ViewChild('toastContainer') toastContainer!: ToastContainerComponent;


  constructor(private warehouseService: WarehouseService) {}

  ngOnInit(): void {
      this.pendingRequestsPage$ = this.warehouseService.pendingRequests$;
      this.inProgressRequestsPage$ = this.warehouseService.inProgressRequests$;
      this.warehouseService.loadPageOfRequests({ status: BookItemRequestStatus.PENDING })
      this.warehouseService.loadPageOfRequests({ status: BookItemRequestStatus.IN_PROGRESS })
  }

  updatePendingRequests(event: TableUpdateEvent) {
    const pageable = new Pageable(event.page, event.size, event.sort);
    this.warehouseService.loadPageOfRequests({ status: BookItemRequestStatus.PENDING, query: event.query, pageable: pageable })
  }

  updateInProgressRequests(event: TableUpdateEvent) {
    const pageable = new Pageable(event.page, event.size, event.sort);
    this.warehouseService.loadPageOfRequests({ status: BookItemRequestStatus.IN_PROGRESS, query: event.query, pageable: pageable })
  }

  activePendingRequest(order: WarehouseBookItemRequestListView) {
    this.selectedPendingEl = this.selectedPendingEl==order ? undefined : order
  }
  activeInProgressRequest(order: WarehouseBookItemRequestListView) {
    this.selectedInProgressEl = this.selectedInProgressEl==order ? undefined : order
  }
  
  accept(bookItemRequest: WarehouseBookItemRequestListView) {
    this.warehouseService.moveRequestToInProgress(bookItemRequest);
  }

  backToPending(bookItemRequest: WarehouseBookItemRequestListView) {
    this.warehouseService.moveRequestToPending(bookItemRequest);
  }

  completeSelectedRequest() {
    if (this.selectedInProgressEl) {
      this.warehouseService.completeRequest(this.selectedInProgressEl).subscribe({
        next: () => this.toastContainer.showSuccess('CAT.TOAST.WAREHOUSE.REQUEST.COMPLETE.SUCCESS'),
        error: () => this.toastContainer.showError('CAT.TOAST.WAREHOUSE.REQUEST.COMPLETE.FAILURE')
      });
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
