import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { PtPageComponent } from './pages/dashboard/pt-page/pt-page.component';
import { StaticalsComponent } from './pages/dashboard/staticals/staticals.component';
import { ManagementPtComponent } from './pages/dashboard/pt-page/management-pt/management-pt.component';
import { ReportPtComponent } from './pages/dashboard/pt-page/report-pt/report-pt.component';
import { ApprovalPtComponent } from './pages/dashboard/pt-page/approval-pt/approval-pt.component';

const routes: Routes = [
  {
    path: '',
    component:LoginComponent
  },
  {
    path: 'pt-page',
    component:PtPageComponent,
    children:[
      {
         path:'managementPt',
         component:ManagementPtComponent
      },
      {
         path:'report',
         component:ReportPtComponent
      },
      {
         path:'approval',
         component:ApprovalPtComponent
      }
    ]
  },
  {
    path: 'statical',
    component:StaticalsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
