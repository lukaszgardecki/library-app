import { HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { inject } from '@angular/core';
import { AUTHORIZED_ENDPOINTS } from '../services/config.service';
import { StorageService } from '../services/storage.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const storageService = inject(StorageService)

  const token = req.url.endsWith("/refresh-token") 
    ? storageService.getRefreshToken() 
    : storageService.getAccessToken();
  const endpointIsAuthorized = AUTHORIZED_ENDPOINTS.some(endpoint => req.url.includes(endpoint));
  if (endpointIsAuthorized && token) {
    req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
  }

  req = req.clone({ setHeaders: {'Accept-Language': `${storageService.getUserLanguage()}`} });

  return next(req).pipe(
    catchError(error => {
      if (req.url.includes('/refresh-token')) {
        return throwError(() => error);
      }
      return throwError(() => error);
    })
  );
}