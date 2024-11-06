
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthServicesService } from '../../services/auth-services.service';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
    isLoggedIn: boolean = false;

    faSignOutAlt = faSignOutAlt;

    constructor(private router: Router, private authService: AuthServicesService) {
      this.isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
  }
  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;
    });
  }

   logout(){
    localStorage.removeItem('token');
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('roles');
    this.router.navigate(['']);
    this.isLoggedIn = false;
   }
}
