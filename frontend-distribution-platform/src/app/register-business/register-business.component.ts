import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'ngx-register-business',
  templateUrl: './register-business.component.html',
  styleUrls: ['./register-business.component.scss']
})
export class RegisterBusinessComponent implements OnInit {

  businessDataForm: FormGroup;
  minDate: Date;
  selectedCategory = '0';
  selectedCountry = '0';
  selectedCoverage = '0';
  tokenGenerated = false;

  constructor(private fb: FormBuilder,public router: Router ) { }

  ngOnInit(): void {
    this.minDate = new Date();
    console.log(this.minDate);
    this.businessDataForm = this.fb.group({
      companyName: ['', Validators.minLength(3)],
      vat: ['', Validators.min(11111111), Validators.max(99999999)],
      address: ['', Validators.minLength(3)],
      operationstart: ['']
    });
  }
  
  finishRegister(){
   this.router.navigate(['/services']);
  }

  generateToken() {
    this.tokenGenerated = true;
  }

  revertToken() {
    this.tokenGenerated = false;
  }


}


