import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TableComponent } from "../../components/tables/table/table.component";
import { TableUpdateEvent } from '../../shared/models/table-event.interface';
import { FavGenreChartComponent } from "../../components/charts/fav-genre-chart/fav-genre-chart.component";
import { AnnualActivityChartComponent } from '../../components/charts/annual-activity-chart/annual-activity-chart.component';
import { WeeklyActivityChartComponent } from "../../components/charts/weekly-activity-chart/weekly-activity-chart.component";
import { RegistrationFormComponent } from "../../components/registration-form/registration-form.component";
import { TopBorrowersComponent } from "../../components/tables/top-borrowers/top-borrowers.component";
import { UsersAgeGroupsChartComponent } from "../../components/charts/users-age-groups-chart/users-age-groups-chart.component";
import { TopCitiesComponent } from "../../components/tables/top-cities/top-cities.component";
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
    TableComponent, FavGenreChartComponent, AnnualActivityChartComponent,
    WeeklyActivityChartComponent, RegistrationFormComponent,
    TopBorrowersComponent, UsersAgeGroupsChartComponent,
    TopCitiesComponent,
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
    // this.usersPage$ = this.userService.getUsersPage(event.page, event.size, event.sort, event.query);
  }

  showDetails(userPreview: UserListPreviewAdmin | UserTopBorrowersAdmin) {
    const userId = userPreview.id;
    this.router.navigate([userId], { relativeTo: this.route });
  }

  generateFakeUsers() {
    this.userService.generateFakeUsers(10);
  }
}
