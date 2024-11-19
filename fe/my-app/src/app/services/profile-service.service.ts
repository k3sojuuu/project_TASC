import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse  } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileServiceService {



  private getUserByUserId = 'http://localhost:8000/api/sv1/user/getUser'
  private getScheduleByUserId = 'http://localhost:8085/sv4/schedules/getScheduleById'
  private getSessionById = "http://localhost:8085/sv4/schedules/getSessionByScheduleId"
  private getCountSessionComplete = "http://localhost:8085/sv4/schedules/getCountSessionComplete"
  private getTotalSession = "http://localhost:8085/sv4/schedules/getTotalSessionByScheduleIdAndUserId"
  private completeSession = "http://localhost:8085/sv4/schedules/setCompleteSession"
  private completeExercise = "http://localhost:8085/sv4/schedules/setCompleteExercise"
  private getExerciseBySessionId = "http://localhost:8085/sv4/schedules/getExerciseBySessionId"
  private getExerciseComplete = "http://localhost:8085/sv4/schedules/getCountExerciseComplete"

  constructor(private http: HttpClient) { }

  getExeComplete(sessionId: number){
    return this.http.get<any>(`${this.getExeComplete}?sessionId=${sessionId}`)
  }

  getExercise(sessionId: number){
    return this.http.get<any>(`${this.getExerciseBySessionId}?sessionId=${sessionId}`)
  }

  completeSessions(sessionId: number){
    return this.http.put<any>(`${this.completeSession}?sessionId=${sessionId}`, sessionId)
  }

  completeExercises(exerciseId: number){
    return this.http.put<any>(`${this.completeExercise}?exerciseId=${exerciseId}`,exerciseId)
  }

  getTotalSessionInSchedule(scheduleId: number,userId:number){
    return this.http.get<any>(`${this.getTotalSession}?scheduleId=${scheduleId}&userId=${userId}`)
  }

  getSessionComplete(scheduleId: number){
    return this.http.get<any>(`${this.getCountSessionComplete}?scheduleId=${scheduleId}`)
  }

  getSession(schedulesId: number): Observable<any>{
    return this.http.get<any>(`${this.getSessionById}?scheduleId=${schedulesId}`)
  }

  getUser(userId: number): Observable<any>{
    return this.http.get<any>(`${this.getUserByUserId}?userId=${userId}`)
  }

  getSchedule(userId: number): Observable<any>{
    return this.http.get<any>(`${this.getScheduleByUserId}?userId=${userId}`)
  }
}
