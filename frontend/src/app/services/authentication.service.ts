import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Login } from '../user-page/login/login.component';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { ConfigService } from './config.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private baseURL;
  private readonly ACCESS_TOKEN_NAME = "access_token";
  private readonly REFRESH_TOKEN_NAME = "refresh_token";
  private _isLoggedInSubject = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this._isLoggedInSubject.asObservable();
  currentUserId: number = -1;
  currentUserRole: string = "";
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
    private configService: ConfigService
  ) {
    this.baseURL = configService.getApiUrl();
    this.accessTokenSubject = new BehaviorSubject<string | null>(this.getFromStorage(this.ACCESS_TOKEN_NAME));
    this.refreshTokenSubject = new BehaviorSubject<string | null>(this.getFromStorage(this.REFRESH_TOKEN_NAME));

    let tokensAreValid = this.isValidToken(this.refreshToken) && this.isValidToken(this.accessToken);
    this._isLoggedInSubject.next(tokensAreValid);
    this.currentUserId = this.findUserIdInToken(this.refreshToken);
    this.currentUserRole = this.findUserRoleInToken(this.refreshToken);
  }

  authenticate(login: Login): Observable<any> {
    return this.http.post<Token>(`${this.baseURL}/authenticate`, login, { withCredentials: true }).pipe(
      tap(response => {
        this.currentUserId = this.findUserIdInToken(response.refresh_token);
        this.currentUserRole = this.findUserRoleInToken(response.refresh_token);
        sessionStorage.setItem(this.ACCESS_TOKEN_NAME, response.access_token);
        sessionStorage.setItem(this.REFRESH_TOKEN_NAME, response.refresh_token);
        this._isLoggedInSubject.next(true);
        this.accessTokenSubject.next(response.access_token);
        this.refreshTokenSubject.next(response.refresh_token);
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
        this.currentUserId = this.findUserIdInToken(response.refresh_token);
        this.currentUserRole = this.findUserRoleInToken(response.refresh_token);
        sessionStorage.setItem(this.ACCESS_TOKEN_NAME, response.access_token);
        sessionStorage.setItem(this.REFRESH_TOKEN_NAME, response.refresh_token);
        this._isLoggedInSubject.next(true);
        this.accessTokenSubject.next(response.access_token);
        this.refreshTokenSubject.next(response.refresh_token);
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
        this.currentUserId = -1;
        this.currentUserRole = "";
        sessionStorage.removeItem(this.ACCESS_TOKEN_NAME);
        sessionStorage.removeItem(this.REFRESH_TOKEN_NAME);
        this._isLoggedInSubject.next(false);
        this.accessTokenSubject.next(null);
        this.refreshTokenSubject.next(null);
        this.stopRefreshTokenTimer();
      }
    });
  }

  hasUserPermissionToWarehouse(): boolean {
    return ["WAREHOUSE", "ADMIN"].includes(this.currentUserRole);
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

  private getFromStorage(tokenName: string) {
    if (typeof window !== 'undefined') {
      return sessionStorage.getItem(tokenName);
    }
    return null;
  }
}

class Token {
  access_token: string;
  refresh_token: string;
}
