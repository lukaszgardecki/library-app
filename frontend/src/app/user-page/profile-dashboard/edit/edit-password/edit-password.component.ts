import { Component, inject } from '@angular/core';
import { ProfileSetting } from '../../profile-dashboard.component';
import { UserService } from '../../../../services/user.service';
import { AuthenticationService } from '../../../../services/authentication.service';
import { UserUpdate } from '../../../../shared/user-update';

@Component({
  selector: 'app-edit-password',
  templateUrl: './edit-password.component.html',
  styleUrl: './edit-password.component.css'
})
export class EditPasswordComponent implements ProfileSetting {
  name: string = "Password";
  routerLink: string = "edit/password";
  userService = inject(UserService);
  authService = inject(AuthenticationService);

  newPass: string = "";
  newPassRepeat: string = "";

  errorMsg: string;
  passIsWrong: boolean = false;
  passIsChanged: boolean = false;

  checkPasswords() {
    this.passIsChanged = false;
    const isValidPassword = this.isPasswordValid();
    const passwordsMatch = this.newPass === this.newPassRepeat;
   
    if (!isValidPassword && !passwordsMatch) {
      this.errorMsg = "Password must be at least 8 characters long and contain at least one lowercase letter, one uppercase letter, and one digit. Passwords do not match.";
    } else if (!isValidPassword) {
      this.errorMsg = "Password must be at least 8 characters long and contain at least one lowercase letter, one uppercase letter, and one digit.";
    } else if (!passwordsMatch) {
      this.errorMsg = "Passwords do not match.";
    } else {
      this.errorMsg = "";
    }

    this.passIsWrong = !!this.errorMsg;
    return isValidPassword && passwordsMatch;
  }

  onSubmit(): void {
    if (!this.newPass || !this.newPassRepeat || !this.checkPasswords()) {
      this.passIsChanged = false;
      this.passIsWrong = true;
      this.errorMsg = 'Please fill in all fields and make sure the passwords match and meet the criteria.';
      return;
    }
    this.changePassword();
    this.newPass="";
    this.newPassRepeat="";
     
  }

  private changePassword() {
    const userId = this.authService.currentUserId;
    const user = new UserUpdate();
    user.password = this.newPass;
    this.userService.updateUser(userId, user).subscribe({
      next: () => {
        this.passIsWrong = false;
        this.passIsChanged = true;
      }
    });
  }

  private isPasswordValid() {
    return this.checkPasswordLength()
        && this.checkIsOneLowerCaseLetter()
        && this.checkIsOneUpperCaseLetter()
        && this.checkIsOneDigitCharacter();
  }

  checkPasswordLength() {
    return this.newPass.length >= 8;
  }
  
  checkIsOneLowerCaseLetter() {
    return /[a-z]/.test(this.newPass);
  }

  checkIsOneUpperCaseLetter() {
    return /[A-Z]/.test(this.newPass);
  }

  checkIsOneDigitCharacter() {
    return /[0-9]/.test(this.newPass);
  }
}
