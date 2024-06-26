import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { ActionsPage } from '../models/actions-page';
import { Observable } from 'rxjs';
import { Page } from '../shared/page';
import { Params } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ActionService {
  private baseURL;

  constructor(
    private http: HttpClient,
    configService: ConfigService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/actions`
  }

  getActionsByUserId(id: number, pageNum: number, sort: string, type: string): Observable<ActionsPage> {
    let params: Params = { memberId: id };
    params["page"] = pageNum;
    params["sort"] = `created_at,${sort}`;
    if (type != "ALL") params["type"] = type;
    return this.http.get<ActionsPage>(this.baseURL, { params: params , withCredentials: true });
  }
}
