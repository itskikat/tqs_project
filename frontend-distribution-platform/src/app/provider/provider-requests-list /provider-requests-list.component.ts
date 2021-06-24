import { query } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MENU_ITEMS } from '../../provider-menu';
import {ServiceContract} from '../../shared/models/ServiceContract';
import { ServiceStatus } from '../../shared/models/ServiceStatus';
import {ServiceContractService} from '../../shared/services/service-contract.service';
 
@Component({
  selector: 'ngx-provider-requests-list',
  templateUrl: './provider-requests-list.component.html',
  styleUrls: ['./provider-requests-list.component.scss']
})
export class ProviderRequestsListComponent implements OnInit {
    
  // DATA
  menu=MENU_ITEMS;

  previous: boolean;
  next: boolean;
  currentPage: number =0;

  serviceContracts: ServiceContract[];

  status=[{id: "ALL", name:"ALL"},
    {id:ServiceStatus.ACCEPTED , name:ServiceStatus.ACCEPTED},
    {id:ServiceStatus.FINNISHED , name:ServiceStatus.FINNISHED},
    {id:ServiceStatus.REJECTED , name:ServiceStatus.REJECTED},
    {id:ServiceStatus.WAITING , name:ServiceStatus.WAITING}];
  

  order=[
    {id:'date_DESC', name:'Newer'},
    {id:'date_ASC', name:'Older'},
    {id:'review_ASC', name:'Worst Reviews'},
    {id:'review_DESC', name:'Best Reviews'}];
  
  orderSelected: string;
  statusSelected: string;

  constructor(public router: Router, private serviceContractService: ServiceContractService) { }

  ngOnInit(): void {
    this.statusSelected= "ALL";
    this.orderSelected= "date_DESC";
    this.getContracts();
  }

  
  acceptContract(scId: number): void{
    let sc= this.serviceContracts.find(x => x.id === scId);
    sc.status= ServiceStatus.ACCEPTED;
    this.serviceContractService.putServiceContract(scId, sc);
  }

  finnishContract(scId: number): void{
    let sc= this.serviceContracts.find(x => x.id === scId);
    sc.status= ServiceStatus.FINNISHED;
    this.serviceContractService.putServiceContract(scId, sc);
  }


  getContracts(): void{
    let query="?page=" + this.currentPage.toString()
    if(this.statusSelected!="ALL"){
      query+="&status="+ this.statusSelected
    }
    let order= this.orderSelected.split("_")
    query+="&order="+ order[1]+ "&sort="+order[0]
    this.serviceContractService.getServiceContracts("p",query).subscribe(data => {
      this.serviceContracts = data.data;
      if(this.currentPage<1){
        this.previous= false;
      }
      else{
        this.previous=true;
      }
      if(this.currentPage>=data.totalPages-1){
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
    this.getContracts()
  }

  nextPage(): void{
    this.currentPage+=1
    this.getContracts()
  }
}
