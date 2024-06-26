import { Component, EventEmitter, Input, Output} from '@angular/core';
import { Page } from '../shared/page';


@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent {
  @Input() page: Page;
  @Output() pageChange = new EventEmitter<number>();

  goPrevPage() {
    this.updatePage(this.page.number - 1);
  }

  goNextPage() {
    this.updatePage(this.page.number + 1);
  }

  /**
   * 
   * @param number Number of button 1-7 !!!.
   * 
   */
  goToPageFromBtn(number: number) {
    const btnValue = this.getValueOfBtnNo(number);
    
    if (typeof btnValue === 'number') {
      const page = btnValue - 1;
      this.updatePage(page);
    }
  }

  private updatePage(pageIndex: number) {
    if (pageIndex < 0 || pageIndex >= this.page.totalPages) {
      return;
    }
    this.pageChange.emit(pageIndex);
  }
  
  isSelectedFirst(): boolean {
    return this.page.number === 0;
  }

  isSelectedLast(): boolean {
    return this.page.number === this.page.totalPages-1;
  }

  isSelected(count: number): boolean {
    return this.page.number === count-1;
  }

  isBlankBtn(buttonNo: number): boolean {
    return this.getValueOfBtnNo(buttonNo).toString() === '...';
  }

  arePagesMin(amount: number): boolean {
    return this.page.totalPages >= amount;
  }

  arePagesMax(amount: number): boolean {
    return this.page.totalPages <= amount;
  }

  isActive(btnNo: number): boolean {
    let isActive = false;
    switch(btnNo) {
      case 1:
        isActive = this.isSelected(1);
        break;
      case 2:
        isActive = this.isSelected(2);
        break;
      case 3:
        isActive = this.isSelected(3);
        break;
      case 4:
        isActive = (this.page.number > 2 && this.page.number < this.page.totalPages - 3) || (this.arePagesMax(6) && this.isSelected(4));
        break;
      case 5:
        isActive = (this.arePagesMax(6) && this.isSelected(5)) || (this.arePagesMin(7) && this.isSelected(this.page.totalPages - 2));
        break;
      case 6:
        isActive = (this.arePagesMax(6) && this.isSelected(6)) || (this.arePagesMin(7) && this.isSelected(this.page.totalPages - 1));
        break;
      case 7:
        isActive = this.isSelected(this.page.totalPages);
        break;
    }
    return isActive;
  }

  getValueOfBtnNo(number: number): number | string {
    let value: number | string = 0;
    let pageNum = this.page.number + 1; // page.number is an index (numerated from 0)
    let totalPages = this.page.totalPages;
    switch(number) {
      case 1:
        value = 1;
        break;
      case 2:
        value = this.arePagesMax(7) || pageNum <= 4 ? 2: '...';
        break;
      case 3:
        value = this.arePagesMax(7) || pageNum <= 4 ? 3 : pageNum > totalPages - 3 ? totalPages - 4 : pageNum - 1;
        break;
      case 4:
        value = this.arePagesMax(7) || pageNum <= 4 ? 4 : pageNum > totalPages - 3 ? totalPages - 3 : pageNum;
        break;
      case 5:
        value = this.arePagesMax(7) || pageNum <= 4 ? 5 : pageNum > totalPages - 3 ? totalPages - 2 : pageNum + 1;
        break;
      case 6:
        value = this.arePagesMax(7) ? 6 : pageNum >= totalPages - 3 ? totalPages - 1 : '...';
        break;
      case 7:
        value = totalPages;
        break;
    }
    return value;
  }

}