import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Client } from "src/app/shared/models/Client";
import { AuthService } from "src/app/shared/services/auth.service";

@Component({
  selector: "service-register",
  templateUrl: "./register.component.html",
  host: {'class': 'w-full flex flex-column flex-wrap'}
})
export class ServiceRegisterComponent implements OnInit {

  registerForm: FormGroup;
  invalid: String = "";
  client: Client;
  maxDate: String;
  
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    public router: Router
  ) {
  }

  ngOnInit(): void {
    this.redirect();

    let d = new Date();
    d.setFullYear(d.getFullYear()-18);
    this.maxDate = d.getFullYear() + "-0" + d.getMonth() + '-' + d.getDate();

    this.registerForm = this.fb.group({
      email: ['', Validators.email],
      password: ['', Validators.minLength(3)],
      full_name: ['', Validators.minLength(3)],
      address: ['', Validators.minLength(3)],
      birthdate: ['', ],
    });
  }

  onSubmit(f: FormGroup) {
    this.invalid = "";
    // Create observer to login
    const loginObserver = {
      next: x => {
        if (x==null) {
          this.invalid = "Invalid data. Please make sure you are not already registered."
        } else {
          this.registerForm.reset();
          this.client = x;
        }
        console.log(x);
      },
      error: err => {
        this.invalid = err.error.message;
      }
    }

    this.authService.register(this.registerForm.value).subscribe(loginObserver);
  }

  redirect() {
    // If user is logged, redirect
    if (this.authService.loggedIn()) {
      this.router.navigate(['/dashboard']);
    }
  }
}
