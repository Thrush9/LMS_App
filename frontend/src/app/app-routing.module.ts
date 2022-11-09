import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home.component';
import { SignupComponent } from './components/signup/signup.component';
import { SigninComponent } from './components/signin/signin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AddCompanyComponent } from './components/add-company/add-company.component';
import { AddCourseComponent } from './components/add-course/add-course.component';
import { CourseDetailsComponent } from './components/course-details/course-details.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { UserDashboardComponent } from './components/user-dashboard/user-dashboard.component';
import { LandingPageGuard } from './guards/landingpage.guard';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'signin',
    component: SigninComponent
  },
  {
    path: 'signup',
    component: SignupComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [LandingPageGuard],
    children: [
      {
        path: 'admin',
        component: AdminDashboardComponent,
        canActivate: [LandingPageGuard]
      },
      {
        path: 'addCompany',
        component: AddCompanyComponent,
        canActivate: [LandingPageGuard]
      },
      {
        path: 'addCourse',
        component: AddCourseComponent,
        canActivate: [LandingPageGuard]
      },
      {
        path: 'courseList',
        component: CourseDetailsComponent,
        canActivate: [LandingPageGuard]
      },
      {
        path: 'user',
        component: UserDashboardComponent,
        canActivate: [LandingPageGuard]
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
