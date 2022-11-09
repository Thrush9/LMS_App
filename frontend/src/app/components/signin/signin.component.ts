import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { RoutingService } from 'src/app/services/routing.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  signInForm: FormGroup;
  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required, Validators.minLength(5)]);
  error: string = '';

  constructor(private authService: AuthenticationService, private routing: RoutingService, private snackbar: MatSnackBar, private router: Router) {
    this.signInForm = new FormGroup({
      username: this.username,
      password: this.password
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    let data = this.signInForm.value;
    this.authService.validateUser(data).subscribe(
      (res: Record<string, any>) => {
        let token = res["accessToken"];
        let userRole = res["roles"];
        let userEmail = res["email"];
        this.authService.setBearertoken(token);
        this.authService.setUserRole(userRole[0]);
        this.authService.setUserEmail(userEmail);
        this.authService.setUserDetails(this.signInForm.value.username, userEmail, userRole);
        let msg = 'SignIn Succesful';
        this.snackbar.open(msg, 'OK', { duration: 5000 });
        if (userRole[0] === 'ROLE_ADMIN') {
          this.router.navigate(['/dashboard/admin']);
        } else {
          this.router.navigate(['/dashboard/user'])
        }
      },
      (err) => {
        if (err.status === 400) {
          this.error = err.error;
        } else {
          this.error = err.error.message;
        }
      }
    );
  }

  clearForm() {
    this.signInForm.reset();
  }

}
