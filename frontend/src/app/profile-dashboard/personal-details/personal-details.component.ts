import { Component, Inject, OnInit, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { UserService } from '../../services/user.service';
import { UserDetails } from '../../models/user-details';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-personal-details',
  templateUrl: './personal-details.component.html',
  styleUrl: './personal-details.component.css'
})
export class PersonalDetailsComponent implements ProfileSetting, OnInit {
  name: string = "Personal details";
  routerLink: string = "details";
  user?: UserDetails;
  private userService = inject(UserService);
  private authService = inject(AuthenticationService);

  ngOnInit(): void {
    const userId = this.authService.currentUserId;
    this.userService.getUserDetailsById(userId).subscribe({
      next: user => this.user = user
    })
  }
}
