import { ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from "@angular/router";
import { AuthenticationService } from "./authentication.service";
import { Injectable } from "@angular/core";
import { Observable, map, take } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class RoleGuard implements CanActivate, CanActivateChild {
    
    constructor(private authService: AuthenticationService, private router: Router) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> {
        return this.checkRole(route);
    }

    canActivateChild(
        childRoute: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> {
        return this.checkRole(childRoute);
    }

    private checkRole(route: ActivatedRouteSnapshot): Observable<boolean> {
        const expectedRoles = route.data["expectedRoles"];

        return this.authService.isLoggedIn$.pipe(
            take(1),
            map(isLoggedIn => {
              if (!isLoggedIn) {
                this.router.navigate(['not-authorized']);
                return false;
              }
      
              const userRole = this.authService.currentUserRole;
              if (expectedRoles && expectedRoles.includes(userRole)) {
                return true;
              } else if (!expectedRoles && isLoggedIn) {
                return true;
              } else {
                this.router.navigate(['not-authorized']);
                return false;
              }
            })
          );
      }
}