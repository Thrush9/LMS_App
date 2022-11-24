import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  //userServiceAPI: string = 'http://localhost:8765/UserService';
  userServiceAPI: string = 'http://userservice-env.eba-muuciyqz.ap-south-1.elasticbeanstalk.com';

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAllUserDetails() {
    let token = this.authService.getBearertoken();
    return this.http.get<User[]>(this.userServiceAPI + '/api/v1.0/lms/fetchAllUsers',
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
      });
  }

}
