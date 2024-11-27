import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../core/services/authentication.service';
import { FormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { CommonModule } from '@angular/common';
import { InputHasValDirective } from '../../../../shared/directives/input-has-val.directive';
import { TranslationService } from '../../../../shared/services/translation.service';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, TranslateModule, FormsModule, InputHasValDirective],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginObj: Login;
  showEmailValidationMessage: boolean = false;
  showPasswordValidationMessage: boolean = false;

  constructor(
    private authService: AuthenticationService,
    private traslate: TranslationService,
    private router: Router
  ) {
    this.loginObj = new Login();
  }

  onSubmit() {
    let credentials = this.validateCredentials();
    
    if(credentials) {
      this.authService.authenticate(this.loginObj).subscribe({
        next: () => {
          this.router.navigate(['library-app/dashboard']);
        },
        error: err => {
          console.log("Błędne hasło lub email");
        }
      });
    }
    this.clearForm();
  }

  private validateCredentials(): boolean {
    let isEmailValid = this.validateEmail();
    let isPasswordValid = this.validatePassword();
    return isEmailValid && isPasswordValid;
  }


  private validateEmail(): boolean {
    const emailRegex = /^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/;
    const isEmailValid = emailRegex.test(this.loginObj.username);
    if(!isEmailValid) {
      this.showEmailValidationMessage = true;
      return false;
    }
    return true;
  }

  private validatePassword(): boolean {
    if(this.loginObj.password.trim().length === 0) {
      this.showPasswordValidationMessage = true;
      return false;
    }
    return true;
  }
  

  private clearForm() {
    this.loginObj = new Login();
  }
}

export class Login {
  username: string;
  password: string;

  constructor() {
    this.username = "";
    this.password = "";
  }
}
