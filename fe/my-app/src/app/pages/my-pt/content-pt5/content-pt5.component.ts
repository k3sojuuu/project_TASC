import { Component } from '@angular/core';

@Component({
  selector: 'app-content-pt5',
  templateUrl: './content-pt5.component.html',
  styleUrl: './content-pt5.component.css'
})
export class ContentPt5Component {
  totalGoals = 20;
  goals: boolean[] = Array(this.totalGoals).fill(false);

  trackByIndex(index: number, obj: any): any {
    return index;
  }
  get completionPercent(): number {
    const completedGoals = this.goals.filter(goal => goal).length;
    return (completedGoals / this.totalGoals) * 100;
  }

  updateCompletion() {
    console.log("Cập nhật phần trăm hoàn thành:", this.completionPercent);
  }
}
