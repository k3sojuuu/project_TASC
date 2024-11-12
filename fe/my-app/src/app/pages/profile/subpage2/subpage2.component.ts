import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileServiceService } from '../../../services/profile-service.service';

@Component({
  selector: 'app-subpage2',
  templateUrl: './subpage2.component.html',
  styleUrl: './subpage2.component.css'
})
export class Subpage2Component implements OnInit{
  constructor(private route:Router,private profileService: ProfileServiceService){}

  ngOnInit(): void {
    const userId = Number(localStorage.getItem("userId"));
    if (userId) {
      this.getCourse(userId);
    } else {
      console.error("User ID not found in local storage");
    }
  }

    myCourse: any[] = [];

    getCourse(userId: number): void {
      this.profileService.getSchedule(userId).subscribe(
        (res: any) => {
          this.myCourse = res.schedulesList;
          console.log("Course", this.myCourse)
        },
        (error) => {
          console.error("Error fetching user data:", error);
        }
      );
    }

    redirect(){
      this.route.navigate(['/my-pt'])
    }

    viewSession(scheduleId: number): void {
      console.log("Redirecting to session with scheduleId:", scheduleId);
      this.route.navigate(['/profile/subpage2/session'], {
        queryParams: { scheduleId: scheduleId }
      });
    }


}
