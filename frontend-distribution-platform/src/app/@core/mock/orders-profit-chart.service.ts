import { of as observableOf,  Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { OrdersChart, OrdersChartData } from '../data/orders-chart';
import { OrderProfitChartSummary, OrdersProfitChartData } from '../data/orders-profit-chart';
import { ProfitChart, ProfitChartData } from '../data/profit-chart';

@Injectable()
export class OrdersProfitChartService extends OrdersProfitChartData {

  private summary = [
    {
      title: 'Marketplace',
      value: 3654,
    },
    {
      title: 'Last Month',
      value: 946,
    },
    {
      title: 'Last Week',
      value: 654,
    },
    {
      title: 'Today',
      value: 230,
    },
  ];

  private summary_provider= [
    {
      title: 'Marketplace',
      value: 3654,
    },
    {
      title: 'Last Month',
      value: 946,
    },
    {
      title: 'Last Week',
      value: 654,
    },
    {
      title: 'Today',
      value: 230,
    },
  ];

  constructor(private ordersChartService: OrdersChartData,
              private profitChartService: ProfitChartData) {
    super();
  }

  getOrderProfitChartSummary(provider?: boolean): Observable<OrderProfitChartSummary[]> {
    if(provider){
      return observableOf(this.summary_provider);
    }
    return observableOf(this.summary);
  }

  getOrdersChartData(period: string, provider?: boolean): Observable<OrdersChart> {
    if(provider){
      return observableOf(this.ordersChartService.getOrdersChartData(period,true));
    }
    return observableOf(this.ordersChartService.getOrdersChartData(period));
  }

  getProfitChartData(period: string, provider?:boolean): Observable<ProfitChart> {
    if(provider){
      return observableOf(this.profitChartService.getProfitChartData(period, true));
    }
    return observableOf(this.profitChartService.getProfitChartData(period));
  }
}
