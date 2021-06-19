import { Injectable } from '@angular/core';
import { ProfitChart, ProfitChartData } from '../../@core/data/profit-chart';

@Injectable()
export class ProfitChartService extends ProfitChartData {

  private year = [
    '2012',
    '2013',
    '2014',
    '2015',
    '2016',
    '2017',
    '2018',
  ];

  private data = { };
  private data_provider = { };

  constructor() {
    super();
    this.data = {
      week: this.getDataForWeekPeriod(),
      month: this.getDataForMonthPeriod(),
      year: this.getDataForYearPeriod(),
    };
    this.data_provider = {
      week: this.getProviderDataForWeekPeriod(),
      month: this.getProviderDataForMonthPeriod(),
      year: this.getProviderDataForYearPeriod(),
    };
  }

  private getDataForWeekPeriod(): ProfitChart {
    const nPoint = ["d","f"].length;

    return {
      chartLabel: ["d","f"],
      data: [
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
      ],
    };
  }

  private getDataForMonthPeriod(): ProfitChart {
    const nPoint = ["d","f"].length;

    return {
      chartLabel: ["d","f"],
      data: [
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
      ],
    };
  }

  private getDataForYearPeriod(): ProfitChart {
    const nPoint = this.year.length;

    return {
      chartLabel: this.year,
      data: [
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
      ],
    };
  }

  private getProviderDataForWeekPeriod(): ProfitChart {
    const nPoint = ["d","f"].length;

    return {
      chartLabel: ["d","f"],
      data: [
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
      ],
    };
  }

  private getProviderDataForMonthPeriod(): ProfitChart {
    const nPoint = ["d","f"].length;

    return {
      chartLabel: ["d","f"],
      data: [
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
      ],
    };
  }

  private getProviderDataForYearPeriod(): ProfitChart {
    const nPoint = this.year.length;

    return {
      chartLabel: this.year,
      data: [
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
        this.getRandomData(nPoint),
      ],
    };
  }

  private getRandomData(nPoints: number): number[] {
    return Array.from(Array(nPoints)).map(() => {
      return Math.round(Math.random() * 500);
    });
  }

  getProfitChartData(period: string, provider?:boolean): ProfitChart {
    if(provider){
      return this.data_provider[period];
    }
    return this.data[period];
  }
}
