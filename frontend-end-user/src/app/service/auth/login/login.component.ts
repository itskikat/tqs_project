import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { AuthService } from '../../../shared/services/auth.service';
import { distributionPlatform } from '../../../../environments/environment';
import { Router } from "@angular/router";

@Component({
  selector: "service-login",
  templateUrl: "./login.component.html",
  host: {'class': 'w-full flex flex-column flex-wrap'}
})
export class ServiceLoginComponent implements OnInit {

  backboneUrl: String = distributionPlatform;

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
        this.invalid = err.error.message;
      }
    }

    this.authService.login(this.loginForm.value).subscribe(loginObserver);
  }

  redirect() {
    // If user is logged, redirect
    if (this.authService.loggedIn()) {
      this.router.navigate(['/dashboard']);
    }
  }
}
