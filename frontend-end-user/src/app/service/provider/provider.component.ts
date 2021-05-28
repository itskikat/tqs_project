import { Component, OnInit } from "@angular/core";

interface Provider {
  name: string,
  id: number
}

interface Service {
  id: number,
  name: string,
  icon: string,
  area: string,
  price: number,
  color: string,
  description: string, 
  hasExtras: boolean,
  provider: Provider,
  category: string
}

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
    category: "Water shortages"
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
    category: "Bathroom"
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
    category: "Tap instalation"
  }
];

@Component({
  selector: "service-provider",
  templateUrl: "./provider.component.html",
})
export class ServiceProviderComponent implements OnInit {
  
  services: Service[];

  constructor() {
    this.services = servicesList;
  }

  ngOnInit(): void {}
}
