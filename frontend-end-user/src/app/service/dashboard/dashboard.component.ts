import { Component, OnInit } from "@angular/core";
import { Service } from "../service";
import { Provider } from "../provider";
import { Router } from "@angular/router";
import { GeneralService } from "src/app/shared/services/general.service";
import { BusinessService } from "src/app/shared/models/BusinessService";
import { ProviderService } from "src/app/shared/models/ProviderService";



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
    number_reviews: 28
  },
  {
    id: 2,
    name: 'Pool cleaning',
    icon: 'fas fa-swimming-pool',
    area: 'Portugal mainland',
    price: 70,
    color: 'bg-lightBlue-500',
    description: 'Loren ipsum...',
    hasExtras: false,
    provider: {
      name: 'Pool services, Lda.',
      id: 2
    },
    category: "Swimming pool maintenance",
    rate: 4.5,
    number_reviews: 13
  },
  {
    id: 3,
    name: 'Water drill study',
    icon: 'fas fa-water',
    area: 'Portugal North Region',
    price: 150,
    color: 'bg-lightBlue-400',
    description: 'Loren ipsum...',
    hasExtras: false,
    provider: {
      name: 'Poll services, Lda.',
      id: 2
    },
    category: "Swimming pool maintenance",
    rate: 3.9,
    number_reviews: 20
  },
  /*
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
    rate: 2.1,
    number_reviews: 15
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
    rate: 3.0,
    number_reviews: 89
  },
  */
  
];

@Component({
  selector: "service-dashboard",
  templateUrl: "./dashboard.component.html",
  host: {'class': 'w-full flex flex-column flex-wrap'}
})
export class ServiceDashboardComponent implements OnInit {

  services: Service[];
  searched: boolean=false;
  
  // Matchine
  clientName: String;
  serviceTypes: BusinessService[];
  serviceTypeSelected: number;
  serviceType: BusinessService;
  matches: ProviderService[];
  signed: boolean = false;

  constructor(public router: Router, private generalService: GeneralService) {
    this.services = servicesList;
    this.clientName = localStorage.getItem("name").split(" ")[0];
  }

  ngOnInit(): void {
    // Get services from API
    this.generalService.getServices().then(data => {
      console.log("GOT SERVICES", data);
      this.serviceTypes = data;
      this.serviceTypeSelected = data[0]['service']['id'];
    });
  }

  details(id: number): void{
    this.router.navigate(["/services/"+ id.toString()])
  }

  search() {
    this.searched = true;
    // Get matches from API
    this.generalService.match(this.serviceTypeSelected).then(data => {
      console.log("GOT MATCHES", data);
      this.matches = data;
      this.serviceType = this.serviceTypes.filter(st => st.id==this.serviceTypeSelected)[0]
    });
  }

  searchRevert() {
    this.searched = false;
    this.matches = [];
  }

  contract(businessService: number) {
    console.log("Business service", businessService);
    console.log("Provider service", this.serviceTypeSelected);
    this.generalService.signContract(businessService, this.serviceTypeSelected).then(data => {
      console.log("SIGNED");
      console.log(data);
      this.signed = true;
      this.matches = [];
    });

  }
}
