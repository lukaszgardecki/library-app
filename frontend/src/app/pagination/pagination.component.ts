import { Component, Inject, Input, OnInit } from '@angular/core';
import { Page } from '../shared/page';
import { ActivatedRoute, Router } from '@angular/router';
import { ListComponent } from '../shared/list-component';


@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent{

  @Input()
  listComponent: ListComponent;

  pageSizes = [10, 20, 50, 100];

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {}

  goPrevPage() {
    const currPageNum = this.listComponent.page.number + 1;
    const prevPageNum = currPageNum - 1;
    this.goToPage(prevPageNum);
  }

  goNextPage() {
    const currPageNum = this.listComponent.page.number + 1;
    const nextPageNum = currPageNum + 1;
    this.goToPage(nextPageNum);
  }

  /**
   * 
   * @param number Page number (not index) counted from 1.
   * 
   */
  goToPage(number: number) {
    const btnValue = this.getValueOfBtnNo(number);
    
    if (typeof btnValue === 'number') {
      const page = btnValue - 1;
      const size = this.listComponent.page.size;
      const sort = this.route.snapshot.queryParams["sort"];
      this.updatePage(page, size, sort);
    }
  }

  changeSize(size: number) {
      const sort = this.route.snapshot.queryParams["sort"];
      this.updatePage(0, size, sort);
  }

  sort(sort?: any) {
    const size = this.listComponent.page.size;
    this.updatePage(0, size, sort);
  }

  private updatePage(pageIndex: number, pageSize: number, sort: any) {
    this.router.navigate([this.listComponent.routeName], { queryParams: { page: pageIndex, size: pageSize, sort: sort } });
    this.listComponent.getAll(pageIndex, pageSize, sort);
  }
  
  isSelectedFirst(): boolean {
    return this.listComponent.page.number === 0;
  }

  isSelectedLast(): boolean {
    return this.listComponent.page.number === this.listComponent.page.totalPages-1;
  }

  isSelected(count: number): boolean {
    return this.listComponent.page.number === count-1;
  }

  isBlankBtn(buttonNo: number): boolean {
    return this.getValueOfBtnNo(buttonNo).toString() === '...';
  }

  arePagesMin(amount: number): boolean {
    return this.listComponent.page.totalPages >= amount;
  }

  arePagesMax(amount: number): boolean {
    return this.listComponent.page.totalPages <= amount;
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
        isActive = (this.listComponent.page.number > 2 && this.listComponent.page.number < this.listComponent.page.totalPages - 3) || (this.arePagesMax(6) && this.isSelected(4));
        break;
      case 5:
        isActive = (this.arePagesMax(6) && this.isSelected(5)) || (this.arePagesMin(7) && this.isSelected(this.listComponent.page.totalPages - 2));
        break;
      case 6:
        isActive = (this.arePagesMax(6) && this.isSelected(6)) || (this.arePagesMin(7) && this.isSelected(this.listComponent.page.totalPages - 1));
        break;
      case 7:
        isActive = this.isSelected(this.listComponent.page.totalPages);
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
        value = this.listComponent.page.number < 4 || this.arePagesMax(7) ? 2: '...';
        break;
      case 3:
        value = this.listComponent.page.number > 3 && this.listComponent.page.number < this.listComponent.page.totalPages - 3 ? this.listComponent.page.number : this.listComponent.page.number >= this.listComponent.page.totalPages - 4 && this.arePagesMin(7) ? this.listComponent.page.totalPages - 4 : 3 ;
        break;
      case 4:
        value = this.listComponent.page.number > 3 && this.listComponent.page.number < this.listComponent.page.totalPages - 3 ? this.listComponent.page.number + 1 : this.listComponent.page.number >= this.listComponent.page.totalPages - 3 && this.arePagesMin(7) ? this.listComponent.page.totalPages - 3 : 4 ;
        break;
      case 5:
        value = this.listComponent.page.number > 3 && this.listComponent.page.number < this.listComponent.page.totalPages - 3 ? this.listComponent.page.number + 2 : this.listComponent.page.number >= this.listComponent.page.totalPages - 3 && this.arePagesMin(7) ? this.listComponent.page.totalPages - 2 : 5;
        break;
      case 6:
        value = this.arePagesMax(7) ? 6 : this.listComponent.page.number >= this.listComponent.page.totalPages - 4 ? this.listComponent.page.totalPages - 1 : '...';
        break;
      case 7:
        value = this.listComponent.page.totalPages;
        break;
    }
    return value;
  }

  
}