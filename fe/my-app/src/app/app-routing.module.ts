import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePagesComponent } from './pages/home-pages/home-pages.component';
import { AboutUsComponent } from './pages/about-us/about-us.component';
import { MyPtComponent } from './pages/my-pt/my-pt.component';
import { ContactsComponent } from './pages/contacts/contacts.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { LoginsComponent } from './pages/auth/logins/logins.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { ForgotPasswordComponent } from './pages/auth/forgot-password/forgot-password.component';
import { AuthGuard } from './pages/auth/auth.guard';
import { ConfirmPasswordComponent } from './pages/auth/confirm-password/confirm-password.component';
import { PayOrderComponent } from './pages/my-pt/pay-order/pay-order.component';
import { Subpage1Component } from './pages/profile/subpage1/subpage1.component';
import { Subpage2Component } from './pages/profile/subpage2/subpage2.component';
import { Subpage3Component } from './pages/profile/subpage3/subpage3.component';
import { Subpage4Component } from './pages/profile/subpage4/subpage4.component';
import { SessionComponent } from './pages/profile/subpage2/session/session.component';

const routes: Routes = [
  {
    path: '',
    component: HomePagesComponent
  },
  {
    path: 'aboutUs',
    component: AboutUsComponent
  },
  {
    path: 'login',
    component: LoginsComponent
  },
  {
    path: 'my-pt',
    component: MyPtComponent
  },
  {
    path: 'contacts',
    component: ContactsComponent
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate:[AuthGuard],
    children:[
      {
        path: 'subpage1',
        component: Subpage1Component
      },
      {
        path: 'subpage2',
        component: Subpage2Component,
        children:[
          {
            path: 'session',
            component: SessionComponent
          }
        ]
      },
      {
        path: 'subpage3',
        component: Subpage3Component
      },
      {
        path: 'subpage4',
        component: Subpage4Component
      }
    ]
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent
  },
  {
    path: 'confirm-code',
    component: ConfirmPasswordComponent
  },
  {
    path:  'my-pt/pay-order',
    component: PayOrderComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
