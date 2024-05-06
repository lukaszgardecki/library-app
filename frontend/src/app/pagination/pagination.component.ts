import { Component, Input, OnInit } from '@angular/core';
import { Page } from '../shared/page';
import { BookItemListComponent } from '../book-item-list/book-item-list.component';
import { isatty } from 'tty';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent{

  @Input()
  page: Page;

  constructor(private bookItemList: BookItemListComponent) {}

  goPrevPage() {
    // this.router.navigateByUrl(this.links.prev.href);
    const prevPage = this.page.number-1;
    // const pageSize = this.page.size;
    const pageSize = this.page.size;
    // this.router.navigate(['book-items', {page: prevPage, size: pageSize}])
    this.bookItemList.getAllBookItems(prevPage, pageSize);
  }

  goNextPage() {
    const nextPage = this.page.number+1;
    // const pageSize = this.page.size;
    const pageSize = this.page.size;
    // this.router.navigateByUrl(this.links.next.href);
    // console.log(this.links.next.href);
    this.bookItemList.getAllBookItems(nextPage, pageSize);
  }

  goToPage(number: number) {
    const btnValue = this.getValueOfBtnNo(number);
    
    if (typeof btnValue === 'number') {
      const pageIndex = btnValue - 1;
      const pageSize = this.page.size;
      this.bookItemList.getAllBookItems(pageIndex, pageSize);
    }
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
    switch(number) {
      case 1:
        value = 1;
        break;
      case 2:
        value = this.page.number < 4 || this.arePagesMax(7) ? 2: '...';
        break;
      case 3:
        value = this.page.number > 3 && this.page.number < this.page.totalPages - 3 ? this.page.number : this.page.number >= this.page.totalPages - 4 && this.arePagesMin(7) ? this.page.totalPages - 4 : 3 ;
        break;
      case 4:
        value = this.page.number > 3 && this.page.number < this.page.totalPages - 3 ? this.page.number + 1 : this.page.number >= this.page.totalPages - 3 && this.arePagesMin(7) ? this.page.totalPages - 3 : 4 ;
        break;
      case 5:
        value = this.page.number > 3 && this.page.number < this.page.totalPages - 3 ? this.page.number + 2 : this.page.number >= this.page.totalPages - 3 && this.arePagesMin(7) ? this.page.totalPages - 2 : 5;
        break;
      case 6:
        value = this.arePagesMax(7) ? 6 : this.page.number >= this.page.totalPages - 4 ? this.page.totalPages - 1 : '...';
        break;
      case 7:
        value = this.page.totalPages;
        break;
    }
    return value;
  }

  
}