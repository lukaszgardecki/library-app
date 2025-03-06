import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { BasicSectionComponent } from '../../../components/sections/basic-section/basic-section.component';
import { TableComponent } from '../../../components/tables/table/table.component';
import { Observable } from 'rxjs';
import { Page, Pageable } from '../../../../../shared/models/page';
import { WarehouseService } from '../../../core/services/warehouse.service';
import { BookItemRequestStatus } from '../../../shared/enums/book-item-request-status';
import { BookItemRequest } from '../../../shared/models/book-item-request';
import { TableUpdateEvent } from '../../../shared/models/table-event.interface';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, TranslateModule, TableComponent, BasicSectionComponent],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent implements OnInit {
  pendingRequestsPage$: Observable<Page<BookItemRequest>>;
  inProgressRequestsPage$: Observable<Page<BookItemRequest>>;
  

  constructor(private warehouseService: WarehouseService) {}

  ngOnInit(): void {
      this.pendingRequestsPage$ = this.warehouseService.pendingRequests$;
      this.inProgressRequestsPage$ = this.warehouseService.inProgressRequests$;
  }

  updatePendingRequests(event: TableUpdateEvent) {
    const pageable = new Pageable(event.page, event.size, event.sort);
    this.warehouseService.getRequests(BookItemRequestStatus.PENDING, event.query, pageable)
  }

  updateInProgressRequests(event: TableUpdateEvent) {
    const pageable = new Pageable(event.page, event.size, event.sort);
    this.warehouseService.getRequests(BookItemRequestStatus.IN_PROGRESS, event.query, pageable)
  }
  
  accept(bookItemRequest: BookItemRequest) {
    this.warehouseService.moveToInProgress(bookItemRequest);
  }

  backToPending(bookItemRequest: BookItemRequest) {
    this.warehouseService.moveToPending(bookItemRequest);
  }

  complete(bookItemRequest: BookItemRequest) {
    this.warehouseService.completeRequest(bookItemRequest);
  }
}
