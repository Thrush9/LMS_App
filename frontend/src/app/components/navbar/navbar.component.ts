import { Component, Input, OnInit } from '@angular/core';
import { Session } from 'src/app/models/Session';
import { RoutingService } from 'src/app/services/routing.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private routing: RoutingService) { }

  public isLoggedIn = false;
  @Input()
  session: Session = new Session();

  ngOnInit(): void {
    this.session = new Session();
  }

  navigateToHome() {
    this.routing.openHomePage();
  }

  navigateToSignIn() {
    this.routing.openSignInPage();
  }

  navigateToSignUp() {
    this.routing.openSignUpPage();
  }
  signOut() {
    sessionStorage.clear();
    this.routing.openHomePage();
    location.reload();
  }

}
