import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MENU_ITEMS } from '../provider-menu';

@Component({
  selector: 'ngx-provider-service-form',
  templateUrl: './provider-service-form.component.html',
  styleUrls: ['./provider-service-form.component.scss']
})
export class ProviderServiceFormComponent implements OnInit {
  
  menu=MENU_ITEMS
  serviceForm: FormGroup;

  options=[{id:'price', name:'price'},{id:'aaaa', name:'aaaa'}];
  selected= this.options[0].id;

  constructor( private fb: FormBuilder) { }

  ngOnInit(): void {

    this.serviceForm = this.fb.group({
      subject:['', Validators.required],
      price:[0, Validators.min(0)],
      description:['', Validators.required],
      area:['', Validators.required]
    });
  }

  saveService(): void{

  }
}
