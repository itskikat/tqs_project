import { Component, OnInit } from '@angular/core';
import { Service } from "../service";
import { Provider } from "../provider";
import { Router } from '@angular/router';
import { GeneralService } from 'src/app/shared/services/general.service';
import { ServiceContract } from 'src/app/shared/models/ServiceContract';
import { ServiceStatus } from 'src/app/shared/models/ServiceStatus';


var servicesList:Service[] = [
  {
    id: 1,
    name: 'Pipe broken',
    icon: 'fas fa-water',
    area: 'Aveiro',
    price: 30,
    color: 'bg-lightBlue-600',
    description: 'Loren ipsum...',
    hasExtras: true,
    provider: {
      name: 'Bob Dickard',
      id: 1
    },
    category: "Water shortages",
    rate: 4,
    number_reviews: 28,
    status: 'W',
    date: "2021-11-02"
  },
  {
    id: 4,
    name: 'Tap instalation',
    icon: 'fas fa-faucet',
    area: 'Aveiro Region',
    price: 10,
    color: 'bg-lightBlue-500',
    description: 'Loren ipsum...',
    hasExtras: true,
    provider: {
      name: 'Bob Dickard',
      id: 1
    },
    category: "Bathroom",
    rate: 4,
    number_reviews: 28,
    status: 'A',
    date: "2021-05-21"
  },
  {
    id: 5,
    name: 'Bathroom pipes maintenance',
    icon: 'fas fa-shower',
    area: 'Aveiro',
    price: 560,
    color: 'bg-lightBlue-600',
    description: 'Loren ipsum...',
    hasExtras: true,
    provider: {
      name: 'Bob Dickard',
      id: 1
    },
    category: "Tap instalation",
    rate: 4,
    number_reviews: 28,
    status: 'F',
    date: "2020-03-19"
  },
  {
    id: 5,
    name: 'Bathroom pipes maintenance',
    icon: 'fas fa-shower',
    area: 'Aveiro',
    price: 560,
    color: 'bg-lightBlue-600',
    description: 'Loren ipsum...',
    hasExtras: true,
    provider: {
      name: 'Bob Dickard',
      id: 1
    },
    category: "Tap instalation",
    rate: 4,
    number_reviews: 28,
    status: 'F',
    date: "2020-03-19"
  }
];


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
