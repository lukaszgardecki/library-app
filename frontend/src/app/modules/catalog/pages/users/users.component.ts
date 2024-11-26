import { Component } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TableComponent } from "../../components/table/table.component";
import { TableUpdateEvent } from '../../shared/models/table-event.interface';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, TranslateModule, RouterModule, TableComponent],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent {
  usersPage$ = this.userService.usersPage$;

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