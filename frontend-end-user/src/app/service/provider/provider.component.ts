import { Component, OnInit } from "@angular/core";
import { Service } from "../service";
import { Provider } from "../provider";
import { Router } from "@angular/router";


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
    number_reviews: 28
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
    number_reviews: 28
  }
];

@Component({
  selector: "service-provider",
  templateUrl: "./provider.component.html",
  host: {'class': 'w-full flex flex-column flex-wrap'}
})
export class ServiceProviderComponent implements OnInit {
  
  services: Service[];

  constructor(public router: Router) {
    this.services = servicesList;
  }

  ngOnInit(): void {}

  details(id: number): void{
    this.router.navigate(["/services/"+ id.toString()])
  }
}
