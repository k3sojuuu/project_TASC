import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { PtServiceService } from '../../../../service/pt-service.service';

@Component({
  selector: 'app-management-pt',
  templateUrl: './management-pt.component.html',
  styleUrl: './management-pt.component.css'
})
export class ManagementPtComponent implements OnInit{
   ptList: any[] = [];
   page: number = 0;
   size: number = 10;
   totalItems: number = 0;

   constructor(private ptService:PtServiceService){}

   ngOnInit(): void {
     this.loadPts();
   }

   loadPts(){
    this.ptService.getAllPtPaging(this.page, this.size).subscribe(
      (res : any) =>{
        this.ptList = res.data
        this.totalItems = res.totalItems;
        console.log("Data của tao đây nè pt",this.ptList)
      },
      (error) => {
        console.error("Error fetching user data:", error);
      }
    )
   }

   nextPage(): void {
    if ((this.page + 1) * this.size < this.totalItems) {
      this.page++;
      this.loadPts();
    }
  }

  prevPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadPts();
    }
  }

  onPageChange(page: number): void {
    if (page >= 0 && page * this.size < this.totalItems) {
      this.page = page;
      this.loadPts();
    }
  }

  viewDetailPt(userId: number){
    alert("UserId is:" + userId)
  }
}
