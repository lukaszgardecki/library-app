import { Component, inject } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';
import { AuthenticationService } from '../../../../services/authentication.service';
import { UserService } from '../../../../services/user.service';
import { UserUpdate } from '../../../../models/user-details';
import { TEXT } from '../../../../shared/messages';

@Component({
  selector: 'app-edit-phone-number',
  templateUrl: './edit-phone-number.component.html',
  styleUrl: './edit-phone-number.component.css'
})
export class EditPhoneNumberComponent implements ProfileSetting {
  TEXT = TEXT;
  name: string = TEXT.PROFILE_EDIT_PHONE_NAME;
  routerLink: string = "edit/phone";
  userService = inject(UserService);
  authService = inject(AuthenticationService);

  newPhone: string = "";

  errorMsg: string;
  phoneIsWrong: boolean = false;
  phoneIsChanged: boolean = false;

  checkPhoneNumber() {
    this.phoneIsChanged = false;
    return this.isValidPhoneNumber();
  }

  private isValidPhoneNumber(): boolean {
    const regex = /(?:([+]\d{1,4})[-.\s]?)?(?:[(](\d{1,3})[)][-.\s]?)?(\d{1,4})[-.\s]?(\d{1,4})[-.\s]?(\d{1,9})/;
    return regex.test(this.newPhone);
  }


  onSubmit(): void {
    if (!this.newPhone) {
      this.setError(TEXT.PROFILE_EDIT_PHONE_ERROR_MSG_EMPTY_FIELD);
      return;
    }

    if (!this.checkPhoneNumber()) {
      this.setError(TEXT.PROFILE_EDIT_PHONE_ERROR_MSG_NOT_VALID);
      return;
    }

    this.changePhoneNumber();
    this.newPhone = "";
  }

  private setError(message: string): void {
    this.phoneIsChanged = false;
    this.phoneIsWrong = true;
    this.errorMsg = message;
  }

  private changePhoneNumber() {
    const userId = this.authService.currentUserId;
    const user = new UserUpdate();
    user.phone = this.newPhone;
    this.userService.updateUser(userId, user).subscribe({
      next: () => {
        this.phoneIsWrong = false;
        this.phoneIsChanged = true;
      }
    });
  }
}
