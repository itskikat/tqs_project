import { Component, OnInit } from '@angular/core';
import { MENU_ITEMS } from '../../business-menu';
import { Router } from '@angular/router';

@Component({
  selector: 'ngx-business-api',
  templateUrl: './business-api.component.html',
  styleUrls: ['./business-api.component.scss']
})
export class BusinessAPIComponent implements OnInit {

  menu=MENU_ITEMS;
  tokenGenerated: boolean;
  lastAPIGenerated: Date;

  constructor(public router: Router) { }

  ngOnInit(): void {
    this.tokenGenerated = false;
    this.lastAPIGenerated = new Date();
  }

  generateToken() {
    this.tokenGenerated = true;
  }
}
