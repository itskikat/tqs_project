import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authUrl = "http://localhost:8080/api/users/";

  constructor(private http: HttpClient) { }

  login(model: any) {
    return this.http.post(this.authUrl + "login", model).pipe(
      map((response:any) => {
        const user = response;
        if (user.token) {
          localStorage.setItem('token', user.token);
          
        }
      })
    );
  }

  loggedIn() {
    const token = localStorage.getItem("token");
    // TODO! Call api to check if valid
    return token!=null;
  }

  logOut() {
    localStorage.removeItem("token");
  }
}
