import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Login } from '../user-page/login/login.component';
import { BehaviorSubject, Observable, catchError, combineLatest, map, tap, throwError } from 'rxjs';
import { ConfigService } from './config.service';
import { Router } from '@angular/router';
import { UserService } from './user.service';
import { StorageService } from './storage.service';
import { BookItem } from '../models/book-item';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private baseURL;
  private _isLoggedInSubject = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this._isLoggedInSubject.asObservable();
  currentUserId: number = -1;
  currentUserRole: string = "";
  private loanedItemsIdsSubject: BehaviorSubject<number[]> = new BehaviorSubject<number[]>([]);
  private reservedItemsIdsSubject: BehaviorSubject<number[]> = new BehaviorSubject<number[]>([]);
  private accessTokenSubject: BehaviorSubject<string | null>;
  private refreshTokenSubject: BehaviorSubject<string | null>;
  private refreshTokenTimeout: any;

  public get accessToken(): string | null {
    return this.accessTokenSubject.value;
  }

  public get refreshToken(): string | null {
    return this.refreshTokenSubject.value;
  }

  constructor(
    private http: HttpClient,
    private configService: ConfigService,
    private storageService: StorageService,
    private userService: UserService,
    private router: Router
  ) {
    this.baseURL = configService.getApiUrl();
    this.accessTokenSubject = new BehaviorSubject<string | null>(this.storageService.getAccessToken());
    this.refreshTokenSubject = new BehaviorSubject<string | null>(this.storageService.getRefreshToken());
    this.loanedItemsIdsSubject.next(this.storageService.getLoanedIds());
    this.reservedItemsIdsSubject.next(this.storageService.getReservedIds());

    let tokensAreValid = this.isValidToken(this.refreshToken) && this.isValidToken(this.accessToken);
    if (tokensAreValid) {
      this._isLoggedInSubject.next(true);
      this.currentUserId = this.findUserIdInToken(this.refreshToken);
      this.currentUserRole = this.findUserRoleInToken(this.refreshToken);
    } else {
      this.clearUserData();
    }
  }

  authenticate(login: Login): Observable<any> {
    return this.http.post<Token>(`${this.baseURL}/authenticate`, login, { withCredentials: true }).pipe(
      tap(response => {
        this.initializeUserData(response);
        this.startRefreshTokenTimer();
      })
    );
  }

  refreshTokenRequest(): Observable<any> {
    if (!this.refreshToken) {
      return throwError(() => 'No refresh token available');
    }

    return this.http.post<Token>(`${this.baseURL}/refresh-token`, {}, { withCredentials: true }).pipe(
      tap(response => {
        this.initializeUserData(response)
        this.startRefreshTokenTimer();
      }),
      catchError(error => {
        this.logout();
        return throwError(() => error);
      })
    );
  }

  logout() {
    this.http.post(`${this.baseURL}/authenticate/logout`, {}).subscribe({
      next: () => {
        this.clearUserData();
        this.stopRefreshTokenTimer();
        this.router.navigate(['login']);
      }
    });
  }

  hasUserPermissionToWarehouse(): boolean {
    return ["WAREHOUSE", "ADMIN"].includes(this.currentUserRole);
  }

  hasUserBorrowed(bookItem: BookItem): Observable<boolean> {
    return this.loanedItemsIdsSubject.asObservable().pipe(
      map(ids => ids.includes(bookItem.id))
    );
  }

  hasUserReserved(bookItem: BookItem): Observable<boolean> {
    return combineLatest([
      this.reservedItemsIdsSubject.asObservable(),
      this.hasUserBorrowed(bookItem)
    ]).pipe(
      map(([reservedIds, hasBorrowed]) => !hasBorrowed && reservedIds.includes(bookItem.id))
    );
  }

  addToReservedItemsIds(id: number) {
    const ids = this.reservedItemsIdsSubject.value;
    if (!ids.includes(id)) {
      this.reservedItemsIdsSubject.next([...ids, id]);
      this.storageService.saveReservedIds([...ids, id]);
    }
  }

  private initializeUserData(response: Token) {
    this.currentUserId = this.findUserIdInToken(response.refresh_token);
        this.currentUserRole = this.findUserRoleInToken(response.refresh_token);
        this.storageService.saveTokens(response.access_token, response.refresh_token);
        this._isLoggedInSubject.next(true);
        this.accessTokenSubject.next(response.access_token);
        this.refreshTokenSubject.next(response.refresh_token);
        this.userService.getUserDetailsById(this.currentUserId).subscribe({
          next: user => {
            this.loanedItemsIdsSubject.next(user.loanedItemsIds);
            this.reservedItemsIdsSubject.next(user.reservedItemsIds);
            this.storageService.saveLoanedIds(user.loanedItemsIds);
            this.storageService.saveReservedIds(user.reservedItemsIds);
          }
        });
  }

  private clearUserData() {
    this.currentUserId = -1;
    this.currentUserRole = "";
    this.loanedItemsIdsSubject.next([]);
    this.reservedItemsIdsSubject.next([]);
    this.storageService.clearStorage();
    this._isLoggedInSubject.next(false);
    this.accessTokenSubject.next(null);
    this.refreshTokenSubject.next(null);
  }

  private startRefreshTokenTimer() {
    const token = this.accessToken;
    if (token) {
      const jwtToken = this.getTokenPayload(token);
      const expires = new Date(jwtToken.exp * 1000);
      const timeout = expires.getTime() - Date.now() - 60000; // 1 minute before expiry
      this.refreshTokenTimeout = setTimeout(() => this.refreshTokenRequest().subscribe(), timeout);
    }
  }

  private findUserIdInToken(token: string | null): number {
    if (!token) return -1;
    return this.getTokenPayload(token).userId;
  }

  private findUserRoleInToken(token: string | null): string {
    if (!token) return "";
    return this.getTokenPayload(token).userRole;
  }

  private isValidToken(token: string | null): boolean {
    if (token) {
      const jwtToken = this.getTokenPayload(token);
      const expires = new Date(jwtToken.exp * 1000);
      return expires.getTime() > Date.now();
    }
    return false;
  }

  private stopRefreshTokenTimer() {
    clearTimeout(this.refreshTokenTimeout);
  }

  private getTokenPayload(token: string) {
    const payloadBase64 = token.split('.')[1];
    const payloadJson = atob(payloadBase64);
    return JSON.parse(payloadJson);
  }
}

class Token {
  access_token: string;
  refresh_token: string;
}
