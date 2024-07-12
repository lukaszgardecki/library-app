import { Component, OnInit } from '@angular/core';
import { TEXT } from '../../../shared/messages';
import { UserService } from '../../../services/user.service';
import { Location } from '@angular/common';
import { UserDetails } from '../../../models/user-details';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.css'
})
export class UserDetailsComponent implements OnInit {
  TEXT = TEXT;
  user: UserDetails = new UserDetails();

  constructor(
    private userService: UserService,
    private location: Location,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
      const id = this.route.snapshot.params['id'];
    this.userService.getUserDetailsById(id).subscribe({
      next: user => this.user = user
    });
  }

  goBack() {
    this.location.back();
  }
}
