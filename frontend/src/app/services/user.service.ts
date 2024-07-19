import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { UserDetails } from '../models/user-details';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserUpdate } from '../shared/user-update';
import { UsersPage } from '../models/users-page';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseURL;
  private usersPageSubject = new BehaviorSubject<UsersPage>(new UsersPage());
  usersPage$ = this.usersPageSubject.asObservable();

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/members`;
  }

  getUsersPage(params: HttpParams) {
    this.fetchUsersPage(params);
  }

  getUserDetailsById(id: number): Observable<UserDetails> {
    return this.http.get<UserDetails>(`${this.baseURL}/${id}`, { withCredentials: true });
  }

  updateUser(userId: number, user: UserUpdate): Observable<UserDetails> {
    return this.http.patch<UserDetails>(`${this.baseURL}/${userId}`, user, { withCredentials: true });
  }

  private fetchUsersPage(params: HttpParams): void {
    this.http.get<UsersPage>(`${this.baseURL}`, { params: params, withCredentials: true })
      .subscribe({
        next: usersPage => {
          this.usersPageSubject.next(usersPage);
        }
      });
  }
}
