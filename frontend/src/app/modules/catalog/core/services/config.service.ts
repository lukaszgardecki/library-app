import { Injectable } from '@angular/core';
import { environment } from '../../../../../environments/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private baseUrl: string = environment.apiUrl;

  constructor(
    private http: HttpClient
  ) {}

  getApiUrl(): string {
    return this.baseUrl;
  }

  getAppVersion(): Observable<string> {
    return this.http.get("http://localhost:8080/version",  { responseType: 'text' });
  }
}

export const AUTHORIZED_ENDPOINTS = [
  "/auth/refresh",
  "/auth/logout",
  "/stats",
  "/users",
  "/loans",
  "/book-requests",
  "/book-items",
  "/activities",
  "/notifications",
  "/warehouse",
  "/admin"
];