import { Component, OnInit } from '@angular/core';
import { ProfileServiceService } from '../../../services/profile-service.service';

@Component({
  selector: 'app-subpage1',
  templateUrl: './subpage1.component.html',
  styleUrl: './subpage1.component.css'
})
export class Subpage1Component implements OnInit{

  userId: number | null = null;
  userProfile: any | null = null; // Lưu dữ liệu người dùng

  constructor(private profileService: ProfileServiceService) {}

  ngOnInit(): void {
    // Lấy userId từ localStorage
    const storedUserId = localStorage.getItem("userId");
    this.userId = storedUserId ? +storedUserId : null; // Chuyển userId từ string sang number

    if (this.userId !== null) {
      this.getUserById(this.userId);
    }
  }

  getUserById(userId: number): void {
    this.profileService.getUser(userId).subscribe(
      (data: any) => {
        this.userProfile = data;
        console.log("User nè",this.userProfile)
      },
      (error) => {
        console.error("Error fetching user data:", error);
      }
    );
  }

}
