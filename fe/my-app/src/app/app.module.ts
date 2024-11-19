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
import { ContentPt1Component } from './pages/my-pt/content-pt1/content-pt1.component';
import { ContentPt2Component } from './pages/my-pt/course-online-pt2/content-pt2.component';
import { ContentPt3Component } from './pages/my-pt/content-pt3/content-pt3.component';
import { ContentPt4Component } from './pages/my-pt/content-pt4/content-pt4.component';
import { ContentPt5Component } from './pages/my-pt/content-pt5/content-pt5.component';
import { PtPageModule } from './pages/my-pt/pt-page.module';
import { SlideHomePageComponent } from './pages/home-pages/slide-home-page/slide-home-page.component';
import { NgCircleProgressModule } from 'ng-circle-progress';
import { Subpage1Component } from './pages/profile/subpage1/subpage1.component';
import { Subpage2Component } from './pages/profile/subpage2/subpage2.component';
import { Subpage3Component } from './pages/profile/subpage3/subpage3.component';
import { Subpage4Component } from './pages/profile/subpage4/subpage4.component';
import { SessionComponent } from './pages/profile/subpage2/session/session.component';
import { ExerciseComponent } from './pages/profile/subpage2/session/exercise/exercise.component';



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
    FeedbackHomePageComponent,
    ContentPt1Component,
    ContentPt2Component,
    ContentPt3Component,
    ContentPt4Component,
    ContentPt5Component,
    SlideHomePageComponent,
    Subpage1Component,
    Subpage2Component,
    Subpage3Component,
    Subpage4Component,
    SessionComponent,
    ExerciseComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HomePagesModule,
    FontAwesomeModule,
    PtPageModule,
    NgCircleProgressModule.forRoot({
      radius: 50,
      outerStrokeWidth: 8,
      innerStrokeWidth: 4,
      outerStrokeColor: "#78C000",
      innerStrokeColor: "#C7E596",
      animationDuration: 300,
    }),
  ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent]
})
export class AppModule { }
