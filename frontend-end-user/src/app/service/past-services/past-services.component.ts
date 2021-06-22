import { Component, OnInit } from '@angular/core';
import { Service } from "../service";
import { Provider } from "../provider";
import { Router } from '@angular/router';
import { GeneralService } from 'src/app/shared/services/general.service';
import { ServiceContract } from 'src/app/shared/models/ServiceContract';
import { ServiceStatus } from 'src/app/shared/models/ServiceStatus';

@Component({
  selector: 'app-past-services',
  templateUrl: './past-services.component.html',
  styleUrls: ['./past-services.component.css'],
  host: {'class': 'w-full flex flex-column flex-wrap'}
})
export class PastServicesComponent implements OnInit {

  myServices: Service[];
  contracts: ServiceContract[];

  // Filters
  status = [
    {id: "ALL", name:"ALL"},
    {id:ServiceStatus.ACCEPTED , name:ServiceStatus.ACCEPTED},
    {id:ServiceStatus.FINNISHED , name:ServiceStatus.FINNISHED},
    {id:ServiceStatus.REJECTED , name:ServiceStatus.REJECTED},
    {id:ServiceStatus.WAITING , name:ServiceStatus.WAITING}
  ];
  statusSelected: string;
  order = [
    {id:'date_DESC', name:'Newest'},
    {id:'date_ASC', name:'Oldest'},
  ];
  orderSelected: string;

  // Pagination
  previous: boolean;
  next: boolean;
  currentPage: number = 0;
  
  constructor(public router: Router, private generalService: GeneralService) { }

  
  ngOnInit(): void {
    // Initialize filters
    this.statusSelected = this.status[0]['id'];
    this.orderSelected = this.order[0]['id'];
    // Get user contracts
    this.fetchContracts();
  }

  details(id: number): void{
    this.router.navigate(["/services/"+ id.toString()])
  }

  updatePage(page: number) {
    this.currentPage = page;
    this.fetchContracts();
  }

  fetchContracts() {
    this.generalService.getContracts(
      this.statusSelected,
      this.orderSelected.split("_")[0],
      this.orderSelected.split("_")[1],
      this.currentPage
    ).then(data => {
      this.contracts=data.data;
      console.log("GOT CONTRACTS");
      console.log(this.contracts);
      this.currentPage = data.currentPage;
      this.previous = this.currentPage!=0;
      this.next = (this.currentPage+1) < data.totalPages;
    });
  }
}
