import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './layout/header/header.component';
import { FooterComponent } from './layout/footer/footer.component';
import { SidebarComponent } from './layout/sidebar/sidebar.component';
import { ApprovalPTComponent } from './pages/dashboard/pt-page/approval-pt/approval-pt.component';
import { ReportPtComponent } from './pages/dashboard/pt-page/report-pt/report-pt.component';
import { ManagementPtComponent } from './pages/dashboard/pt-page/management-pt/management-pt.component';
import { StaticalsComponent } from './pages/dashboard/staticals/staticals.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { PtPageComponent } from './pages/dashboard/pt-page/pt-page.component';
import { DashboardModule } from './pages/dashboard/dashboard.module';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    ApprovalPTComponent,
    StaticalsComponent,
    LoginComponent,
    DashboardComponent,
    PtPageComponent,
    ReportPtComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    FormsModule,
    DashboardModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
