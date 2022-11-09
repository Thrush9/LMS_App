import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;


  isAdmin: boolean = true;
  isUser: boolean = false;
  isAdminDashboard: boolean = true;
  isUserDashboard: boolean = false;
  username: string = '';
  email: string = '';
  role: String = '';

  constructor(private observer: BreakpointObserver, private router: Router) { }

  ngOnInit(): void {
    let details: any = sessionStorage.getItem('userDetails');
    let userDetails = JSON.parse(details);
    this.username = userDetails.username;
    this.email = userDetails.email;
    this.role = userDetails.role[0];
    this.checkRoleAndPath();
  }

  ngAfterViewInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((res) => {
      if (res.matches) {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      } else {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
    });
  }

  checkRoleAndPath() {
    if (this.role === 'ROLE_ADMIN') {
      return true;
    }

    return false;
  }

}
