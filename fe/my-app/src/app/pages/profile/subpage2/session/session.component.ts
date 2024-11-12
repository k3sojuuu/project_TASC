import { Component, OnInit } from '@angular/core';
import { ProfileServiceService } from '../../../../services/profile-service.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-session',
  templateUrl: './session.component.html',
  styleUrl: './session.component.css'
})
export class SessionComponent implements OnInit{

 constructor(private ProfileService:ProfileServiceService
            ,private route: ActivatedRoute){}


  goals: boolean[] = [];
  sessionComplete: number = 0;
  totalSessionInSchedule: number = 0;
  scheduleId!: number;
  sessions: any[] = [];
  userId: any = localStorage.getItem("userId");
  resultSession: any;

  ngOnInit(): void {
    console.log("acbnmjhg")
    this.route.queryParams.subscribe(params => {
      this.scheduleId = +params['scheduleId'];
      console.log("Id is:", this.scheduleId)
      if (this.scheduleId) {
        this.getSessions(this.scheduleId);
        this.getTotalCountSession(this.scheduleId)
        this.getTotalSessionInSchedule(this.scheduleId,this.userId)
        this.updateCompletion
      } else {
        console.error("Schedule ID không tồn tại.");
      }
    });
  }
  setCompleteSession(sessionId:number){
    this.ProfileService.completeSessions(sessionId).subscribe(
      (res : any) => {
          this.resultSession = res
          console.log("data update:", this.resultSession)
      },(error) => {
        console.error("Lỗi khi lấy sessions:", error);
      }
    )


  }

  getTotalSessionInSchedule(scheduleId: number,userId: number){
     this.ProfileService.getTotalSessionInSchedule(scheduleId,userId).subscribe(
        (res : any) => {
          this.totalSessionInSchedule = res
          console.log("total", this.totalSessionInSchedule)
        },(error) =>  {
          console.error("Lỗi khi lấy sessions:", error);
        }
     )
  }

  getTotalCountSession(scheduleId: number){
    this.ProfileService.getSessionComplete(scheduleId).subscribe(
      (res : any) => {
        this.sessionComplete = res
        console.log("total:", this.sessionComplete)
      },
      (error) => {
        console.error("Lỗi khi lấy sessions:", error);
      }
    )
  }

  getSessions(scheduleId: number){
    this.ProfileService.getSession(scheduleId).subscribe(
      (res : any) => {
        this.sessions = res.sessionsList
        console.log("data:",this.sessions)
        this.goals = Array(this.sessions.length).fill(false);
      },
      (error) => {
        console.error("Lỗi khi lấy sessions:", error);
      }
    );
  }

  get completionPercent(): number {
    const completedGoals = this.sessionComplete;
    const total = this.totalSessionInSchedule
    return (completedGoals / total) * 100;
  }

  updateCompletion(sessionId:number) {
    this.setCompleteSession(sessionId);
  }

}
