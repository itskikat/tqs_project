import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'ngx-register-business',
  templateUrl: './register-business.component.html',
  styleUrls: ['./register-business.component.scss']
})
export class RegisterBusinessComponent implements OnInit {

  personalDataForm: FormGroup;

  minDate: Date;

  constructor(private fb: FormBuilder,public router: Router ) { }

  ngOnInit(): void {
    this.minDate = new Date();
    this.minDate.setFullYear(this.minDate.getFullYear() - 18);  
    console.log(this.minDate);
    this.personalDataForm = this.fb.group({
      firstName: ['', Validators.minLength(3)],
      lastName: ['', Validators.minLength(3)],
      address: ['', Validators.minLength(3)],
      bdate: ['']
    });
  }
  
  finishRegister(){
   this.router.navigate(['/services']);
  }


}


