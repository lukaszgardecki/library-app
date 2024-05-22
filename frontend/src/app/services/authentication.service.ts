import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Login } from '../login/login.component';
import { BehaviorSubject, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private baseURL = "http://localhost:8080/api/v1/authenticate";
  private readonly TOKEN_NAME = "libraryToken";
  private _isLoggedIn$ = new BehaviorSubject<boolean>(false);

  get token() {
    if (typeof window !== 'undefined') {
      return localStorage.getItem(this.TOKEN_NAME);
    }
    return null;
  }

  get isLoggedIn$() {
    return this._isLoggedIn$.asObservable();
  }

  constructor(private http: HttpClient) { 
    this._isLoggedIn$.next(!!this.token);
  }

  authenticate(login: Login) {
    return this.http.post<Token>(this.baseURL, login).pipe(
      tap(token => {
        localStorage.setItem(this.TOKEN_NAME, token.token);
        this._isLoggedIn$.next(true);
      })
    );
  }

  logout() {
    this.http.post(`${this.baseURL}/logout`, undefined).subscribe({
      next: () => {
        localStorage.removeItem(this.TOKEN_NAME);
        this._isLoggedIn$.next(false);
      }
    });
  }
}

class Token {
  token: string;
}