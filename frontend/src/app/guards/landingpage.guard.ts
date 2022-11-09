import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { RoutingService } from 'src/app/services/routing.service';

@Injectable({
  providedIn: 'root'
})
export class LandingPageGuard implements CanActivate {

  constructor(private routing: RoutingService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let flag = sessionStorage.getItem("bearerToken");
    if (flag != null) {
      return true;
    } else {
      this.routing.openSignInPage();
      return false;
    }
  }

}
