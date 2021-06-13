import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'ngx-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(f: NgForm) {
    // Create observer to login
    const loginObserver = {
      next: x => console.log("User logged in!"),
      error: err => console.log(err)
    }
    this.authService.login(f.value).subscribe(loginObserver);
  }

}
