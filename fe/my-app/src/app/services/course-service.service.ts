import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse  } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CourseServiceService {


  private getCourseByType = 'http://localhost:8081/sv2/course/getCourseFilter'

  constructor(private http: HttpClient) { }

  getCodeByType(typeCourse: string): Observable<any>{
      return this.http.get<any>(`${this.getCourseByType}?type=${typeCourse}`)
  }

}
