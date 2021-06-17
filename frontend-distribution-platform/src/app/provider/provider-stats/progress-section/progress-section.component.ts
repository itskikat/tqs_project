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
  selector: 'ngx-provider-stats-progress-section',
  styleUrls: ['./progress-section.component.scss'],
  templateUrl: './progress-section.component.html',
})
export class ProviderStatsProgressSection implements OnDestroy {

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
      name: 'Provider'
    },
  ]
  progressInfoData: ProgressInfo[] = [
    {
      title: 'Profit',
      value: 130,
      activeProgress: 30,
      description: 'Better than average week (30%)',
    },
    {
      title: 'Number of calls',
      value: 10,
      activeProgress: 40,
      description: 'Better than average week (40%)',
    },
    {
      title: 'New custommers',
      value: 2,
      activeProgress: 10,
      description: 'Better than last week (10%)',
    },
  ];

  constructor() {
  }

  ngOnDestroy() {
    this.alive = true;
  }
}
