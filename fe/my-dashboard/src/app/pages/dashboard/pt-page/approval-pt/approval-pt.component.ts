import { Component, OnInit } from '@angular/core';
import { PtServiceService } from '../../../../service/pt-service.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-approval-pt',
  templateUrl: './approval-pt.component.html',
  styleUrl: './approval-pt.component.css'
})
export class ApprovalPtComponent implements OnInit{

  ngOnInit(): void {
    this.loadRegisPts();
  }

  userRegisList: any[] = [];
  page: number = 0;
  size: number = 10;
  totalItems: number = 0;
  popupStatus: boolean = false;
  popupAccept: boolean = false;
  message: string = "";
  UserId: number = 0
  response: string = '';
  constructor(private ptService:PtServiceService,private toastr: ToastrService){}

  loadRegisPts(){
    this.ptService.getAllUserRegisPT(this.page, this.size).subscribe(
      (res : any) =>{
        this.userRegisList = res.data
        this.totalItems = res.totalItems;
        console.log("Data của tao đây nè",this.userRegisList)
      },
      (error) => {
        console.error("Error fetching user data:", error);
      }
    )
   }

   nextPage(): void {
    if ((this.page + 1) * this.size < this.totalItems) {
      this.page++;
      this.loadRegisPts();
    }
  }

  prevPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadRegisPts();
    }
  }

  onPageChange(page: number): void {
    if (page >= 0 && page * this.size < this.totalItems) {
      this.page = page;
      this.loadRegisPts();
    }
  }

  viewDetailPt(userId: number){
    alert("UserId is:" + userId)
  }



  OnPopup(userId:number){
    this.popupStatus = true;
    this.UserId = userId;
    console.log("popups are enabled")
  }

  OffPopup(){
    this.popupStatus = false;
    console.log("popups are disabled")
  }

  OnPopupAccepts(userId:number){
     this.popupAccept = true;
     this.UserId = userId;
  }

  OffPopupAccept(){
    this.popupAccept = false;
    console.log("popups are disabled")
  }

  sendRejectMessage(rejectUserId:number,message:string){
     this.ptService.rejectPt(rejectUserId,message).subscribe(
      (res : any) => {
        this.response = res;
        this.loadRegisPts()
      },(error) => {
        console.error("Error fetching user data:", error);
      }
     )
     alert("Ok rồi nhé")
     this.popupStatus = false;
  }

  sendAcceptMessage(userId:number,message:string){
    this.ptService.acceptsPt(userId,message).subscribe(
      (res : any) => {
        this.response = res;
        this.loadRegisPts()
        this.toastr.success('Data saved successfully!', 'Success');
      },(error) => {
        console.error("Error fetching user data:", error);
      }
    )
    this.popupAccept = false;
  }

  showSuccess() {
    this.toastr.success('Data saved successfully!', 'Success');
  }

  showError() {
    this.toastr.error('An error occurred!', 'Error');
  }

  showInfo() {
    this.toastr.info('Here is some information.', 'Info');
  }

  showWarning() {
    this.toastr.warning('Be careful!', 'Warning');
  }

}
