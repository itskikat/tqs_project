import { Component, OnInit } from '@angular/core';
import { MENU_ITEMS } from '../provider-menu';

@Component({
  selector: 'ngx-provider-profile',
  templateUrl: './provider-profile.component.html',
  styleUrls: ['./provider-profile.component.scss']
})
export class ProviderProfileComponent implements OnInit {

  menu=MENU_ITEMS
  constructor() { }

  ngOnInit(): void {
  }

}
