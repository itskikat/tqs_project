import { Component, OnDestroy, ViewChild } from '@angular/core';
import { takeWhile } from 'rxjs/operators';

import { ProviderStatsChartsOrdersChartComponent } from './charts/orders-chart.component';
import { ProviderStatsChartsProfitChartComponent } from './charts/profit-chart.component';
import { OrdersChart } from '../../../@core/data/orders-chart';
import { ProfitChart } from '../../../@core/data/profit-chart';
import { OrdersProfitChartData } from '../../../@core/data/orders-profit-chart';

// INTERFACE
export interface OrderProfitChartSummary {
  title: string;
  value: number;
}


// COMPONENT
@Component({
  selector: 'ngx-provider-stats-charts',
  styleUrls: ['./charts-panel.component.scss'],
  templateUrl: './charts-panel.component.html',
})
export class ProviderStatsCharts implements OnDestroy {

  private alive = true;
  
  // Data
  chartPanelSummary: OrderProfitChartSummary[] = [
    {
      title: 'All services orders',
      value: 10,
    },
    {
      title: 'All service profit ',
      value: 209,
    },
    {
      title: 'Best services orders',
      value: 10,
    },
    {
      title: 'Best service profit ',
      value: 209,
    } 
  ];

  period: string = 'week';
  ordersChartData: OrdersChart;
  profitChartData: ProfitChart;

  @ViewChild('ordersChart', { static: true }) ordersChart: ProviderStatsChartsOrdersChartComponent;
  @ViewChild('profitChart', { static: true }) profitChart: ProviderStatsChartsProfitChartComponent;

  constructor(private ordersProfitChartService: OrdersProfitChartData) {
    this.ordersProfitChartService.getOrderProfitChartSummary()
      .pipe(takeWhile(() => this.alive))
      .subscribe((summary) => {
        // this.chartPanelSummary = summary;
      });

    this.getOrdersChartData(this.period);
    this.getProfitChartData(this.period);
  }

  setPeriodAndGetChartData(value: string): void {
    if (this.period !== value) {
      this.period = value;
    }

    this.getOrdersChartData(value);
    this.getProfitChartData(value);
  }

  changeTab(selectedTab) {
    if (selectedTab.tabTitle === 'Profit') {
      this.profitChart.resizeChart();
    } else {
      this.ordersChart.resizeChart();
    }
  }

  getOrdersChartData(period: string) {
    this.ordersProfitChartService.getOrdersChartData(period, true)
      .pipe(takeWhile(() => this.alive))
      .subscribe(ordersChartData => {
        this.ordersChartData = ordersChartData;
      });
  }

  getProfitChartData(period: string) {
    this.ordersProfitChartService.getProfitChartData(period,true)
      .pipe(takeWhile(() => this.alive))
      .subscribe(profitChartData => {
        this.profitChartData = profitChartData;
      });
  }

  ngOnDestroy() {
    this.alive = false;
  }
}
