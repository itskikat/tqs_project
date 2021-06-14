import { query } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MENU_ITEMS } from '../provider-menu';
import {ServiceContract} from '../shared/models/ServiceContract';
import { ServiceStatus } from '../shared/models/ServiceStatus';
import {ServiceContractService} from '../shared/services/service-contract.service';
 
@Component({
  selector: 'ngx-provider-requests-list',
  templateUrl: './provider-requests-list.component.html',
  styleUrls: ['./provider-requests-list.component.scss']
})
export class ProviderRequestsListComponent implements OnInit {
    
  // DATA
  menu=MENU_ITEMS;


  serviceContracts: ServiceContract[];

  status=[{id: "ALL", name:"ALL"},{id:ServiceStatus.ACCEPTED , name:ServiceStatus.ACCEPTED},
    {id:ServiceStatus.FINNISHED , name:ServiceStatus.FINNISHED},
    {id:ServiceStatus.REJECTED , name:ServiceStatus.REJECTED},
    {id:ServiceStatus.WAITING , name:ServiceStatus.WAITING}];
  statusSelected= this.status[0].id;

  order=[
    {id:'date_DESC', name:'Newer'},
    {id:'date_ASC', name:'Older'},
    {id:'review_ASC', name:'Worst Reviews'},
    {id:'review_DESC', name:'Best Reviews'}];
  orderSelected= this.order[0].id;

  constructor(public router: Router, private serviceContractService: ServiceContractService) { }

  ngOnInit(): void {
    this.getContracts();
  }

  editService() :void{
    this.router.navigate(['/services/add']);
  }

  deleteService(): void{
  }

  addService(): void{
    this.router.navigate(['/services/add']);
  }
  
  acceptContract(scId: number): void{

  }

  getContracts(): void{
    let query="?page=0"
    if(this.statusSelected!="ALL"){
      query+="&status="+ this.statusSelected
    }
    let order= this.orderSelected.split("_")
    query+="&order="+ order[1]+ "&sort="+order[0]
    console.log(query)
    this.serviceContractService.getServiceContracts("p",query).subscribe(data => {
      this.serviceContracts = data.data;
      console.log(data.data);
    });
  }
}
