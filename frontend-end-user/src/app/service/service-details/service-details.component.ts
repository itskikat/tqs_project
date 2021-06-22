import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProviderService } from 'src/app/shared/models/ProviderService';
import { ServiceContract } from 'src/app/shared/models/ServiceContract';
import { GeneralService } from 'src/app/shared/services/general.service';
import { Service } from '../service';


var service: Service =
{
  id: 1,
  name: 'Pipe broken',
  icon: 'fas fa-water',
  area: 'Aveiro',
  price: 30,
  color: 'bg-lightBlue-600',
  description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sagittis odio est. Sed non facilisis arcu. Vestibulum ultricies, leo sit amet ornare mollis, ligula lacus sodales purus, vel malesuada neque odio in turpis. Mauris fermentum tincidunt ligula, eu lacinia velit. Duis quis placerat velit, ac egestas purus. Cras eu mi neque. Nam finibus tempus nisl, ac interdum ipsum euismod eu. Aliquam convallis tortor mi. Aenean mollis fringilla quam, sit amet gravida felis elementum in. Pellentesque a varius velit. Nullam aliquam tellus in sapien finibus, in feugiat orci euismod. Etiam vitae interdum nisi, in fringilla elit.',
  hasExtras: true,
  provider: {
    name: 'Bob Dickard',
    id: 1
  },
  category: "Water shortages",
  rate: 4,
  number_reviews: 28,
  status: 'W',
  date: "2021-11-02"
};


@Component({
  selector: 'app-service-details',
  templateUrl: './service-details.component.html',
  styleUrls: ['./service-details.component.css'],
  host: { 'class': 'w-full flex flex-column flex-wrap' }
})

export class ServiceDetailsComponent implements OnInit {

  service: Service = service;
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
    
  }
}
