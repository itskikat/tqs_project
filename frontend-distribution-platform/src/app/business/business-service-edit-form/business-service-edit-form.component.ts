import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MENU_ITEMS } from '../../business-menu';
import {BusinessServiceService} from '../../shared/services/business-service.service';
import {BusinessService} from '../../shared/models/BusinessService';

@Component({
  selector: 'ngx-provider-service-form',
  templateUrl: './business-service-edit-form.component.html',
  styleUrls: ['./business-service-edit-form.component.scss']
})
export class BusinessServiceEditFormComponent implements OnInit {
  
  menu=MENU_ITEMS
  serviceForm: FormGroup;
  
  businessService: BusinessService;

  id: number;

  constructor( private fb: FormBuilder, public router: Router, private route: ActivatedRoute, private businessServiceService: BusinessServiceService) { 
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
  }

  ngOnInit(): void {
    this.businessServiceService.getBusinnessService(this.id).subscribe(data=>{
      this.serviceForm = this.fb.group({
        title:[{value:data.service.name, disabled:true}, Validators.required],
        price:[data.price, Validators.min(0)],
        extras: [{value:data.service.hasExtras, disabled:true}]
      });
      this.businessService=data;
    });
    this.serviceForm.get('title').disable();
    this.serviceForm.get('extras').disable();
  }

  saveService(): void{
    this.businessService.price=this.serviceForm.get('price').value;
    this.businessServiceService.putBusinnessServices(this.id, this.businessService).subscribe(data=>{
      this.router.navigate(['/business/services']);
    });

  }
}
