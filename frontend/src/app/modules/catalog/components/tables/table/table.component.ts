import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Sort } from '../../../shared/models/sort.interface';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Size } from '../../../shared/models/size.interface';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { PdfService } from '../../../core/services/pdf.service';
import { PaginationComponent } from "../../pagination/pagination.component";
import { Page } from '../../../../../shared/models/page';
import { TableUpdateEvent } from '../../../shared/models/table-event.interface';
import { EnumNamePipe } from '../../../../../shared/pipes/enum-name.pipe';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule, TranslateModule, ReactiveFormsModule, PaginationComponent, EnumNamePipe],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent {
  @Input() tableName: string = "";
  tableId: string;
  @Input() columns: { key: string; label: string; type?: string }[] = [];
  @Input() data?: any[] = [];
  @Input() page: Page = new Page();
  @Output() onUpdate = new EventEmitter<TableUpdateEvent>();
  @Output() onRowClick = new EventEmitter<number>();
  searchControl = new FormControl('');
  sortState: Sort = { columnKey: '', direction: undefined };
  pageSizes: Size[] = [
    { value: 10, selected: false },
    { value: 15, selected: false },
    { value: 20, selected: false },
    { value: 50, selected: false }
  ];
  selectedSize: Size = this.pageSizes[0];
  query: string;
  currentPage: number = 0;

  constructor(private pdfService: PdfService) { }

  ngOnInit(): void {
    this.searchControl.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(searchQuery => {
      this.query = searchQuery ?? "";
      this.currentPage = 0;
      this.sortState = { columnKey: '', direction: undefined };
      this.loadUsersPage();
    });
    this.loadUsersPage();
    this.tableId = `${this.tableName}-${Math.random().toString().slice(2, 7)}`;
  }

  onSort(column: string) {
    const isCurrentlySorted = this.sortState.columnKey === column;

    this.sortState.direction =
      isCurrentlySorted
        ? this.sortState.direction === undefined
          ? 'asc'
          : this.sortState.direction === 'asc'
          ? 'desc'
          : undefined
        : 'asc';

    this.sortState.columnKey = column;
    this.loadUsersPage();
  }

  showDetails(itemId: number) {
    this.onRowClick.emit(itemId);
  }

  loadPage(pageIndex: number) {
    this.currentPage = pageIndex;
    this.loadUsersPage();
  }

  getValue(obj: any, key: string): any {
    if (!key) return obj;
    const keys = key.split('.');
    return keys.reduce((acc, curr) => acc?.[curr], obj);
  }

  changePageSize(event: any) {
    this.pageSizes.forEach(size => size.selected = false);
    this.selectedSize = this.pageSizes.find(size => size.value == event.target.value) || this.pageSizes[0];
    this.selectedSize.selected = true;
    this.currentPage = 0;
    this.loadUsersPage();
  }

  saveAsPDF(): void {
    const data = document.getElementById(this.tableId);
    this.pdfService.saveAsPDF(data, this.tableId);
  }

  private loadUsersPage(): void {
    let event: TableUpdateEvent = { 
      page: this.currentPage, 
      size: this.selectedSize.value, 
      sort: this.sortState, 
      query: this.query 
    }; 
    this.onUpdate.emit(event);
  }
}


