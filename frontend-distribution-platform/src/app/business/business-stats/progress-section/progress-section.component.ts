import { Component, OnDestroy } from '@angular/core';
import { StatsProgressBarData } from '../../../@core/data/stats-progress-bar';
import { takeWhile } from 'rxjs/operators';


export interface ProgressInfo {
  title: string;
  value: number;
  activeProgress: number;
  description: string;
}

@Component({
  selector: 'ngx-business-stats-progress-section',
  styleUrls: ['./progress-section.component.scss'],
  templateUrl: './progress-section.component.html',
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
    },
    {
      id: 'all',
      name: 'Business'
    },
  ]
  progressInfoData: ProgressInfo[] = [
    {
      title: 'Profit',
      value: 572900,
      activeProgress: 70,
      description: 'Better than average week (70%)',
    },
    {
      title: 'Sales',
      value: 6378,
      activeProgress: 30,
      description: 'Better than average week (30%)',
    },
    {
      title: 'New services',
      value: 200,
      activeProgress: 55,
      description: 'Better than last week (55%)',
    },
  ];

  constructor() {
  }

  ngOnDestroy() {
    this.alive = true;
  }
}
