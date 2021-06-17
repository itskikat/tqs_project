import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'ngx-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  invalid: String = "";


  constructor(
    private fb: FormBuilder, 
    private authService: AuthService,
    public router: Router
  ) { }

  ngOnInit(): void {
    this.redirect();

    this.loginForm = this.fb.group({
      username: ['', Validators.email],
      password: ['', Validators.minLength(3)]
    });
  }

  onSubmit(f: FormGroup) {
    this.invalid = "";
    // Create observer to login
    const loginObserver = {
      next: x => {
        this.redirect();
        this.loginForm.reset();
      },
      error: err => {
        this.invalid = "Invalid username/password";
      }
    }

    this.authService.login(this.loginForm.value).subscribe(loginObserver);
  }

  redirect() {
    // If user is logged in and has valid role, redirect
    if (this.authService.loggedIn()) {
      let role = this.authService.role();
      if (role == "BUSINESS") {
        this.router.navigate(['/business/stats']);
      } else if (role == "PROVIDER") {
        this.router.navigate(['/provider/requests']);
      } else {
        // Invaid role (CLIENT), alert and log out!
        this.invalid = "You don't have permission to access this portal!";
        this.authService.logOut();
      }
    }
  }

}
