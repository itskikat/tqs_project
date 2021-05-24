import { Component, OnInit } from '@angular/core';
import { NbMenuItem } from '@nebular/theme';
import { MENU_ITEMS } from '../provider-menu';
 
@Component({
  selector: 'ngx-provider-service-list',
  templateUrl: './provider-service-list.component.html',
  styleUrls: ['./provider-service-list.component.scss']
})
export class ProviderServiceListComponent implements OnInit {

  menu=MENU_ITEMS
  constructor() { }

  ngOnInit(): void {
  }

}
