import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MENU_ITEMS } from '../../business-menu';
import {BusinessServiceService} from '../../shared/services/business-service.service';

@Component({
  selector: 'ngx-provider-service-form',
  templateUrl: './business-service-form.component.html',
  styleUrls: ['./business-service-form.component.scss']
})
export class BusinessServiceFormComponent implements OnInit {
  
  menu=MENU_ITEMS
  serviceForm: FormGroup;
  id: number;

  constructor( private fb: FormBuilder, public router: Router, private businessServiceService: BusinessServiceService) { 
    this.id = this.router.getCurrentNavigation().id;
  }

  ngOnInit(): void {
    this.serviceForm = this.fb.group({
      title:['', Validators.required],
      price:[0, Validators.min(0)],
      extras: [false]
    });
    if(this.id>0){
      this.businessServiceService.getBusinnessService(this.id).subscribe(data=>{
        this.serviceForm = this.fb.group({
          title:[data.service.name, Validators.required],
          price:[data.price, Validators.min(0)],
          extras: [data.service.hasExtras]
        });
      });
    }
    
  }

  saveService(): void{
    this.router.navigate(['/business/services']);
  }
}
