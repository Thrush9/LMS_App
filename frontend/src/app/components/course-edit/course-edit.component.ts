import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: 'app-course-edit',
  templateUrl: './course-edit.component.html',
  styleUrls: ['./course-edit.component.css']
})
export class CourseEditComponent implements OnInit {

  editCourseForm: FormGroup;
  id = new FormControl('', [Validators.required]);
  companyId = new FormControl('', [Validators.required]);
  courseId = new FormControl('', [Validators.required]);
  name = new FormControl('', [Validators.required]);
  technology = new FormControl('', [Validators.required]);
  description = new FormControl('', [Validators.required]);
  duration = new FormControl('', [Validators.required]);
  url = new FormControl('', [Validators.required]);
  error: string = '';

  constructor(
    private dialogRef: MatDialogRef<CourseEditComponent>,
    @Inject(MAT_DIALOG_DATA) data: any) {
    this.editCourseForm = new FormGroup({
      companyId: new FormControl(data.companyId, [Validators.required]),
      id: new FormControl(data.id, [Validators.required]),
      courseId: new FormControl(data.courseId, [Validators.required]),
      name: new FormControl(data.name, [Validators.required]),
      technology: new FormControl(data.technology, [Validators.required]),
      description: new FormControl(data.description, [Validators.required]),
      duration: new FormControl(data.duration, [Validators.required]),
      url: new FormControl(data.url, [Validators.required]),
    });
  }

  ngOnInit() {

  }

  onSave() {
    this.dialogRef.close(this.editCourseForm.value);
  }

  close() {
    this.dialogRef.close();
  }
}
