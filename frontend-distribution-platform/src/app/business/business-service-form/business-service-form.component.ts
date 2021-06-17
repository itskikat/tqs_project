import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MENU_ITEMS } from '../../business-menu';
import {BusinessServiceService} from '../../shared/services/business-service.service';
import {BusinessService} from '../../shared/models/BusinessService';
import { ServiceType } from '../../shared/models/ServiceType';
import { Observable, of } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import {ServiceTypeServiceService} from '../../shared/services/service-type-service.service';
import 'rxjs/add/operator/toPromise';
import { title } from 'process';

@Component({
  selector: 'ngx-provider-service-form',
  templateUrl: './business-service-form.component.html',
  styleUrls: ['./business-service-form.component.scss']
})
export class BusinessServiceFormComponent implements OnInit {
  
  menu=MENU_ITEMS
  serviceForm: FormGroup;
  
  businessService: BusinessService;
  filteredOptions$: Observable<string[]>;
  serviceTypes: ServiceType[];
  serviceTypes_Names: string[]=[];
  id: number;
  add_disabled: boolean;

  constructor( private fb: FormBuilder, public router: Router, private route: ActivatedRoute, private businessServiceService: BusinessServiceService,
     private serviceTypeService: ServiceTypeServiceService) { 
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
  }

  ngOnInit(): void {
    this.businessService={id:0, business:{email:""}}
    this.serviceForm = this.fb.group({
      title:['', Validators.minLength(3)],
      price:[0, Validators.min(0)],
      extras: [false]
    });
    this.serviceTypeService.getServiceTypes().subscribe(data=>{
      this.serviceTypes= data;
      this.serviceTypes_Names=[];
      this.serviceTypes.forEach(st=> this.serviceTypes_Names.push(st.name));
      this.filteredOptions$ = of(this.serviceTypes_Names);
      this.filteredOptions$ = this.serviceForm.get('title').valueChanges
        .pipe(
          startWith(''),
          map(filterString => this.filter(filterString)),
        );

      }
    );
   
  }

  onSelectionChange(): void{
    if(this.serviceForm.get('title').value.length >4){
      if(this.serviceTypes_Names.includes(this.serviceForm.get('title').value)){
        this.serviceForm.get('extras').setValue(this.serviceTypes.find(x => x.name==this.serviceForm.get('title').value).hasExtras);
        this.serviceForm.get('extras').disable();
      }
      else{
        this.serviceForm.get('extras').enable();
      }
      this.toggleDisable();
    }
    this.toggleDisable();
  }

  toggleDisable(): void{
    if(( document.getElementById("save") as HTMLButtonElement).disabled){
      ( document.getElementById("save") as HTMLButtonElement).disabled = true;
    }
    ( document.getElementById("save") as HTMLButtonElement).disabled = false;
    
  }
  saveService(): void{
    this.businessService.price=this.serviceForm.get('price').value;
    this.businessService.business.email= localStorage.getItem('email');
    if(!this.serviceTypes_Names.includes(this.serviceForm.get('title').value)){
      this.serviceTypeService.postServiceType({name:this.serviceForm.get('title').value, hasExtras:this.serviceForm.get('extras').value, id:0}).subscribe(
        data=>{
          this.businessService.service=data;
          this.businessServiceService.postBusinnessServices(this.businessService).subscribe(data=>{
            this.router.navigate(['/business/services']);
          });
        }
      );
    }
    else{
      this.businessService.service= this.serviceTypes.find(x => x.name==this.serviceForm.get('title').value)
      this.businessServiceService.postBusinnessServices(this.businessService).subscribe(data=>{
        this.router.navigate(['/business/services']);
      });
    }
    

  }

  private  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    let vals: string[] = this.serviceTypes_Names.filter(optionValue => optionValue.toLowerCase().includes(filterValue)).slice(0,5);
    vals.push(filterValue);
    return vals;

  }
}
