import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { map, Observable } from 'rxjs';
import { UserDetails, UserDetailsAdmin, UserListPreviewAdmin, UserPreview, UserRegister, UserUpdate, UserUpdateAdmin } from '../../shared/models/user-details';
import { Statistics } from '../../shared/models/users-stats-admin';
import { Page, Pageable } from '../../../../shared/models/page';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseURL;
  private fakeUserURL;
  private baseAdminURL;
  private registerURL;

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) {
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/users`;
    this.fakeUserURL = `${baseURL}/fu`;
    this.registerURL = `${baseURL}/register`;
    this.baseAdminURL = `${baseURL}/admin/users`;
  }

  getUsersPage(query: string = "", pageable: Pageable = new Pageable()): Observable<Page<UserListPreviewAdmin>> {
    let params = this.createParams(query, pageable);
    return this.http.get<Page<UserListPreviewAdmin>>(`${this.baseURL}/list`, { params: params, withCredentials: true });
  }

  getUsersStatsAdmin(): Observable<Statistics> {
    return this.http.get<Statistics>(`${this.baseAdminURL}/stats`, { withCredentials: true }).pipe(
      map(stats => {
        if (stats.favGenres) {
          stats.favGenres = new Map<string, number>(Object.entries(stats.favGenres));
        }
        if (stats.ageGroups) {
          stats.ageGroups = new Map<string, number>(Object.entries(stats.ageGroups));
        }
        if (stats.topCities) {
          stats.topCities = new Map<string, number>(Object.entries(stats.topCities));
        }
        return stats;
      })
    );
  }

  getUserDetailsById(id: number): Observable<UserDetails> {
    return this.http.get<UserDetails>(`${this.baseURL}/${id}/details`, { withCredentials: true });
  }

  getUserDetailsByIdAdmin(id: number): Observable<UserDetailsAdmin> {
    return this.http.get<UserDetailsAdmin>(`${this.baseAdminURL}/${id}`, { withCredentials: true });
  }

  getUserPreviewInfo(id: number): Observable<UserPreview> {
    return this.http.get<UserPreview>(`${this.baseURL}/${id}/preview`, { withCredentials: true });
  }

  createNewUser(user: UserRegister): Observable<void>{
    return this.http.post<void>(this.registerURL, user);
  }

  generateFakeUsers(amount: number) {
    this.http.post(`${this.fakeUserURL}?amount=${amount}`, null).subscribe();
  }

  updateUser(userId: number, user: UserUpdate): Observable<UserDetails> {
    return this.http.patch<UserDetails>(`${this.baseURL}/${userId}`, user, { withCredentials: true });
  }

  updateUserByAdmin(userId: number, user: UserUpdateAdmin): Observable<UserDetails> {
    return this.http.patch<UserDetails>(`${this.baseAdminURL}/${userId}`, user, { withCredentials: true });
  }

  private createParams(query: string | null, pageable: Pageable): HttpParams {
    let params = new HttpParams();
    const page = pageable.page;
    const size = pageable.size;
    const sort = pageable.sort;
    if (page !== null) { params = params.set("page", page); }
    if (size !== null) { params = params.set("size", size); }
    if (query !== null) { params = params.set("q", query); }
    if (sort?.direction) {
        const sortParam = ['firstName', 'lastName'].includes(sort.columnKey) ? `${sort.columnKey}` : sort.columnKey;
        const sortValue = `${sortParam},${sort.direction}`;
        params = params.set("sort", sortValue);
    }

    return params;
  }
}
