import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "src/app/shared/services/auth.service";

@Component({
  selector: "service-index",
  templateUrl: "./index.component.html",
  host: {'class': 'w-full flex flex-column flex-wrap'}
})
export class ServiceIndexComponent implements OnInit {
  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    // Redirect logged users to dashboard
    if (this.authService.loggedIn) {
      this.router.navigate(['/dashboard']);
    }
  }
}
