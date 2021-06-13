import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { apiUrl } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authUrl = apiUrl + "/users";

  constructor(private http: HttpClient) { }

  // Operations
  login(model: any) {
    return this.http.post(this.authUrl + "/login", model).pipe(
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

  logOut() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("name");
    localStorage.removeItem("email");
  }

  // Getters
    role() {
    return localStorage.getItem("role");
  }

  user() {
    return {
      'name': localStorage.getItem("name"),
      'email': localStorage.getItem("email"),
      'role': localStorage.getItem("role")
    }
  }
}
