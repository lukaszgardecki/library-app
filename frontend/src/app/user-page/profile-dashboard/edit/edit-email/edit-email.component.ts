import { Component, inject } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';
import { UserService } from '../../../../services/user.service';
import { AuthenticationService } from '../../../../services/authentication.service';
import { UserUpdate } from '../../../../shared/user-update';

@Component({
  selector: 'app-edit-email',
  templateUrl: './edit-email.component.html',
  styleUrl: './edit-email.component.css'
})
export class EditEmailComponent implements ProfileSetting {
  name: string = "PROFILE.EDIT.EMAIL.NAME"
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
      this.setError('PROFILE.EDIT.EMAIL.ERROR_MSG.EMPTY_FIELD');
      return;
    }
  
    if (!this.checkEmail()) {
      this.setError('PROFILE.EDIT.EMAIL.ERROR_MSG.NOT_VALID');
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
