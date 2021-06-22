import { Component, OnInit } from "@angular/core";
import { Service } from "../service";
import { ActivatedRoute, Router } from "@angular/router";
import { GeneralService } from "src/app/shared/services/general.service";
import { Provider } from "src/app/shared/models/Provider";

@Component({
  selector: "service-provider",
  templateUrl: "./provider.component.html",
  host: {'class': 'w-full flex flex-column flex-wrap'}
})
export class ServiceProviderComponent implements OnInit {
  
  services: Service[];
  id: number;
  provider: Provider = {
  
  };

  constructor(public router: Router, private route: ActivatedRoute, private generalService: GeneralService) {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    // Get contract from service
    this.generalService.getContract(this.id).then(data => {
      this.provider = data.providerService.provider;
      console.log(this.provider);
    });
  }

  ngOnInit(): void {}

  details(id: number): void{
    this.router.navigate(["/services/"+ id.toString()])
  }
}
