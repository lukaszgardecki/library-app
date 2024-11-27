import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { UsersPage } from '../../shared/models/users-page';
import { UserDetails, UserDetailsAdmin, UserPreview, UserUpdate, UserUpdateAdmin } from '../../shared/models/user-details';
import { Sort } from '../../shared/models/sort.interface';
import { UserStatsAdmin } from '../../shared/models/users-stats-admin';

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

  getUsersPage(page: number, size: number, sort: Sort, query: string = ""): void {
    let params = this.createParams(page, size, sort, query);
    this.fetchUsersPage(params);
  }

  getUsersStatsAdmin(): Observable<UserStatsAdmin> {
    return this.http.get<UserStatsAdmin>(`${this.baseAdminURL}/stats`, { withCredentials: true });
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

  private createParams(page: number | null, size: number | null, sort: Sort | null, query: string | null): HttpParams {
    let params = new HttpParams();
    if (page !== null) { params = params.set("page", page); }
    if (size !== null) { params = params.set("size", size); } 
    if (query !== null) { params = params.set("q", query); }
    if (sort?.direction) {
        const sortParam = ['firstName', 'lastName'].includes(sort.columnKey) ? `person.${sort.columnKey}` : sort.columnKey;
        const sortValue = `${sortParam},${sort.direction}`;
        params = params.set("sort", sortValue);
    }

    return params;
  }
}
