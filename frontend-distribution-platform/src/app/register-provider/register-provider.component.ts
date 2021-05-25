import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'ngx-register-provider',
  templateUrl: './register-provider.component.html',
  styleUrls: ['./register-provider.component.scss']
})
export class RegisterProviderComponent implements OnInit {

  personalDataForm: FormGroup;

  minDate: Date;

  constructor(private fb: FormBuilder,public router: Router ) { }

  ngOnInit(): void {
    this.minDate = new Date();
    this.minDate.setFullYear(this.minDate.getFullYear() - 18);  
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


