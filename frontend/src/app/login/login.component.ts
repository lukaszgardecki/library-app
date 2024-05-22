import { Component } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: [
    './login.component.css',
    './login.component.util.css',
    '../../assets/fonts/iconic/css/material-design-iconic-font.min.css'
  ]
})
export class LoginComponent {
  loginObj: Login;

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {
    this.loginObj = new Login();
  }

  onSubmit() {
    let credentials = this.validateCredentials();
    
    if(credentials) {
      this.authService.authenticate(this.loginObj).subscribe({
        next: () => {
          this.router.navigate(['/userprofile']);
        },
        error: err => {
          console.error(err.error.message);
        }
      });
    }
    this.clearForm();
  }

  private validateCredentials(): boolean {
    return this.loginObj.username.trim().length > 0
        && this.loginObj.password.trim().length > 0;
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
