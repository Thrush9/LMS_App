import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { Course } from '../models/Course';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  //courseServiceAPI: string = 'http://localhost:8765/CourseService';
  courseServiceAPI: string = 'http://courseservice-env.eba-s2dq6d2t.ap-south-1.elasticbeanstalk.com';

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAllCourseDetails() {
    let token = this.authService.getBearertoken();
    return this.http.get<Course[]>(this.courseServiceAPI + '/api/v1.0/lms/courses/getall',
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
      });
  }

  registerCourse(data: Course) {
    let token = this.authService.getBearertoken();
    return this.http.post<Course>(this.courseServiceAPI + `/api/v1.0/lms/courses/add/${data.name}`, data,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
      });
  }

  getCoursesByCompany(data: string) {
    let token = this.authService.getBearertoken();
    return this.http.get<Course[]>(this.courseServiceAPI + `/api/v1.0/lms/courses/${data}`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
      });
  }

  getCoursesByTechnology(data: string) {
    let token = this.authService.getBearertoken();
    return this.http.get<Course[]>(this.courseServiceAPI + `/api/v1.0/lms/courses/info/${data}`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
      });
  }

  updateCourseDetails(data: Course) {
    let token = this.authService.getBearertoken();
    return this.http.put<Course>(this.courseServiceAPI + `/api/v1.0/lms/courses/update/${data.courseId}`, data,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
      });
  }

  deleteCourseDetails(data: string) {
    let token = this.authService.getBearertoken();
    return this.http.delete<any>(this.courseServiceAPI + `/api/v1.0/lms/courses/delete/${data}`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
      });
  }

  searchCourses(technology: string, fromDuration: any, toDuration: any) {
    let token = this.authService.getBearertoken();
    return this.http.get<Course[]>(this.courseServiceAPI + `/api/v1.0/lms/courses/get/${technology}/${fromDuration}/${toDuration}`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
      });
  }
}
