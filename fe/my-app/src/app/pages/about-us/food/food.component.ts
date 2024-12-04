import { Component, OnInit } from '@angular/core';
import { FoodServiceService } from '../../../services/food-service.service';
import * as AOS from 'aos';

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrl: './food.component.css'
})
export class FoodComponent implements OnInit{
  FoodType: string = 'vitamin'
  FoodGroup: any = []
  currentIndex: number = 0;
  currentFood: any = null;
  img:any = null;

  constructor(private foodService : FoodServiceService){

  }
  ngOnInit(): void {
    AOS.init({
      duration: 1000,
      easing: 'ease-in-out',
      once: false
    })
    window.addEventListener('scroll', () => {
      AOS.refresh();
    });
    console.log("đang ở trang blog nhé")
    this.getFoodByType(this.FoodType)
    console.log("Data lúc load trang là: ", this.currentFood.img)
  }

  getFoodByType(FoodType: string): void{
    this.foodService.getFood(FoodType).subscribe(
        (res) => {
          this.FoodGroup = res.body.data
          console.log("Data is:",this.FoodGroup)
          this.currentFood = this.FoodGroup[this.currentIndex];
          console.log("data food",this.currentFood)
        },
        (error) => {
          console.error('Error fetching courses:', error);
        }
    )
  }

  onFoodTypeChange(event: Event): void {
    const selectedValue = (event.target as HTMLSelectElement).value;
    this.FoodType = selectedValue;
    console.log('Selected FoodType:', this.FoodType);
    this.getFoodByType(this.FoodType);

  }

  previousSlide(): void {
    if (this.currentIndex === 0) {
      this.currentIndex = this.FoodGroup.length - 1;
    } else {
      this.currentIndex--;
    }
    this.currentFood = this.FoodGroup[this.currentIndex];
    this.img = this.currentFood.img
    console.log("Data food (previous):", this.currentFood);
    console.log("Img is: ",this.img)
    setTimeout(() => {
      AOS.refreshHard();
    }, 100);
  }

  nextSlide(): void {
    if (this.currentIndex === this.FoodGroup.length - 1) {
      this.currentIndex = 0;
    } else {
      this.currentIndex++;
    }
    this.currentFood = this.FoodGroup[this.currentIndex];
    console.log("Data food (next):", this.currentFood);
    this.img = this.currentFood.img
    console.log("Img is: ",this.img)
    setTimeout(() => {
      AOS.refreshHard();
    }, 100);
  }
}
