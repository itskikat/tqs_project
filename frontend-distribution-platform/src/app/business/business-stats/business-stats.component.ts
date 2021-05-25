import { Component, OnInit } from '@angular/core';
import { NbMenuItem } from '@nebular/theme';
import { MENU_ITEMS } from '../../business-menu';
 
@Component({
  selector: 'ngx-business-stats',
  templateUrl: './business-stats.component.html',
})
export class BusinessStatsComponent implements OnInit {

  menu=MENU_ITEMS
  constructor() { }

  ngOnInit(): void {
  }

}
