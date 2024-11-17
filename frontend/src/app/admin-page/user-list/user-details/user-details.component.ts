import { Component, inject, OnInit } from '@angular/core';
import { TEXT } from '../../../shared/messages';
import { UserService } from '../../../services/user.service';
import { Location } from '@angular/common';
import { AccountStatus, Gender, Role, UserDetailsAdmin, UserUpdateAdmin } from '../../../models/user-details';
import { ActivatedRoute } from '@angular/router';
import { CardStatus } from '../../../models/library-card';
import { Lending } from '../../../models/lending';
import { LendingService } from '../../../services/lending.service';
import { PdfService } from '../../../services/pdf.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.css'
})
export class UserDetailsComponent implements OnInit {
  TEXT = TEXT;
  user: UserDetailsAdmin = new UserDetailsAdmin();
  favGenre: String;
  genderList = Object.values(Gender)
  accountStatuses = Object.values(AccountStatus)
  cardStatuses = Object.values(CardStatus)
  roles = Object.values(Role)
  isPersonalInfoEditing = false;
  isAccountInfoEditing = false;
  lendings: Array<Lending>;
  lendingService = inject(LendingService);
  pdfService = inject(PdfService);

  constructor(
    private userService: UserService,
    private location: Location,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.userService.getUserDetailsByIdAdmin(id).subscribe({
      next: user => {
        this.user = user
        if (user.genresStats) {
          this.user.genresStats = new Map<string, number>(Object.entries(user.genresStats));
        }
        this.favGenre = this.findFavouriteGenre();
      }
    });
    this.lendingService.getCurrentLendingsByUserId(id).subscribe({
      next: lendingPage => {
        if (lendingPage._embedded) {
          this.lendings = lendingPage._embedded.lendingDtoList;
        }
      }
    });
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

  saveAsPDF(): void {
    const data = document.getElementById('table');
    this.pdfService.saveAsPDF(data, "borrowed-books");
  }

  private findFavouriteGenre(): string {
    return [...this.user.genresStats.entries()].reduce((a, e) => e[1] > a[1] ? e : a)[0];
  }
}
