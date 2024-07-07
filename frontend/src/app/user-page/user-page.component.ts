import { Component } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrl: './user-page.component.css'
})
export class UserPageComponent {
  isLoggedIn: boolean;
  title = 'Library Management System';

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
    });
  }

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  logout(): void {
    this.authService.logout();
  }

  hasPermissionToWarehouse(): boolean {
    return this.authService.hasUserPermissionToWarehouse();
  }

  onNavigate(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const value = target.value;
    if (value) {
      this.router.navigate([value]);
    }
  }
}
