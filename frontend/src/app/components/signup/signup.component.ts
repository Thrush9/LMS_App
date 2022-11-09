import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { RoutingService } from 'src/app/services/routing.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpForm: FormGroup;
  username = new FormControl('', [Validators.required]);
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required, Validators.minLength(8)]);
  cpassword = new FormControl('', [Validators.required, Validators.minLength(8)]);
  error: string = '';

  constructor(private authService: AuthenticationService, private routing: RoutingService, private snackbar: MatSnackBar) {
    this.signUpForm = new FormGroup({
      username: this.username,
      email: this.email,
      password: this.password,
      cpassword: this.cpassword
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {

    let data = this.signUpForm.value;
    this.authService.registerUser(data).subscribe(
      (res) => {
        let msg1 = 'Registration Succesful, SignIn using credentials';
        this.snackbar.open(msg1, 'OK', { duration: 5000 });
        setTimeout(() => {
          this.routing.openSignInPage();
        }, 5000);
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
    this.signUpForm.reset();
  }

}
