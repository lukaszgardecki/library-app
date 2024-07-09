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
  name: string = "PROFILE.EDIT.PASSWORD.NAME";
  routerLink: string = "edit/password";
  minPassLength = 8;
  minLowerCaseChars = 1;
  minUpperCaseChars = 1;
  minDigitChars = 1;
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
      this.errorMsg = "PROFILE.EDIT.PASSWORD.ERROR_MSG.NOT_VALID_AND_NOT_MATCH";
    } else if (!isValidPassword) {
      this.errorMsg = "PROFILE.EDIT.PASSWORD.ERROR_MSG.NOT_VALID";
    } else if (!passwordsMatch) {
      this.errorMsg = "PROFILE.EDIT.PASSWORD.ERROR_MSG.NOT_MATCH";
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
      this.errorMsg = 'PROFILE.EDIT.PASSWORD.ERROR_MSG.EMPTY_FIELD';
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
    return this.newPass.length >= this.minPassLength;
  }
  
  checkIsOneLowerCaseLetter() {
    const digitCount = (this.newPass.match(/[a-z]/g) || []).length;
    return digitCount >= this.minLowerCaseChars;
  }

  checkIsOneUpperCaseLetter() {
    const digitCount = (this.newPass.match(/[A-Z]/g) || []).length;
    return digitCount >= this.minUpperCaseChars;
  }

  checkIsOneDigitCharacter() {
    const digitCount = (this.newPass.match(/[0-9]/g) || []).length;
    return digitCount >= this.minDigitChars;
  }
}
