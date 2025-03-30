import { ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { TimeAgoPipe } from "../../../../../../../shared/pipes/time-ago.pipe";
import { WarehouseBookItemRequestListView } from '../../../../../shared/models/book-item-request';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { EnumNamePipe } from "../../../../../../../shared/pipes/enum-name.pipe";

@Component({
  selector: 'app-card-pending',
  standalone: true,
  imports: [CommonModule, TimeAgoPipe, TranslateModule, EnumNamePipe],
  templateUrl: './card-pending.component.html',
  styleUrl: './card-pending.component.css'
})
export class CardPendingComponent implements OnInit, OnDestroy {
  @Input() selectedRequest: WarehouseBookItemRequestListView | undefined;
  @Input() request: WarehouseBookItemRequestListView;
  @Input() number: number;
  @Output() onSelect = new EventEmitter<WarehouseBookItemRequestListView>();
  @Output() onAccept = new EventEmitter<WarehouseBookItemRequestListView>();
  private intervalId!: ReturnType<typeof setInterval>;

  constructor(private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.intervalId = setInterval(() => {
      this.cdr.detectChanges();
    }, 1000);
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  active(request: WarehouseBookItemRequestListView) {
    this.onSelect.emit(request);
  }

  accept(request: WarehouseBookItemRequestListView) {
    this.onAccept.emit(request);
  }
}
