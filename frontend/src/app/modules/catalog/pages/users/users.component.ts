import { Component } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { PaginationComponent } from "../../components/pagination/pagination.component";
import { TableComponent } from "../../components/table/table.component";
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, TranslateModule, RouterModule, ReactiveFormsModule, PaginationComponent, TableComponent],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent {
  query: string;
  currentPage: number = 0;
  usersPage$ = this.userService.usersPage$;
  searchControl = new FormControl('');

  pageSizes: Size[] = [
    { value: 10, selected: false },
    { value: 15, selected: false },
    { value: 20, selected: false },
    { value: 50, selected: false }
  ];
  selectedSize: Size = this.pageSizes[0];
  sortState: Sort = { column: '', direction: undefined };

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.searchControl.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(searchQuery => {
      this.query = searchQuery ?? "";
      this.currentPage = 0;
      this.sortState = { column: '', direction: undefined };
      this.loadUsersPage();
    });
    this.loadUsersPage();
  }

  loadPage(pageIndex: number): void {
    this.currentPage = pageIndex;
    this.loadUsersPage();
  }

  changePageSize(event: any) {
    this.pageSizes.forEach(size => size.selected = false);
    this.selectedSize = this.pageSizes.find(size => size.value == event.target.value) || this.pageSizes[0];
    this.selectedSize.selected = true;
    this.currentPage = 0;
    this.loadUsersPage();
  }

  sort(event: any) {
    this.sortState = event as Sort;
    this.loadUsersPage();
  }

  onRowClick(userId: number) {
    this.router.navigate([userId], { relativeTo: this.route });
  }

  private loadUsersPage(): void {
    this.userService.getUsersPage(this.currentPage, this.selectedSize.value, this.sortState, this.query);
  }

}

interface Size {
  value: number;
  selected: boolean
}

export interface Sort {
  column: string;
  direction: 'asc' | 'desc' | undefined;
}