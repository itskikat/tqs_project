import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MENU_ITEMS } from '../../business-menu';
import {BusinessService} from '../../shared/models/BusinessService';
import {BusinessServiceService} from '../../shared/services/business-service.service';

@Component({
  selector: 'ngx-business-service',
  templateUrl: './business-service.component.html',
  styleUrls: ['./business-service.component.scss']
})
export class BusinessServiceComponent implements OnInit {

  menu=MENU_ITEMS;

  previous: boolean;
  next: boolean;
  currentPage: number =0;

  searchQuery:string="";

  businessServices: BusinessService[];

  options=[
    {id:'service_name-ASC', name:'Name (A->Z)'},
    {id:'service_name-DESC', name:'Name (Z->A)'},
    {id:'price-ASC', name:'Price (€->€€€)'},
    {id:'price-DESC', name:'Price (€€€->€)'}];
  selected= this.options[0].id;

  constructor(public router: Router, private businessService: BusinessServiceService ) { }

  ngOnInit(): void {
    this.getServices();
  }

  optionSelected() :void{

  }
  editService(id:number) :void{
    this.router.navigate(['/business/services/add', id ]);
  }

  deleteService(id:number): void{
    this.businessService.deleteBusinessServices(id).subscribe(data=>{});
    this.getServices();
  }

  addService(): void{
    this.router.navigate(['/business/services/add',-1]);
  } 

  getServices(): void{
    let query="?page=" + this.currentPage.toString();

    if(this.searchQuery!=""){
      query+="&name="+ this.searchQuery;
    }
    let order= this.selected.split("-")
    query+="&order="+ order[1]+ "&sort="+order[0]
    this.businessService.getBusinnessServices(query).subscribe(data => {
      this.businessServices = data.data;
      if(this.currentPage<1){
        this.previous= false;
      }
      else{
        this.previous=true;
      }
      if(this.currentPage==data.totalPages-1){
        this.next= false;
      }
      else{
        this.next=true;
      }
      console.log(data.data);
    });
  }

  previousPage(): void{
    this.currentPage-=1
    this.getServices()
  }

  nextPage(): void{
    this.currentPage+=1
    this.getServices()
  }
}

  

  

