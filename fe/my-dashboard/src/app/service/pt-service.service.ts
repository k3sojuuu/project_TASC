import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environtment/environment';

const BaseUrl = environment.ApiUrl;
const Endpoint = 'sv1/pt';

@Injectable({
  providedIn: 'root'
})
export class PtServiceService {

  constructor(private http:HttpClient) { }

  private getAllPt = "http://localhost:8000/api/sv1/user/getAllPt";
  private getAllRegisPt = "http://localhost:8000/api/sv1/user/getAllRegisPT";


  getAllPtPaging(page: number,size: number){
    return this.http.get<any>(`${this.getAllPt}?page=${page}&size=${size}`)
  }

  getAllUserRegisPT(page:number,size: number){
    return this.http.get<any>(`${this.getAllRegisPt}?page=${page}&size=${size}`)
  }

  rejectPt = (userId: number,message: string) => this.http.post(`${BaseUrl}/${Endpoint}/reject`,{userId,message})
  acceptsPt = (userId: number,message: string) => this.http.post(`${BaseUrl}/${Endpoint}/accepts`,{userId,message})

}
