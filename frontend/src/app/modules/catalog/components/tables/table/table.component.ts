import { CommonModule } from '@angular/common';
import { Component, ContentChild, EventEmitter, Input, Output, TemplateRef } from '@angular/core';
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
export class TableComponent<T> {

  @ContentChild(TemplateRef) rowTemplate: TemplateRef<any>;
  @Input() tableName: string = "";
  tableId: string;
  @Input() columns: { key: string; label: string; type?: string }[] = [];
  @Input() data?: T[] = [];
  @Input() page: Page<T> = new Page();
  @Output() onUpdate = new EventEmitter<TableUpdateEvent>();
  @Output() onRowClick = new EventEmitter<T>();
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
  @Input() options: TableOptions = {
    pagination: true,
    searchField: true,
    pageSize: true,
    shareExportBtns: true
  }

  constructor(private pdfService: PdfService) { }

  ngOnInit(): void {
    this.searchControl.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(searchQuery => {
      this.query = searchQuery ?? "";
      this.currentPage = 0;
      this.sortState = { columnKey: '', direction: undefined };
      this.updateTable();
    });
    this.updateTable();
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
    this.updateTable();
  }

  onClick(item: T) {
    this.onRowClick.emit(item);
  }

  loadPage(pageIndex: number) {
    this.currentPage = pageIndex;
    this.updateTable();
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
    this.updateTable();
  }

  saveAsPDF(): void {
    const data = document.getElementById(this.tableId);
    this.pdfService.saveAsPDF(data, this.tableId);
  }

  getStartRow(page: Page<any>): number {
    return (page.number * page.size) + 1;
  }
  
  getEndRow(page: Page<any>): number {
    return Math.min((page.number + 1) * page.size, page.totalElements);
  }

  private updateTable(): void {
    let event: TableUpdateEvent = { 
      page: this.currentPage, 
      size: this.selectedSize.value, 
      sort: this.sortState, 
      query: this.query 
    }; 
    this.onUpdate.emit(event);
  }
}

export interface TableOptions {
  pagination: boolean
  pageSize: boolean
  searchField: boolean
  shareExportBtns: boolean
}
