import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MENU_ITEMS } from '../provider-menu';

 
@Component({
  selector: 'ngx-provider-service-list',
  templateUrl: './provider-service-list.component.html',
  styleUrls: ['./provider-service-list.component.scss']
})
export class ProviderServiceListComponent implements OnInit {
  
  menu=MENU_ITEMS;

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
