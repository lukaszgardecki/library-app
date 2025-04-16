import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { AnnualActivityChartComponent } from '../../components/charts/annual-activity-chart/annual-activity-chart.component';
import { FavGenreChartComponent } from '../../components/charts/fav-genre-chart/fav-genre-chart.component';
import { UsersAgeGroupsChartComponent } from '../../components/charts/users-age-groups-chart/users-age-groups-chart.component';
import { WeeklyActivityChartComponent } from '../../components/charts/weekly-activity-chart/weekly-activity-chart.component';
import { BasicSectionComponent } from '../../components/sections/basic-section/basic-section.component';
import { TableComponent } from '../../components/tables/table/table.component';
import { TopCitiesComponent } from '../../components/tables/top-cities/top-cities.component';
import { Observable } from 'rxjs';
import { Statistics } from '../../shared/models/users-stats-admin';
import { UserListPreviewAdmin, UserTopBorrowersAdmin } from '../../shared/models/user-details';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule, TranslateModule, RouterModule,
    TableComponent, FavGenreChartComponent, AnnualActivityChartComponent,
    WeeklyActivityChartComponent,
    UsersAgeGroupsChartComponent,
    TopCitiesComponent,
    BasicSectionComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  usersStats$: Observable<Statistics>;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.usersStats$ = this.userService.getUsersStatsAdmin();
  }

  showDetails(userPreview: UserListPreviewAdmin | UserTopBorrowersAdmin) {
      const userId = userPreview.id;
      this.router.navigate([userId], { relativeTo: this.route });
    }
}
