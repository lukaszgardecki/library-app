import { Component, inject, Input, OnInit } from '@angular/core';
import { ProfileSetting } from '../../user-page/profile-dashboard/profile-dashboard.component';
import { TEXT } from '../../shared/messages';
import { Pageable } from '../../shared/pageable';
import { UserService } from '../../services/user.service';
import { UsersPage } from '../../models/users-page';
import { Observable } from 'rxjs';
import { UserListPreviewAdmin } from '../../models/user-details';
import { Router } from '@angular/router';
import { Size } from '../../shared/sortable';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements ProfileSetting, Pageable, OnInit {
  TEXT = TEXT;
  name: string = TEXT.ADMIN_USERS_NAME;
  routerLink: string = "users";
  usersToSearch: string;
  usersPage$: Observable<UsersPage>;
  pageSizes: Size[] = [new Size(10), new Size(15), new Size(20), new Size(50)];
  selectedSize: Size = this.pageSizes[0];
  userService = inject(UserService);
  router = inject(Router);

  ngOnInit(): void {
    this.usersPage$ = this.userService.usersPage$;
    let params = this.createParams();
    this.userService.getUsersPage(params);
  }

  loadPage(pageIndex: number): void {
    let params = this.createParams(pageIndex);
    this.userService.getUsersPage(params);
  }

  showDetails(user: UserListPreviewAdmin) {
    this.router.navigate(['/admin/users', user.id]);
  }

  searchUsers() {
    let params = this.createParams();
    this.userService.getUsersPage(params);
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'ACTIVE':
        return 'status-active';
      case 'INACTIVE':
        return 'status-inactive';
      case 'SUSPENDED':
        return 'status-suspended';
      case 'CLOSED':
        return 'status-closed';
      case 'PENDING':
        return 'status-pending';
      default:
        return '';
    }
  }

  changeSize(size: Size) {
    this.selectedSize = size;
    let params = this.createParams();
    this.userService.getUsersPage(params);
  }

  private createParams(page: number = 0): HttpParams {
    return new HttpParams()
    .set("page", page)
    .set("size", this.selectedSize.value)
    .set("q", this.usersToSearch || "");
  }
}
