import { Component, OnInit } from '@angular/core';
import { Service } from "../service";
import { Provider } from "../provider";
import { Router } from '@angular/router';


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

  myServices: Service[]
  
  constructor(public router: Router) { }

  
  ngOnInit(): void {
    this.myServices=servicesList;
  }

  details(id: number): void{
    this.router.navigate(["/services/"+ id.toString()])
  }
}
