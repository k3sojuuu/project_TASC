import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { PtPageComponent } from './pages/dashboard/pt-page/pt-page.component';
import { StaticalsComponent } from './pages/dashboard/staticals/staticals.component';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    children:[
      {
        path: 'pt-page',
        component: PtPageComponent
      },
      {
        path: 'statical',
        component: StaticalsComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
