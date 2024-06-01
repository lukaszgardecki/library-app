import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { UserDetails } from '../shared/user-details';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseURL;

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/members`
  }

  getUserDetailsById(id: number): Observable<UserDetails> {
    return this.http.get<UserDetails>(`${this.baseURL}/${id}`, { withCredentials: true });
  }
}
