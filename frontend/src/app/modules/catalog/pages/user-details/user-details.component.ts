import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../core/services/user.service';
import { UserDetailsAdmin, UserUpdateAdmin } from '../../shared/models/user-details';
import { CommonModule, Location } from '@angular/common';
import { Role } from '../../shared/enums/role.enum';
import { Gender } from '../../shared/enums/gender.enum';
import { AccountStatus } from '../../shared/enums/account-status.enum';
import { CardStatus } from '../../shared/enums/card-status.enum';
import { TranslateModule } from '@ngx-translate/core';
import { NullPlaceholderPipe } from "../../../../shared/pipes/null-placeholder.pipe";
import { FormsModule } from '@angular/forms';
import { EnumNamePipe } from "../../../../shared/pipes/enum-name.pipe";
import { FavGenreChartComponent } from "../../components/charts/fav-genre-chart/fav-genre-chart.component";
import { UserActivityChartComponent } from "../../components/charts/user-activity-chart/user-activity-chart.component";
import { LendingService } from '../../core/services/lending.service';
import { TableComponent } from "../../components/table/table.component";
import { LendingsPage } from '../../shared/models/lendings-page';
import { Observable } from 'rxjs';
import { TableUpdateEvent } from '../../shared/models/table-event.interface';

@Component({
  selector: 'app-user-details',
  standalone: true,
  imports: [
    CommonModule, TranslateModule, FormsModule,
    NullPlaceholderPipe, EnumNamePipe,
    FavGenreChartComponent, UserActivityChartComponent,
    TableComponent
],
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.css'
})
export class UserDetailsComponent {
  user: UserDetailsAdmin = new UserDetailsAdmin();
  favGenre: String = '';
  genderList = Object.values(Gender)
  accountStatuses = Object.values(AccountStatus)
  cardStatuses = Object.values(CardStatus)
  roles = Object.values(Role)
  isPersonalInfoEditing = false;
  isAccountInfoEditing = false;
  lendingPage$: Observable<LendingsPage>;

  constructor(
    private userService: UserService,
    private lendingService: LendingService,
    private location: Location,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.userService.getUserDetailsByIdAdmin(id).subscribe({
      next: user => {
        this.user = user;
        if (user.genresStats) {
          this.user.genresStats = new Map<string, number>(Object.entries(user.genresStats));
        }
        this.favGenre = this.findFavouriteGenre();
      }
    });
    this.lendingPage$ = this.lendingService.getCurrentLendingsByUserId(id);
  }

  editPersonalInfo() {
    if (this.isPersonalInfoEditing) {
      this.isPersonalInfoEditing = false
      this.updateUserData()
    } else {
      this.isPersonalInfoEditing = true
    }
  }

  editAccountInfo() {
    if (this.isAccountInfoEditing) {
      this.isAccountInfoEditing = false
      this.updateUserData()
    } else {
      this.isAccountInfoEditing = true
    }
  }

  changeAccountStatus(newStatus: AccountStatus) {
    this.user.status = newStatus
  }

  changeCardStatus(newStatus: CardStatus) {
    this.user.card.status = newStatus
  }

  changeRole(newRole: Role) {
    this.user.role = newRole
  }

  changeGender(newGender: Gender) {
    this.user.gender = newGender
  }

  goBack() {
    this.location.back();
  }

  areAnnualLendingsDataAvailble(): boolean {
    return !this.user.lendingsPerMonth.every(value => value === 0);
  }

  updateTable(event: TableUpdateEvent) {
    // TODO: dodać obsługę wyszukiwania wypożyczeń po tytule książki, id, statusie
    console.log('Aktualizuję tabelę...')
  }

  showDetails(lendingId: number) {
    // TODO: dodać nawigację do szczegółów wypożyczenia
    console.log('Przechodzę do szczegółów wypożyczenia id: ' + lendingId)
    // this.router.navigate([userId], { relativeTo: this.route });
  }

  private updateUserData() {
    let userToUpdate = new UserUpdateAdmin();

    userToUpdate.firstName = this.user.firstName;
    userToUpdate.lastName = this.user.lastName;
    userToUpdate.email = this.user.email;
    userToUpdate.streetAddress = this.user.streetAddress;
    userToUpdate.zipCode = this.user.zipCode;
    userToUpdate.city = this.user.city;
    userToUpdate.state = this.user.state;
    userToUpdate.country = this.user.country;
    userToUpdate.phone = this.user.phoneNumber;
    userToUpdate.gender = this.user.gender;
    userToUpdate.pesel = this.user.pesel;
    userToUpdate.dateOfBirth = this.user.dateOfBirth; // ten format??
    userToUpdate.nationality = this.user.nationality;
    userToUpdate.fathersName = this.user.fathersName;
    userToUpdate.mothersName = this.user.mothersName;
    userToUpdate.accountStatus = this.user.status;
    userToUpdate.cardStatus = this.user.card.status;
    userToUpdate.role = this.user.role;

    this.userService.updateUserByAdmin(this.user.id, userToUpdate).subscribe();
  }

  private findFavouriteGenre(): string {
    return [...this.user.genresStats.entries()]
        .reduce(
          (a, e) => e[1] > a[1] ? e : a,
          ['',0]
        )[0];
  }
}



