import { Component, OnInit } from "@angular/core";

@Component({
  selector: "service-login",
  templateUrl: "./login.component.html",
  host: {'class': 'w-full flex flex-column flex-wrap'}
})
export class ServiceLoginComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  registerProvider() {
    alert("This will be a redirect to the core service website register form! :)");
  }
}
