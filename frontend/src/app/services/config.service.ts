import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private baseUrl: string = environment.apiUrl;

  getApiUrl(): string {
    return this.baseUrl;
  }
}

export const AUTHORIZED_ENDPOINTS = [
  "/refresh-token",
];
