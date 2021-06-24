import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { apiUrl } from '../../../environments/environment';
import { User } from '../models/User';

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
    return token!=null;
  }

  logOut() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("name");
    localStorage.removeItem("email");
  }

  loggedData() {
    return this.http.get<User>(this.authUrl + "/logged", this.getOptions()).toPromise();
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

  // Helpers
  getOptions() {
    // Call this method to get HttpClient options
    // Example: this.http.get<Hero[]>(this.heroesUrl, authService.getOptions())
    return {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem("token"),
        'Access-Control-Allow-Origin': '*'
      })
    }
  }
}
