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
  secondForm: FormGroup;
  thirdForm: FormGroup;

  constructor(private fb: FormBuilder,public router: Router ) { }

  ngOnInit(): void {
    this.personalDataForm = this.fb.group({
      firstName: ['', Validators.minLength(3)],
      lastName: ['', Validators.minLength(3)],
      address: ['', Validators.minLength(3)],
    });
  }
  
  finishRegister(){
   this.router.navigate(['/login']);
  }

  onSecondSubmit() {
    this.secondForm.markAsDirty();
  }

  onThirdSubmit() {
    this.thirdForm.markAsDirty();
  }
}


