import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-add-company',
  templateUrl: './add-company.component.html',
  styleUrls: ['./add-company.component.css']
})
export class AddCompanyComponent implements OnInit {

  companyForm: FormGroup;
  name = new FormControl('', [Validators.required]);
  type = new FormControl('', [Validators.required]);
  email = new FormControl('', [Validators.required, Validators.email]);
  poc = new FormControl('', [Validators.required]);
  contact = new FormControl('');
  error: string = '';

  constructor(private companyService: CompanyService, private snackbar: MatSnackBar) {
    this.companyForm = new FormGroup({
      name: this.name,
      type: this.type,
      email: this.email,
      poc: this.poc,
      contact: this.contact,
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    let data = this.companyForm.value;
    this.companyService.registerCompany(data).subscribe(
      (res) => {
        let msg1 = `${res.name} Compnay Registration Succesful!`;
        this.snackbar.open(msg1, 'OK', { duration: 5000 });
        this.clearForm();
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
    this.companyForm.reset();
  }
}
