import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RoutingService {

  constructor(private myRouter: Router) { }

  openHomePage() {
    this.myRouter.navigate(['home']);
  }

  openSignInPage() {
    this.myRouter.navigate(['signin']);
  }

  openSignUpPage() {
    this.myRouter.navigate(['signup']);
  }

  openDashboardPage() {
    this.myRouter.navigate(['dashboard']);
  }
}
