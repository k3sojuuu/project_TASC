import { SwiperModule } from './../../node_modules/swiper/types/shared.d';
import { FontawesomeObject } from './../../node_modules/@fortawesome/fontawesome-svg-core/index.d';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SidebarComponent } from './layout/sidebar/sidebar.component';
import { HeaderComponent } from './layout/header/header.component';
import { FooterComponent } from './layout/footer/footer.component';
import { HomePagesComponent } from './pages/home-pages/home-pages.component';
import { AboutUsComponent } from './pages/about-us/about-us.component';
import { Content1Component } from './pages/home-pages/content1/content1.component';
import { Content2Component } from './pages/home-pages/content2/content2.component';
import { HomePagesModule } from './pages/home-pages/home-pages.module';
import { ContactsComponent } from './pages/contacts/contacts.component';
import { MyPtComponent } from './pages/my-pt/my-pt.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { LoginsComponent } from './pages/auth/logins/logins.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RegisterComponent } from './pages/auth/register/register.component';
import { ForgotPasswordComponent } from './pages/auth/forgot-password/forgot-password.component';
import { ConfirmPasswordComponent } from './pages/auth/confirm-password/confirm-password.component';
import { CalculatorFatComponent } from './pages/home-pages/calculator-fat/calculator-fat.component';
import { Content3Component } from './pages/home-pages/content3/content3.component';
import { FeedbackHomePageComponent } from './pages/home-pages/feedback-home-page/feedback-home-page.component';


@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    HeaderComponent,
    FooterComponent,
    HomePagesComponent,
    AboutUsComponent,
    Content1Component,
    Content2Component,
    Content3Component,
    ContactsComponent,
    MyPtComponent,
    ProfileComponent,
    LoginsComponent,
    RegisterComponent,
    ForgotPasswordComponent,
    ConfirmPasswordComponent,
    CalculatorFatComponent,
    FeedbackHomePageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HomePagesModule,
    FontAwesomeModule
  ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent]
})
export class AppModule { }
