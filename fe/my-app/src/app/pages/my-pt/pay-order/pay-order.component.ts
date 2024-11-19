import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-pay-order',
  templateUrl: './pay-order.component.html',
  styleUrl: './pay-order.component.css'
})
export class PayOrderComponent implements OnInit {
  selectedCourse: any;

  constructor(private router: ActivatedRoute) {}

  ngOnInit() {
    if (history.state.selectedCourse) {
      this.selectedCourse = history.state.selectedCourse;
      console.log("data from course:",this.selectedCourse)
    }
  }
}
