import { Component, OnInit } from '@angular/core';
import { MENU_ITEMS } from '../../business-menu';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BusinessServiceService } from '../../shared/services/business-service.service';
import { Business } from '../../shared/models/Business';

@Component({
  selector: 'ngx-business-profile',
  templateUrl: './business-profile.component.html',
  styleUrls: ['./business-profile.component.scss']
})
export class BusinessProfileComponent implements OnInit {

  menu=MENU_ITEMS

  business: Business;
  businessDataForm: FormGroup;
  minDate: Date;
  formDisabled: boolean;

  constructor(private fb: FormBuilder,public router: Router, private businessServiceService: BusinessServiceService) { }

  ngOnInit(): void {
    this.formDisabled=true;
    this.minDate = new Date();
    this.businessDataForm = this.fb.group({
      companyName: ['', Validators.minLength(3)],
      vat: ['', [Validators.min(11111111), Validators.max(99999999)]],
      address: ['', Validators.minLength(3)]
    });
    this.businessServiceService.getBusiness(localStorage.getItem("email")).subscribe(data=>{
      this.business=data
      this.businessDataForm.get("companyName").setValue(data.full_name);
      this.businessDataForm.get("vat").setValue(data.nif);
      this.businessDataForm.get("address").setValue(data.address);
    });
    this.businessDataForm.disable();
  }

  editProfile(): void{
    this.formDisabled=false;
    this.businessDataForm.enable();
  }

  saveProfile(): void{
    this.formDisabled=true;
    this.businessDataForm.disable();
    this.business.address=this.businessDataForm.get("address").value;
    this.business.nif=this.businessDataForm.get("vat").value;
    this.business.full_name=this.businessDataForm.get("companyName").value;
    this.businessServiceService.putBusiness(this.business).subscribe(data=>{
      this.ngOnInit();
    });
    
  }
}
