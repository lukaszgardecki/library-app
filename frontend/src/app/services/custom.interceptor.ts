import { HTTP_INTERCEPTORS, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { BehaviorSubject, Observable, catchError, filter, switchMap, take, throwError } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { Injectable } from '@angular/core';
import { AUTHORIZED_ENDPOINTS } from './config.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(private authService: AuthenticationService){}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.getToken(req);
    const endpointIsAuthorized = AUTHORIZED_ENDPOINTS.some(endpoint => req.url.includes(endpoint));
    if (endpointIsAuthorized && token) {
      req = this.addTokenHeader(req, token);
    }

    return next.handle(req).pipe(
      catchError(error => {
        if (req.url.includes('/refresh-token')) {
          return throwError(() => error);
        }
        if (error instanceof HttpErrorResponse && error.status === 401) {
          return this.handle401Error(req, next);
        }
        return throwError(() => error);
      })
    );
  }

  private getToken(req: HttpRequest<any>) {
    if (req.url.endsWith("/refresh-token")) {
      return this.authService.refreshToken;
    } else {
      return this.authService.accessToken;
    }
  }

  private addTokenHeader(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      return this.authService.refreshTokenRequest().pipe(
        switchMap((response: any) => {
          this.isRefreshing = false;
          this.refreshTokenSubject.next(response.refresh_token);
          return next.handle(this.addTokenHeader(request, response.access_token));
        }),
        catchError((err) => {
          this.isRefreshing = false;
          this.authService.logout();
          return throwError(() => err);
        })
      );
    } 
    
    else {
      return this.refreshTokenSubject.pipe(
        filter(token => token != null),
        take(1),
        switchMap(jwt => next.handle(this.addTokenHeader(request, jwt)))
      );
    }
  }
}

export const AuthInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: AuthInterceptor,
  multi: true
}
