import { Component, OnInit } from '@angular/core';
import { MENU_ITEMS } from '../../business-menu';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'ngx-business-profile',
  templateUrl: './business-profile.component.html',
  styleUrls: ['./business-profile.component.scss']
})
export class BusinessProfileComponent implements OnInit {

  menu=MENU_ITEMS
  businessDataForm: FormGroup;
  minDate: Date;
  selectedCategory = '0';
  selectedCountry = '0';
  selectedCoverage = '0';
  formDisabled: boolean;

  constructor(private fb: FormBuilder,public router: Router) { }

  ngOnInit(): void {
    this.formDisabled=true;
    this.minDate = new Date();
    this.businessDataForm = this.fb.group({
      companyName: ['', Validators.minLength(3)],
      vat: ['', Validators.min(11111111), Validators.max(99999999)],
      address: ['', Validators.minLength(3)],
      operationstart: [''],
      country: ['']
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
    this.ngOnInit();
  }
}
