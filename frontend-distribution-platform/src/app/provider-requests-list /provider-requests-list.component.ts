import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MENU_ITEMS } from '../provider-menu';

interface Service {
  name: string,
}

interface Client {
  name: string,
  servicesRequested: number 
}

interface Request {
  moment: Date,
  address: string,
  client: Client
  service: Service,
  done: boolean,
  accepted: boolean,
  minutesAgo: number,
  collected: number
}
 
@Component({
  selector: 'ngx-provider-requests-list',
  templateUrl: './provider-requests-list.component.html',
  styleUrls: ['./provider-requests-list.component.scss']
})
export class ProviderRequestsListComponent implements OnInit {
  
  // DATA
  menu=MENU_ITEMS;

  requests: Request[] = [
    {
      moment: new Date(),
      address: 'R. Mário Sacramento 149a, 3810-106 Aveiro',
      client: {
        name: 'Kate Psychologist',
        servicesRequested: 6
      },
      service: {
        name: 'Service 1',
      },
      done: false,
      accepted: false,
      minutesAgo: 3,
      collected: -1
    },
    {
      moment: new Date(),
      address: 'R. da Estação 169, 3810-167 Aveiro',
      client: {
        name: 'Some Person',
        servicesRequested: 3
      },
      service: {
        name: 'Service 3',
      },
      done: false,
      accepted: true,
      minutesAgo: 10,
      collected: -1
    },
    {
      moment: new Date(),
      address: 'R. Nova 7, 3810-368 Aveiro',
      client: {
        name: 'Mario Ferreira',
        servicesRequested: 1
      },
      service: {
        name: 'Service 1',
      },
      done: true,
      accepted: true,
      minutesAgo: -1,
      collected: 36
    }
  ] 

  options=[{id:'price', name:'price'},{id:'aaaa', name:'aaaa'}];
  selected= this.options[0].id;

  constructor(public router: Router ) { }

  ngOnInit(): void {
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
}
