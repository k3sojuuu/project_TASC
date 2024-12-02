import { Component, OnInit } from '@angular/core';
import { FoodServiceService } from '../../../services/food-service.service';

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrl: './food.component.css'
})
export class FoodComponent implements OnInit{
  FoodType: string = 'vitamin'
  FoodGroup: any = []

  constructor(private foodService : FoodServiceService){

  }
  ngOnInit(): void {
    console.log("đang ở trang blog nhé")
    this.getFoodByType(this.FoodType)

  }

  getFoodByType(FoodType: string): void{
    this.foodService.getFood(FoodType).subscribe(
        (res) => {
          this.FoodGroup = res.data
          console.log("Data is:",this.FoodGroup)
        },
        (error) => {
          console.error('Error fetching courses:', error);
        }
    )
  }
}
