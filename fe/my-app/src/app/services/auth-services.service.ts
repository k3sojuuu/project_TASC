import { HttpClient, HttpHeaders, HttpErrorResponse  } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable ,throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthServicesService {
  private isLoggedInSubject = new BehaviorSubject<boolean>(this.checkLoggedIn());
  public isLoggedIn$ = this.isLoggedInSubject.asObservable();

  private loginUrl = 'http://localhost:9000/api/sv1/auth/login';
  private successCode = 'http://localhost:9000/api/sv1/auth/claim_new_password'

  private checkLoggedIn(): boolean {
    return localStorage.getItem('isLoggedIn') === 'true';
  }

  constructor(private http: HttpClient) { }


  claimPassword(codeConfirm: string): Observable<any>{
    return this.http.get<any>(`${this.successCode}?codeCofirm=${codeConfirm}`)
  }

  login(username: string, password: string) {
    return this.http.post<any>(this.loginUrl, { username, password }).pipe(
      tap(response => {
        if (response && response.body && response.body.token) {
          localStorage.setItem('token', response.body.token);
          localStorage.setItem('isLoggedIn', 'true');
          this.isLoggedInSubject.next(true);
        }
      }),
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'An error occurred. Please try again later.';
        if (error.status === 401) {
          errorMessage = 'Invalid username or password';
        }
        return throwError(() => new Error(errorMessage));
      })
    );
  }


  private handleError(error: HttpErrorResponse) {
    return throwError(error.error || 'An error occurred. Please try again later.');
  }
}
