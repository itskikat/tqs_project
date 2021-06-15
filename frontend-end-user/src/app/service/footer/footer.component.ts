import { Component, OnInit } from "@angular/core";

@Component({
  selector: "service-footer",
  templateUrl: "./footer.component.html",
  host: {'class': 'w-full flex-self-end'}
})
export class ServiceFooterComponent implements OnInit {
  date = new Date().getFullYear();
  constructor() {}

  ngOnInit(): void {}
}
