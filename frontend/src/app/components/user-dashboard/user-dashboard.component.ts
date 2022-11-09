import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { CompanyService } from 'src/app/services/company.service';
import { CourseService } from 'src/app/services/course.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Course } from 'src/app/models/Course';
import { Company } from 'src/app/models/Company';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {

  searchForm: FormGroup;
  technology = new FormControl('');
  fromDuration = new FormControl('', [Validators.required]);
  toDuration = new FormControl('', [Validators.required]);

  p: number = 1;
  coursesData: Course[];
  companiesData: Company[];
  technologyData: any[] = [];

  constructor(private companyService: CompanyService, private courseService: CourseService, private snackbar: MatSnackBar) {
    this.coursesData = [];
    this.companiesData = [];
    this.searchForm = new FormGroup({
      technology: this.technology,
      fromDuration: this.fromDuration,
      toDuration: this.toDuration,
    });

  }

  ngOnInit(): void {
    this.fetchAllCompanies();
    this.fetchAllCourses();
  }

  fetchAllCompanies() {
    this.companyService.getAllCompanyDetails().subscribe(res => {
      this.companiesData = res;
    },
      (err) => {
        if (err.status === 400) {
          console.log(err.error);
        } else {
          console.log(err.error.message);
        }
      });
  }

  fetchAllCourses() {
    this.courseService.getAllCourseDetails().subscribe(res => {
      this.coursesData = res;
      this.filterTechnologies();
    },
      (err) => {
        if (err.status === 400) {
          console.log(err.error);
        } else {
          console.log(err.error.message);
        }
      });
  }

  filterTechnologies() {
    this.coursesData.forEach(course => {
      if (!this.technologyData.includes(course.technology))
        this.technologyData.push(course.technology);
    })
  }

  onSubmit() {
    let technology = (this.searchForm.value.technology === '' || this.searchForm.value.technology === null) ? 'none' : this.searchForm.value.technology;
    let fromDuration = this.searchForm.value.fromDuration !== '' ? this.searchForm.value.fromDuration : 0;
    let toDuration = this.searchForm.value.toDuration !== '' ? this.searchForm.value.toDuration : 1000;
    this.courseService.searchCourses(technology, fromDuration, toDuration).subscribe(res => {
      this.coursesData = res;
      let msg;
      if (this.coursesData.length === 0) {
        msg = 'No Results found! Try other options';
      } else {
        msg = 'Results Fetched Sucessfully!';
      }
      this.snackbar.open(msg, 'OK', { duration: 5000 });
    },
      (err) => {
        if (err.status === 400) {
          console.log(err.error);
        } else {
          console.log(err.error.message);
        }
      });
  }

  reset() {
    this.searchForm.reset();
    this.technologyData = [];
    this.fetchAllCourses();
  }


}
