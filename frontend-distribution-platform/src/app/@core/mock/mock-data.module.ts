import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PeriodsService } from './periods.service';
import { OrdersChartService } from './orders-chart.service';
import { ProfitChartService } from './profit-chart.service';
import { OrdersProfitChartService } from './orders-profit-chart.service';
import { ProfitBarAnimationChartService } from './profit-bar-animation-chart.service';
import { StatsBarService } from './stats-bar.service';
import { StatsProgressBarService } from './stats-progress-bar.service';


const SERVICES = [
  OrdersChartService,
  ProfitChartService,
  OrdersProfitChartService,
  ProfitBarAnimationChartService,
  StatsBarService,
  StatsProgressBarService,
  PeriodsService
];

@NgModule({
  imports: [
    CommonModule,
  ],
  providers: [
    ...SERVICES,
  ],
})
export class MockDataModule {
  static forRoot(): ModuleWithProviders<MockDataModule> {
    return {
      ngModule: MockDataModule,
      providers: [
        ...SERVICES,
      ],
    };
  }
}
