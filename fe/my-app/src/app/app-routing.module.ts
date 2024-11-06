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
    canActivate:[AuthGuard]
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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
