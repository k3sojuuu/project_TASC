import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PtPageComponent } from './pages/dashboard/pt-page/pt-page.component';
import { StaticalsComponent } from './pages/dashboard/staticals/staticals.component';
import { ApprovalPtComponent } from './pages/dashboard/pt-page/approval-pt/approval-pt.component';
import { ManagementPtComponent } from './pages/dashboard/pt-page/management-pt/management-pt.component';
import { ReportPtComponent } from './pages/dashboard/pt-page/report-pt/report-pt.component';
import { LoginComponent } from './pages/login/login.component';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from './layout/header/header.component';
import { FooterComponent } from './layout/footer/footer.component';
import { SidebarComponent } from './layout/sidebar/sidebar.component';
import { HttpClient } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';


@NgModule({
  declarations: [
    AppComponent,
    PtPageComponent,
    StaticalsComponent,
    ApprovalPtComponent,
    ManagementPtComponent,
    ReportPtComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    SidebarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    FormsModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true, 
    }),
  ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent]
})
export class AppModule { }
