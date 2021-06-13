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


  constructor(
    private fb: FormBuilder, 
    private authService: AuthService,
    public router: Router
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.email],
      password: ['', Validators.minLength(3)]
    });
  }

  onSubmit(f: FormGroup) {
    // Create observer to login
    const loginObserver = {
      next: x => console.log("User logged in!"),
      error: err => console.log(err)
    }

    this.authService.login(this.loginForm.value).subscribe(loginObserver);
  }

}
