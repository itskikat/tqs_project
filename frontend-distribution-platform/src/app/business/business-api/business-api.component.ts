import { Component, OnInit } from '@angular/core';
import { MENU_ITEMS } from '../../business-menu';
import { Router } from '@angular/router';
import {BusinessServiceService} from '../../shared/services/business-service.service';

@Component({
  selector: 'ngx-business-api',
  templateUrl: './business-api.component.html',
  styleUrls: ['./business-api.component.scss']
})
export class BusinessAPIComponent implements OnInit {

  menu=MENU_ITEMS;
  tokenGenerated: boolean;
  token: String;

  constructor(public router: Router, private businessService: BusinessServiceService ) { }

  ngOnInit(): void {
    this.tokenGenerated = false;
  }

  generateToken() {
    this.businessService.generateToken().subscribe( data =>{ 
        this.token= data.key;
        this.tokenGenerated = true;
      }
    );
    
  }
}
