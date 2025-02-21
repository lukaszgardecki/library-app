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
    return this.http.get("http://localhost:8080/api/version",  { responseType: 'text' });
  }
}

export const AUTHORIZED_ENDPOINTS = [
  "/refresh-token",
  "/users",
  "/loans",
  "/book-requests",
  "/authenticate/logout",
  "/activities",
  "/notifications",
  "/warehouse"
];