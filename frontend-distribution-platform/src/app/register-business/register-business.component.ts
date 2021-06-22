import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BusinessServiceService } from '../shared/services/business-service.service';
import { Business } from '../shared/models/Business';
import {AuthService} from '../shared/services/auth.service';

@Component({
  selector: 'ngx-register-business',
  templateUrl: './register-business.component.html',
  styleUrls: ['./register-business.component.scss']
})
export class RegisterBusinessComponent implements OnInit {

  businessDataForm: FormGroup;
  businessAccountForm: FormGroup;
  minDate: Date;

  token: String;
  tokenGenerated = false;

  constructor(private fb: FormBuilder,public router: Router, private businessService: BusinessServiceService, private authService: AuthService) { }

  ngOnInit(): void {
    this.minDate = new Date();
    this.businessDataForm = this.fb.group({
      companyName: ['', Validators.minLength(3)],
      vat: ['', [Validators.min(11111111), Validators.max(99999999)]],
      address: ['', Validators.minLength(3)],
    });

    this.businessAccountForm = this.fb.group({
      email: ['', Validators.email],
      pass: ['', Validators.minLength(3)],
      newpass: ['', Validators.minLength(3)],
    });

  }
  
  checkEqual(){
    return this.businessAccountForm.get("pass").value==this.businessAccountForm.get("newpass").value;
  }

  finishRegister(){
   this.router.navigate(['/business/stats']);
  }

  generateToken() {
    this.authService.login({"email":this.businessAccountForm.get("email").value, "password": this.businessAccountForm.get("pass").value})
    this.tokenGenerated = true;
  }

  saveBusiness(){
    let business: Business={email:''};
    console.log(this.businessAccountForm);
    business.email= this.businessAccountForm.value.email;
    business.password= this.businessAccountForm.get("pass").value;
    business.address= this.businessDataForm.get("address").value;
    business.full_name= this.businessDataForm.get("companyName").value;
    business.nif= this.businessDataForm.get("vat").value;
    this.businessService.registBusiness(business).subscribe(
      data =>{
        this.token = data.apikey
      }
    );
  }

}


