import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateChild, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { first, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardComponent implements CanActivate, CanActivateChild {
  constructor(private authService: AuthenticationService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    const endpointSecured = route.data["expectedRoles"];
    const userRole = this.authService.currentUserRole;

    return this.authService.isLoggedIn$.pipe(first(), map(isLoggedIn => {
      if (endpointSecured && endpointSecured.includes(userRole)) {
        return true;
      } else if (!endpointSecured && isLoggedIn) {
        return true;
      } else {
        this.router.navigate(['library-app/login']);
        return false;
      }
    }));
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    return this.canActivate(childRoute, state);
  }
}
