import { ProfileComponent } from './../../../profile.component';
import { Component, OnInit } from '@angular/core';
import { Router,ActivatedRoute } from '@angular/router';
import { ProfileServiceService } from '../../../../../services/profile-service.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrl: './exercise.component.css'
})
export class ExerciseComponent implements OnInit{
  exercise: any[] = [];
  sessionId!: number;
  totalGoals: number = 0;
  goals: boolean[] = [];
  resultExercise: any;
  exerciseComplete: number = 0;

  constructor(private location:Location,private router:Router,private route:ActivatedRoute,private ProfileService:ProfileServiceService){

  }

  goBack(){
    this.location.back();
  }

  ngOnInit(): void {
    console.log("acbnmjhg")
    this.route.queryParams.subscribe(params => {
      this.sessionId = +params['sessionId'];
      console.log("SessionId is:", this.sessionId)
      if (this.sessionId) {
        this.getExerciseById(this.sessionId)
        this.getCountExerComplete(this.sessionId)
      } else {
        console.error("Schedule ID không tồn tại.");
      }
    });
  }

  getExerciseById(sessionId: number): void {
    // Gọi service để lấy dữ liệu exercise theo sessionId
    this.ProfileService.getExercise(sessionId).subscribe(
      (res: any) => {
        this.exercise = res.exerciseList;
        this.totalGoals = this.exercise.length
        this.goals =  Array(this.totalGoals).fill(false);
        console.log("Goal:", this.totalGoals)
        console.log("Data:", this.exercise);
        if (this.exercise && this.exercise.length > 0) {
          this.exercise.forEach(ex => {
            if (ex.videoPath) {
              // Parse videoPath và lấy URL đầu tiên
              try {
                const videoUrls = JSON.parse(ex.videoPath); // parse chuỗi JSON
                ex.videoPath = videoUrls[0]; // Lấy URL đầu tiên trong mảng
              } catch (e) {
                console.error('Error parsing videoPath:', e);
              }
            }
          });
        }
      },(error) => {
        console.error("Error when fetching exercise:", error);
      }
    );
  }




  trackByIndex(index: number, obj: any): any {
    return index;
  }

  get completionPercent(): number {
    const completedGoals = this.exerciseComplete
    const total = this.goals.length
    return (completedGoals / total) * 100;
  }

  updateCompletion(exerciseId: number) {
    this.setCompleteExercise(exerciseId);

  }

  getCountExerComplete(sessionId: number){
    this.ProfileService.getExeComplete(sessionId).subscribe(
      (res : any) => {
          this.exerciseComplete = res
          console.log("get count exe:",this.exerciseComplete)
      },(error) => {
        console.error("Lỗi khi lấy exercise:", error);
      }
    )
  }

  setCompleteExercise(exerciseId: number){
    this.ProfileService.completeExercises(exerciseId).subscribe(
      (res : any) => {
        this.resultExercise = res
        console.log("data update:", this.resultExercise)
      },(error) => {
        console.error("Lỗi khi lấy exercise:", error);
      }
    )
  }
}
