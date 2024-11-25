import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Page } from '../../../../shared/models/page';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-pagination',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent {
  @Input() page: Page;
  @Output() pageChange = new EventEmitter<number>();

  get pageNumbers(): number[] {
    const pageCount = this.page.totalPages;
    const startPage = Math.max(0, this.page.number - 2);
    const endPage = Math.min(pageCount - 1, this.page.number + 2);
    return Array.from({ length: endPage - startPage + 1 }, (_, i) => i + startPage);
  }

  changePage(pageNumber: number): void {
    if (pageNumber < 0 || pageNumber >= this.page.totalPages) return;
    this.pageChange.emit(pageNumber);
  }
}
