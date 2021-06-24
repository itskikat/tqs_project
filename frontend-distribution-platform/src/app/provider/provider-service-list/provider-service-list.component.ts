import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MENU_ITEMS } from '../../provider-menu';
import { ProviderService } from '../../shared/models/ProviderService';
import { ProviderServicePage } from '../../shared/models/ProviderServicePage';
import { ProviderServiceService } from '../../shared/services/provider-service.service';


@Component({
  selector: 'ngx-provider-service-list',
  templateUrl: './provider-service-list.component.html',
  styleUrls: ['./provider-service-list.component.scss']
})
export class ProviderServiceListComponent implements OnInit {
  
  menu=MENU_ITEMS;

  previous: boolean;
  next: boolean;
  currentPage: number =0;

  searchQuery:string="";

  providerServices: ProviderService[];

  options=[
    {id:'service_name-ASC', name:'Name (A->Z)'},
    {id:'service_name-DESC', name:'Name (Z->A)'},
    // {id:'price-ASC', name:'Price (€->€€€)'},
    // {id:'price-DESC', name:'Price (€€€->€)'}
  ];
  selected= this.options[0].id;

  constructor(public router: Router, private providerService: ProviderServiceService ) { }

  ngOnInit(): void {
    this.getServices();
  }

  optionSelected() :void{

  }
  editService(id:number) :void{
    this.router.navigate(['/provider/services/edit', id ]);
  }

  deleteService(id:number): void{
    this.providerService.deleteProviderServices(id).subscribe(data=>{
      this.getServices();
    });
  }

  addService(): void{
    this.router.navigate(['/provider/services/add']);
  } 


  getServices(): void{
    let query="?page=" + this.currentPage.toString();

    if(this.searchQuery!=""){
      query+="&name="+ this.searchQuery;
    }
    let order= this.selected.split("-")
    query+="&order="+ order[1]+ "&sort="+order[0]
    this.providerService.getProviderServices(query).subscribe(data => {
      this.providerServices = data.data;
      console.log("PROVIDER SERVICES", data.data);
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
