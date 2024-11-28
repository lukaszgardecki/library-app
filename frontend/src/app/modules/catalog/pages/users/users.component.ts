import { Component } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TableComponent } from "../../components/table/table.component";
import { TableUpdateEvent } from '../../shared/models/table-event.interface';
import { FavGenreChartComponent } from "../../components/charts/fav-genre-chart/fav-genre-chart.component";
import { AnnualActivityChartComponent } from '../../components/charts/annual-activity-chart/annual-activity-chart.component';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, TranslateModule, RouterModule, TableComponent, FavGenreChartComponent, AnnualActivityChartComponent],
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
}