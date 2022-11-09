import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Company } from 'src/app/models/Company';
import { CompanyService } from 'src/app/services/company.service';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {

  companiesData: Company[];
  courseForm: FormGroup;
  companyId = new FormControl('', [Validators.required]);
  courseId = new FormControl('', [Validators.required]);
  name = new FormControl('', [Validators.required]);
  technology = new FormControl('', [Validators.required]);
  description = new FormControl('', [Validators.required]);
  duration = new FormControl('', [Validators.required]);
  url = new FormControl('', [Validators.required]);
  error: string = '';

  constructor(private companyService: CompanyService, private courseService: CourseService, private snackbar: MatSnackBar) {
    this.companiesData = [];
    this.courseForm = new FormGroup({
      companyId: this.companyId,
      courseId: this.courseId,
      name: this.name,
      technology: this.technology,
      description: this.description,
      duration: this.duration,
      url: this.url,
    });
  }

  ngOnInit(): void {
    this.fetchAllCompanies();
  }

  fetchAllCompanies() {
    this.companyService.getAllCompanyDetails().subscribe(res => {
      this.companiesData = res;
    });
  }

  onSubmit() {
    let data = this.courseForm.value;
    this.courseService.registerCourse(data).subscribe(
      (res) => {
        let msg1 = `${res.name} Registration Succesful!`;
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
    this.courseForm.reset();
  }

}
