import { Component, Input, output} from '@angular/core';
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
  selectedSortTypeName: string = "Sortuj";

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {}

  goPrevPage() {
    const page = this.listComponent.page.number - 1;
    const size = this.route.snapshot.queryParams["size"]
    const sort = this.route.snapshot.queryParams["sort"];
    this.updatePage(page, size, sort);
  }

  goNextPage() {
    const page = this.listComponent.page.number + 1;
    const size = this.route.snapshot.queryParams["size"]
    const sort = this.route.snapshot.queryParams["sort"];
    this.updatePage(page, size, sort);
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
      const size = this.listComponent.page.size;
      const sort = this.route.snapshot.queryParams["sort"];
      this.updatePage(page, size, sort);
    }
  }

  changeSize(size: number) {
      const sort = this.route.snapshot.queryParams["sort"];
      this.updatePage(0, size, sort);
  }

  sort(sort: any) {
    const size = this.listComponent.page.size;
    const selectedSortType = this.listComponent.sortTypes.filter(t => t.queryParam===sort)[0];
    this.updatePage(0, size, sort);
    this.selectedSortTypeName = selectedSortType.queryParam ? selectedSortType.name : "Sortuj";
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
    let pageNum = this.listComponent.page.number + 1; // page.number is an index (numerated from 0)
    let totalPages = this.listComponent.page.totalPages;
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