import { Component, inject } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';
import { UserService } from '../../../../services/user.service';
import { AuthenticationService } from '../../../../services/authentication.service';
import { UserUpdate } from '../../../../models/user-details';
import { TEXT } from '../../../../shared/messages';

@Component({
  selector: 'app-edit-email',
  templateUrl: './edit-email.component.html',
  styleUrl: './edit-email.component.css'
})
export class EditEmailComponent implements ProfileSetting {
  TEXT = TEXT;
  name: string = TEXT.PROFILE_EDIT_EMAIL_NAME;
  routerLink: string = "edit/email";
  userService = inject(UserService);
  authService = inject(AuthenticationService);

  newEmail: string = "";

  errorMsg: string;
  emailIsWrong: boolean = false;
  emailIsChanged: boolean = false;

  checkEmail() {
    this.emailIsChanged = false;
    return this.isValidEmail();
  }

  private isValidEmail(): boolean {
    const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regex.test(this.newEmail);
  }


  onSubmit(): void {
    if (!this.newEmail) {
      this.setError(TEXT.PROFILE_EDIT_EMAIL_ERROR_MSG_EMPTY_FIELD);
      return;
    }

    if (!this.checkEmail()) {
      this.setError(TEXT.PROFILE_EDIT_EMAIL_ERROR_MSG_NOT_VALID);
      return;
    }

    this.changeEmail();
    this.newEmail = "";
  }

  private setError(message: string): void {
    this.emailIsChanged = false;
    this.emailIsWrong = true;
    this.errorMsg = message;
  }

  private changeEmail() {
    const userId = this.authService.currentUserId;
    const user = new UserUpdate();
    user.email = this.newEmail;
    this.userService.updateUser(userId, user).subscribe({
      next: () => {
        this.emailIsWrong = false;
        this.emailIsChanged = true;
      }
    });
  }
}
