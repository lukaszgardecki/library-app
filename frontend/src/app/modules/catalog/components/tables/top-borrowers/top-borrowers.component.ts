import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { EnumNamePipe } from "../../../../../shared/pipes/enum-name.pipe";
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-top-borrowers',
  standalone: true,
  imports: [CommonModule, TranslateModule, EnumNamePipe],
  templateUrl: './top-borrowers.component.html',
  styleUrl: './top-borrowers.component.css'
})
export class TopBorrowersComponent {
  @Input() columns: { key: string; label: string; type?: string }[] = [];
  @Input() data?: any[] = [];
  @Output() onRowClick = new EventEmitter<number>();


  showDetails(itemId: number) {
    this.onRowClick.emit(itemId);
  }

  getValue(obj: any, key: string): any {
    if (!key) return obj;
    const keys = key.split('.');
    return keys.reduce((acc, curr) => acc?.[curr], obj);
  }
}
