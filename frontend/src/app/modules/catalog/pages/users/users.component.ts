import { Component } from '@angular/core';
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

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, TranslateModule, RouterModule, TableComponent, FavGenreChartComponent, AnnualActivityChartComponent, WeeklyActivityChartComponent, RegistrationFormComponent, TopBorrowersComponent, UsersAgeGroupsChartComponent],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent {
  usersPage$ = this.userService.usersPage$;
  usersStats$ = this.userService.getUsersStatsAdmin();

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  updateTable(event: TableUpdateEvent) {
    this.userService.getUsersPage(event.page, event.size, event.sort, event.query);
  }

  showDetails(userId: number) {
    this.router.navigate([userId], { relativeTo: this.route });
  }

  generateFakeUsers() {
    this.userService.generateFakeUsers(10);
  }
}
