import { Component, OnInit } from "@angular/core";

@Component({
  selector: "service-index",
  templateUrl: "./index.component.html",
  host: {'class': 'w-full flex flex-column flex-wrap'}
})
export class ServiceIndexComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
