import { Component, EventEmitter, Input, Output } from '@angular/core';
import { WarehouseBookItemRequestListView } from "../../../../../../../shared/models/rack";
import { EnumNamePipe } from "../../../../../../../shared/pipes/enum-name.pipe";
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-card-in-progress',
  standalone: true,
  imports: [CommonModule, EnumNamePipe, TranslateModule],
  templateUrl: './card-in-progress.component.html',
  styleUrl: './card-in-progress.component.css'
})
export class CardInProgressComponent {
  @Input() selectedRequest: WarehouseBookItemRequestListView | undefined;
  @Input() request: WarehouseBookItemRequestListView;
  @Input() number: number;
  @Output() onSelect = new EventEmitter<WarehouseBookItemRequestListView>();
  @Output() onBackToPending = new EventEmitter<WarehouseBookItemRequestListView>();
  @Output() onCompleteRequest = new EventEmitter<void>();

  active(request: WarehouseBookItemRequestListView) {
    this.onSelect.emit(request);
  }
}