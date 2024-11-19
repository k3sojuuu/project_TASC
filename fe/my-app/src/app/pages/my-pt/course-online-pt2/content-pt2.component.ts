import { Component, OnInit } from '@angular/core';
import { last } from 'rxjs';
import { CourseServiceService } from '../../../services/course-service.service';
import * as AOS from 'aos'
import { Router } from '@angular/router';

@Component({
  selector: 'app-content-pt2',
  templateUrl: './content-pt2.component.html',
  styleUrl: './content-pt2.component.css'
})
export class ContentPt2Component implements OnInit{


  backgrounds: string[] = [
    'url("assets/img/PtPage/ptpage1.png")',
    'url("assets/img/PtPage/ptpage2.png")',
    'url("assets/img/PtPage/ptpage3.png")'
  ];

  constructor(private courseService: CourseServiceService,private router: Router){}
  ngOnInit(): void {
    AOS.init({
      duration: 2000,
      easing: 'ease-in-out',
      once: true
    })
  }


  backgroundSelector: string = this.backgrounds[0];

  hideButtonGym: boolean = false;
  hideButtonPilates: boolean = false;
  hideButtonYoga: boolean = false;
  statusGym: boolean = false;
  statusYoga: boolean = false;
  statusPilates: boolean = false;

  isPopupVisible: boolean = false;
  selectedCourse: any = null;

  courses: any;
  selectedType: string = '';

  buyNow(selectedCourse: any) {
    this.router.navigate(['/my-pt/pay-order'], {
      state: { selectedCourse: selectedCourse }
    });
  }

  getCourseByType(typeCourses: string): void{
     this.selectedType = typeCourses;
     this.courseService.getCodeByType(typeCourses).subscribe(
      (res) => {
        this.courses = res;
        console.log( res)
      },
      (error) => {
        console.error('Error fetching courses:', error);
      }
     )
  }

  openPopup(course: any): void {
    this.selectedCourse = course;  // Chọn sản phẩm khi nhấn vào "View Detail"
    this.isPopupVisible = true;     // Hiển thị popup
    console.log("Selected course is:", this.selectedCourse)
  }

  closePopup(): void {
    this.isPopupVisible = false;   // Ẩn popup
  }

  leaveGym(){
    this.hideButtonGym = false;
  }
  leavePilates(){
    this.hideButtonPilates = false;
  }
  leaveYoga(){
    this.hideButtonYoga = false;
  }
  gymBackground() {
    this.backgroundSelector = this.backgrounds[0];
    this.hideButtonGym = true;
  }
  pilatesBackground() {
    this.backgroundSelector = this.backgrounds[1];
    this.hideButtonPilates = true;
  }
  yogaBackground() {
    this.backgroundSelector = this.backgrounds[2];
    this.hideButtonYoga = true;
  }
  GymClick() {
    this.statusGym = !this.statusGym;
    this.getCourseByType('Gym')
    if (this.statusGym) {
        this.statusYoga = false;
        this.statusPilates = false;
    }
}
YogaClick() {
    this.statusYoga = !this.statusYoga;
    this.getCourseByType('Yoga')
    if (this.statusYoga) {
        this.statusGym = false;
        this.statusPilates = false;
    }
}
PilatesClick() {
    this.statusPilates = !this.statusPilates;
    this.getCourseByType('Pilates')
    if (this.statusPilates) {
        this.statusGym = false;
        this.statusYoga = false;
    }
}

viewPt(){
  alert("okokok")
}
}
