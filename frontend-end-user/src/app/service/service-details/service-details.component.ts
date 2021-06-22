import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProviderService } from 'src/app/shared/models/ProviderService';
import { ServiceContract } from 'src/app/shared/models/ServiceContract';
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


  constructor(public router: Router, private route: ActivatedRoute, private generalService: GeneralService) {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    // Get contract from service
    this.generalService.getContract(this.id).then(data => {
      console.log(data);
      this.contract = data;
      this.user_rate = this.contract.review;
    });
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
