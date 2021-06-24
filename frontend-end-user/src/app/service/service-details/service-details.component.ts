import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProviderService } from 'src/app/shared/models/ProviderService';
import { ServiceContract } from 'src/app/shared/models/ServiceContract';
import { ServiceStatus } from 'src/app/shared/models/ServiceStatus';
import { GeneralService } from 'src/app/shared/services/general.service';
import { Service } from '../service';


@Component({
  selector: 'app-service-details',
  templateUrl: './service-details.component.html',
  styleUrls: ['./service-details.component.css'],
  host: { 'class': 'w-full flex flex-column flex-wrap' }
})

export class ServiceDetailsComponent implements OnInit {

  contract: ServiceContract = {};
  user_rate = 0;
  id: number;

  // Service or contract?
  service: boolean;

  constructor(public router: Router, private route: ActivatedRoute, private generalService: GeneralService) {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    // Is service or contract?
    this.service = (this.router.url.split('?')[0]).indexOf("/contracts")<0;
    // Get contract
    if (!this.service) {
      // By contract API
      this.generalService.getContract(this.id).then(data => {
        console.log("GOT CONTRACT");
        console.log(data);
        this.contract = data;
        this.user_rate = this.contract.review;
      });
    } else {
      // By service API and create fake contract with other fields null
      this.generalService.getService(this.id).then(data => {
        console.log("GOT SERVICE");
        console.log(data);
        this.contract = {
          id: 0,
          providerService: data,
          client: null,
          businessService: {
            price: 0,
            service: {
              hasExtras: false
            }
          },
          status: ServiceStatus.WAITING,
          review: 0,
        }
      });
    }
  }

  ngOnInit(): void {
  }

  rate(rate: number): void {
    if (this.contract.review == 0) {
      this.user_rate = rate;
    }
  }

  submitReview() {
    this.contract.review = this.user_rate;
    this.generalService.updateContract(this.id, this.contract).then(data => {
      console.log("SUBMITTED");
      console.log(data);
      this.contract = data;
      this.user_rate = this.contract.review;
    });
  }
}
