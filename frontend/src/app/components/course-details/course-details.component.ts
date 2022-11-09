import { Component, OnInit } from '@angular/core';
import { CompanyService } from 'src/app/services/company.service';
import { CourseService } from 'src/app/services/course.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Course } from 'src/app/models/Course';
import { Company } from 'src/app/models/Company';
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { CourseEditComponent } from '../course-edit/course-edit.component';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { CourseDeleteComponent } from '../course-delete/course-delete.component';

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.css']
})
export class CourseDetailsComponent implements OnInit {

  p: number = 1;
  companiesData: Company[];
  coursesData: Course[];

  searchForm: FormGroup;
  company = new FormControl('', [Validators.required]);

  constructor(private companyService: CompanyService, private courseService: CourseService, private snackbar: MatSnackBar, private dialog: MatDialog) {
    this.companiesData = [];
    this.coursesData = [];
    this.searchForm = new FormGroup({
      company: this.company
    })
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
    },
      (err) => {
        if (err.status === 400) {
          console.log(err.error);
        } else {
          console.log(err.error.message);
        }
      });
  }

  openEditDialog(data: Course) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { ...data };
    const dialogRef = this.dialog.open(CourseEditComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      data => {
        if (data !== undefined) {
          this.updateCourse(data);
        }
        console.log("Dialog output:", data)
      });
  }

  openDeleteDialog(data: Course) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { ...data };
    const dialogRef = this.dialog.open(CourseDeleteComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      data => {
        if (data !== undefined) {
          this.deleteCourse(data);
        }
        console.log("Dialog output:", data)
      });
  }

  onSubmit() {
    let data = { ...this.searchForm.value };
    this.courseService.getCoursesByCompany(data.company).subscribe(res => {
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


  updateCourse(data: Course) {
    this.courseService.updateCourseDetails(data).subscribe(res => {
      let status = res;
      let msg;
      if (status === null) {
        msg = 'Course Not Updated! Try Again';
      } else {
        msg = 'Course Updated Sucessfully!';
      }
      this.snackbar.open(msg, 'OK', { duration: 3000 });
      this.refreshCourses();
    },
      (err) => {
        if (err.status === 400) {
          console.log(err.error);
        } else {
          console.log(err.error.message);
        }
      });
  }

  deleteCourse(data: Course) {
    this.courseService.deleteCourseDetails(data.name).subscribe(res => {

      let status = res;
      let msg;
      if (!status) {
        msg = 'Course Not Deleted! Try Again';
      } else {
        msg = 'Course Deleted Sucessfully!';
      }
      this.snackbar.open(msg, 'OK', { duration: 3000 });
      this.refreshCourses();
    },
      (err) => {
        if (err.status === 400) {
          console.log(err.error);
        } else {
          console.log(err.error.message);
        }
      });
  }

  refreshCourses() {
    setTimeout(() => {
      if (this.searchForm.value.company !== '') {
        this.onSubmit();
      } else
        this.fetchAllCourses();
    }, 1000);
  }
}
