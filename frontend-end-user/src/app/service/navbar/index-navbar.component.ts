import { Component, OnInit } from "@angular/core";

@Component({
  selector: "service-navbar",
  templateUrl: "./index-navbar.component.html",
})
export class ServiceNavbarComponent implements OnInit {
  navbarOpen = false;

  constructor() {}

  ngOnInit(): void {}

  setNavbarOpen() {
    this.navbarOpen = !this.navbarOpen;
  }
}
