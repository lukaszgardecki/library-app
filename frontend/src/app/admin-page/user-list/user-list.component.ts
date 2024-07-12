import { Component, inject, OnInit } from '@angular/core';
import { ProfileSetting } from '../../user-page/profile-dashboard/profile-dashboard.component';
import { TEXT } from '../../shared/messages';
import { Pageable } from '../../shared/pageable';
import { UserService } from '../../services/user.service';
import { UsersPage } from '../../models/users-page';
import { Observable } from 'rxjs';
import { UserDetails } from '../../models/user-details';
import { Router } from '@angular/router';

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
  userService = inject(UserService);
  router = inject(Router);

  ngOnInit(): void {
      this.usersPage$ = this.userService.usersPage$;
      this.userService.getUsersPage(0);
  }

  loadPage(pageIndex: number): void {
    this.userService.getUsersPage(pageIndex, this.usersToSearch);
  }

  showDetails(user: UserDetails) {
    this.router.navigate(['/admin/users', user.id]);
  }

  searchUsers() {
    this.userService.getUsersPage(0, this.usersToSearch);
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
}
