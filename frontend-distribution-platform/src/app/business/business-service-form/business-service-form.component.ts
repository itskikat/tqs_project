import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MENU_ITEMS } from '../../provider-menu';

@Component({
  selector: 'ngx-provider-service-form',
  templateUrl: './business-service-form.component.html',
  styleUrls: ['./business-service-form.component.scss']
})
export class BusinessServiceFormComponent implements OnInit {
  
  menu=MENU_ITEMS
  serviceForm: FormGroup;

  options=[{id:'h', name:'hour'},{id:'unic', name:'unic'}];
  selected= this.options[0].id;

  constructor( private fb: FormBuilder, public router: Router) { }

  ngOnInit(): void {

    this.serviceForm = this.fb.group({
      title:['', Validators.required],
      price:[0, Validators.min(0)],
      description:['', Validators.required],
      area:['', Validators.required],
      extras: [false]
    });
  }

  saveService(): void{
    this.router.navigate(['business/services']);
  }
}
