import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent {
  @Input() columns: { key: string; label: string }[] = [];
  @Input() data?: any[] = [];
  @Output() sortChange = new EventEmitter<{ column: string; direction: 'asc' | 'desc' | undefined }>();
  @Output() rowClick = new EventEmitter<number>();

  sortState: { column: string; direction: 'asc' | 'desc' | undefined } = { column: '', direction: undefined };


  onSort(column: string) {
    const isCurrentlySorted = this.sortState.column === column;

    this.sortState.direction =
      isCurrentlySorted
        ? this.sortState.direction === undefined
          ? 'asc'
          : this.sortState.direction === 'asc'
          ? 'desc'
          : undefined
        : 'asc';

    this.sortState.column = column;

    this.sortChange.emit(this.sortState);
  }

  onRowClick(itemId: number) {
    this.rowClick.emit(itemId);
  }
}
