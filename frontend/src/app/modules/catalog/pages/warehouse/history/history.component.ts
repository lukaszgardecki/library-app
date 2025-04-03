import { Component } from '@angular/core';
import { BasicSectionComponent } from "../../../components/sections/basic-section/basic-section.component";
import { TableComponent } from "../../../components/tables/table/table.component";
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { Page, Pageable } from '../../../../../shared/models/page';
import { WarehouseBookItemRequestListView } from "../../../../../shared/models/rack";
import { WarehouseService } from '../../../core/services/warehouse.service';
import { TableUpdateEvent } from '../../../shared/models/table-event.interface';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule, TranslateModule, TableComponent, BasicSectionComponent],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {
  allRequestsPage$: Observable<Page<WarehouseBookItemRequestListView>>;

  constructor(private warehouseService: WarehouseService) {}
  
  ngOnInit(): void {
      this.allRequestsPage$ = this.warehouseService.allRequests$;
  }

  updateAllRequests(event: TableUpdateEvent) {
    const pageable = new Pageable(event.page, event.size, event.sort);
    this.warehouseService.loadPageOfRequests({ query: event.query, pageable: pageable })
  }

  display(bookItemRequest: WarehouseBookItemRequestListView) {
    
  }
}
