import { Component, OnInit } from '@angular/core';
import { MENU_ITEMS } from '../provider-menu';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'ngx-provider-profile',
  templateUrl: './provider-profile.component.html',
  styleUrls: ['./provider-profile.component.scss']
})
export class ProviderProfileComponent implements OnInit {

  menu=MENU_ITEMS
  minDate: Date;
  personalDataForm: FormGroup;
  formDisabled: boolean;

  constructor(private fb: FormBuilder,public router: Router) { }

  ngOnInit(): void {
    this.formDisabled=true;
    this.minDate = new Date();
    this.minDate.setFullYear(this.minDate.getFullYear() - 18);  
    this.personalDataForm = this.fb.group({
      firstName: ['', Validators.minLength(3)],
      lastName: ['', Validators.minLength(3)],
      address: ['', Validators.minLength(3)],
      bdate: ['']
    });
    this.personalDataForm.disable();
  }

  editProfile(): void{
    this.formDisabled=false;
    this.personalDataForm.enable();
  }

  saveProfile(): void{
    this.personalDataForm.disable();
    this.ngOnInit();
  }
}
