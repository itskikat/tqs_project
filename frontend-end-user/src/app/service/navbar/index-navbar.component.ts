import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { User } from "src/app/shared/models/User";
import { AuthService } from "src/app/shared/services/auth.service";

@Component({
  selector: "service-navbar",
  templateUrl: "./index-navbar.component.html",
  host: {'class': 'w-full flex-self-start'}
})
export class ServiceNavbarComponent implements OnInit {
  navbarOpen = false;

  user: User;
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Get user data
    this.authService.loggedData().then(data => {
      this.user=data;
    }).catch(error => {
      if (this.router.url.split('?')[0]!="/" && this.router.url.split('?')[0]!="/login") {
        if (error.status==401) {
          this.authService.logOut();
          alert("Your session has expired!");
          this.router.navigate(['/login']);
        }
      }
    });
  }

  setNavbarOpen() {
    this.navbarOpen = !this.navbarOpen;
  }

  logOut() {
    this.authService.logOut();
    this.router.navigate(['/login']);
  }
}
