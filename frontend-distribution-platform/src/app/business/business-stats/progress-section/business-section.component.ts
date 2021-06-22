import { Component, OnDestroy } from '@angular/core';
import {BusinessServiceService} from '../../../shared/services/business-service.service';
import { DatePipe } from '@angular/common';

export interface ProgressInfo {
  title: string;
  value: any;
}

@Component({
  selector: 'ngx-business-stats-progress-section',
  styleUrls: ['./progress-section.component.scss'],
  templateUrl: './business-section.component.html',
})
export class BusinessStatsProgressSection implements OnDestroy {

  private alive = true;

  selectedTimespan = "week";
  timespanOptions = [
    {
      id: 'week',
      name: 'Week'
    },
    {
      id: 'month',
      name: 'Month'
    },
    {
      id: 'year',
      name: 'Year'
    }
  ]
  progressInfoData: ProgressInfo[] = [];

  constructor(private providerServiceService: BusinessServiceService, public datepipe: DatePipe) {
    this.getData();
  }

  getData(){
    let end = new Date();
    end.setDate(end.getDate() +1)
    let start =new Date();
    if(this.selectedTimespan=="week"){
      
      start.setDate(start.getDate() - 7);
    }
    if(this.selectedTimespan=="month"){
      
      start.setMonth(start.getMonth() - 1);
    }
    if(this.selectedTimespan=="year"){
      start.setFullYear(start.getFullYear() - 1);
    }
    this.providerServiceService.getBusinessStatistics(this.datepipe.transform(start, 'dd/MM/yyyy'), this.datepipe.transform(end, 'dd/MM/yyyy')).subscribe(data=>{
      this.progressInfoData=[];
      this.progressInfoData.push({title:'Profit', value: data.PROFIT });
      this.progressInfoData.push({title:'Number of contracts', value: data.TOTAL_CONTRACTS });
      this.progressInfoData.push({title:'Service with most profit', value: data.MOST_REQUESTED_SERVICETYPE.name});
    });
  }

  ngOnDestroy() {
    this.alive = true;
  }
}
