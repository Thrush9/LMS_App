import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Course } from 'src/app/models/Course';

@Component({
  selector: 'app-course-delete',
  templateUrl: './course-delete.component.html',
  styleUrls: ['./course-delete.component.css']
})
export class CourseDeleteComponent implements OnInit {

  course!: Course;

  constructor(private dialogRef: MatDialogRef<CourseDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) data: any) {
    this.course = data;
  }

  ngOnInit(): void {
  }

  onDelete() {
    this.dialogRef.close(this.course);
  }

  close() {
    this.dialogRef.close();
  }

}
