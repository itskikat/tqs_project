/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import { Component, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'ngx-app',
  template: '<router-outlet></router-outlet>',
})
export class AppComponent implements OnInit {

  constructor() {
  }

  private stompClient = null;


  connect() {
    const socket = new SockJS('http://deti-tqs-14.ua.pt:8080/notification/');
    this.stompClient = Stomp.over(socket);

    const _this = this;
    var headers = {
      // additional header
      "Access-Control-Allow-Origin":"http://deti-tqs-14.ua.pt:8080",
    };

    this.stompClient.connect(headers, function (frame) {
      console.log('Connected: ' + frame);

      _this.stompClient.subscribe('/contract', function (message) {
        let email=localStorage.getItem("email");
        let info = message.body.toString().split(":");

        if(info[0]==email){
          if(info[1]=="W"){
            alert("There is a new service contract!");
          }
          else{
            alert("A service contract was accepted!");
          }
        }
      });
    });
  }

  ngOnInit(): void {
    this.connect();
  }
}
