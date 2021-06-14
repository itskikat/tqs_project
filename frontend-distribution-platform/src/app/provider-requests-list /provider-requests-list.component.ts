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

  serviceStatus= ServiceStatus;

  serviceContracts: ServiceContract[];

  options=[{id:'price', name:'price'},{id:'aaaa', name:'aaaa'}];
  selected= this.options[0].id;

  constructor(public router: Router, private serviceContractService: ServiceContractService) { }

  ngOnInit(): void {
    this.getContracts();
  }

  optionSelected() :void{

  }
  editService() :void{
    this.router.navigate(['/services/add']);
  }

  deleteService(): void{
  }

  addService(): void{
    this.router.navigate(['/services/add']);
  }
  
  getContracts(): void{
    this.serviceContractService.getServiceContracts("p").subscribe(data => {
      this.serviceContracts = data.data;
      console.log(data.data);
    });
  }
}
