import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { UserDetails, UserDetailsAdmin, UserPreview, UserUpdateAdmin } from '../models/user-details';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserUpdate } from '../models/user-details';
import { UsersPage } from '../models/users-page';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseURL;
  private baseAdminURL;
  private usersPageSubject = new BehaviorSubject<UsersPage>(new UsersPage());
  usersPage$ = this.usersPageSubject.asObservable();

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/members`;
    this.baseAdminURL = `${baseURL}/admin/members`;
  }

  getUsersPage(params: HttpParams) {
    this.fetchUsersPage(params);
  }

  getUserDetailsById(id: number): Observable<UserDetails> {
    return this.http.get<UserDetails>(`${this.baseURL}/${id}`, { withCredentials: true });
  }

  getUserDetailsByIdAdmin(id: number): Observable<UserDetailsAdmin> {
    return this.http.get<UserDetailsAdmin>(`${this.baseAdminURL}/${id}`, { withCredentials: true });
  }

  getUserPreviewInfo(id: number): Observable<UserPreview> {
    return this.http.get<UserPreview>(`${this.baseURL}/${id}/preview`, { withCredentials: true });
  }

  updateUser(userId: number, user: UserUpdate): Observable<UserDetails> {
    return this.http.patch<UserDetails>(`${this.baseURL}/${userId}`, user, { withCredentials: true });
  }

  updateUserByAdmin(userId: number, user: UserUpdateAdmin): Observable<UserDetails> {
    return this.http.patch<UserDetails>(`${this.baseAdminURL}/${userId}`, user, { withCredentials: true });
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
