import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { convertCompilerOptionsFromJson } from 'typescript';

@Component({
  selector: 'ngx-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;


  constructor(private fb: FormBuilder, public router: Router) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.email],
      password: ['', Validators.minLength(3)]
    });
  }

  onSubmit(f: FormGroup) {
    console.log("FORM SUBMITTED", f.value);
  }

}
