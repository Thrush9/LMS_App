import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { CompanyService } from 'src/app/services/company.service';
import { CourseService } from 'src/app/services/course.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Course } from 'src/app/models/Course';
import { Company } from 'src/app/models/Company';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  p: number = 1;
  p1: number = 1;
  companiesData: Company[];
  coursesData: Course[];
  totalCompanies: number = 0;
  totalCourses: number = 0;
  totalUsers: number = 5;

  ngAfterViewInit() {
  }

  constructor(private userService: UserService, private companyService: CompanyService, private courseService: CourseService, private snackbar: MatSnackBar) {
    this.companiesData = [];
    this.coursesData = [];
  }

  ngOnInit(): void {
    this.fetchAllUsers();
    this.fetchAllCompanies();
    this.fetchAllCourses();
  }

  fetchAllUsers() {
    this.userService.getAllUserDetails().subscribe(res => {
      this.totalUsers = res.length
    },
      (err) => {
        if (err.status === 400) {
          console.log(err.error);
        } else {
          console.log(err.error.message);
        }
      });
  }

  fetchAllCompanies() {
    this.companyService.getAllCompanyDetails().subscribe(res => {
      this.companiesData = res;
      this.totalCompanies = this.companiesData.length
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
      this.totalCourses = this.coursesData.length
    },
      (err) => {
        if (err.status === 400) {
          console.log(err.error);
        } else {
          console.log(err.error.message);
        }
      });
  }
}
