import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { Company } from '../models/Company';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  //courseServiceAPI: string = 'http://localhost:8084';
  courseServiceAPI: string = 'http://localhost:8765/CourseService';

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAllCompanyDetails() {
    let token = this.authService.getBearertoken();
    return this.http.get<Company[]>(this.courseServiceAPI + '/api/v1.0/lms/company/getAllCompanies',
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
      });
  }

  registerCompany(data: Company) {
    let token = this.authService.getBearertoken();
    return this.http.post<any>(this.courseServiceAPI + '/api/v1.0/lms/company/register', data,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
      });
  }

}
