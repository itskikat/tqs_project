import { Component, OnInit } from '@angular/core';
import { MENU_ITEMS } from '../../provider-menu';
 
@Component({
  selector: 'ngx-provider-stats',
  templateUrl: './provider-stats.component.html',
})
export class ProviderStatsComponent implements OnInit {

  menu=MENU_ITEMS
  constructor() { }

  ngOnInit(): void {
  }

}
