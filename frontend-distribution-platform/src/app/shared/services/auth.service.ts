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
          localStorage.setItem('role', user.type.authority);
          localStorage.setItem('name', user.name);
          localStorage.setItem('email', user.email);
        }
      })
    );
  }

  loggedIn() {
    const token = localStorage.getItem("token");
    // TODO! Call api to check if valid
    return token!=null;
  }

  role() {
    return localStorage.getItem("role");
  }

  logOut() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("name");
    localStorage.removeItem("email");
  }
}
