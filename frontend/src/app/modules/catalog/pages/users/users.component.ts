import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TableComponent } from "../../components/tables/table/table.component";
import { TableUpdateEvent } from '../../shared/models/table-event.interface';
import { Observable } from 'rxjs';
import { Page, Pageable } from '../../../../shared/models/page';
import { UserListPreviewAdmin, UserTopBorrowersAdmin } from '../../shared/models/user-details';
import { Statistics } from '../../shared/models/users-stats-admin';
import { BasicSectionComponent } from "../../components/sections/basic-section/basic-section.component";

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    CommonModule, TranslateModule, RouterModule,
    TableComponent,
    BasicSectionComponent
],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {
  usersPage: Page<UserListPreviewAdmin>;
  usersStats$: Observable<Statistics>;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.usersStats$ = this.userService.getUsersStatsAdmin();
    this.userService.getUsersPage().subscribe({next: page => {this.usersPage = page}});
  }

  updateTable(event: TableUpdateEvent) {
    const pageable = new Pageable(event.page, event.size, event.sort);
    this.userService.getUsersPage(event.query, pageable).subscribe({next: page => {this.usersPage = page}});
  }

  showDetails(userPreview: UserListPreviewAdmin | UserTopBorrowersAdmin) {
    const userId = userPreview.id;
    this.router.navigate([userId], { relativeTo: this.route });
  }

  goToRegistrationForm() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }

  generateFakeUsers() {
    this.userService.generateFakeUsers(10);
  }
}
