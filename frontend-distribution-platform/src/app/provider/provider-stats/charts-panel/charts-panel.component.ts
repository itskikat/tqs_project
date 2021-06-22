import { Component, OnDestroy, ViewChild } from '@angular/core';
import { takeWhile } from 'rxjs/operators';
import { EChartOption } from 'echarts';
import { ProviderServiceService } from '../../../shared/services/provider-service.service';
import { DatePipe } from '@angular/common';
import { AfterViewInit } from '@angular/core';
import { NbThemeService } from '@nebular/theme';

// INTERFACE
export interface OrderProfitChartSummary {
  title: string;
  value: any;
}

export interface OrdersChart {
  chartLabel: string[];
  linesData: number[];
}


// COMPONENT
@Component({
  selector: 'ngx-provider-stats-charts',
  styleUrls: ['./charts-panel.component.scss'],
  templateUrl: './charts-panel.component.html',
})
export class ProviderStatsCharts implements OnDestroy{

  private alive = true;
  
  // Data
  chartPanelSummary: OrderProfitChartSummary[] = [ ];

  period: string = 'week';
  eTheme: any;

  ordersChartData: OrdersChart={chartLabel:[],linesData:[]};
  profitChartData: OrdersChart={chartLabel:[],linesData:[]};
  option_order:EChartOption={};

  constructor(private theme: NbThemeService,private providerServiceService: ProviderServiceService, public datepipe: DatePipe ) {
    let dates: Date[]= this.getDates(this.period);
    this.getChartPannelSummary(dates);
    this.getChartData(dates);
    this.theme.getJsTheme()
      .pipe(
        takeWhile(() => this.alive),
      )
      .subscribe(config => {
        this.eTheme = config.variables.orders});

  }




  setPeriodAndGetChartData(value: string): void {
    if (this.period !== value) {
      this.period = value;
    }
    let dates: Date[]= this.getDates(this.period);
    this.getChartData(dates);

  }

  

  getChartPannelSummary(date: Date[]) {
    this.providerServiceService.getProviderStatistics(this.datepipe.transform(date[0], 'dd/MM/yyyy'), this.datepipe.transform(date[1], 'dd/MM/yyyy'))
      .pipe(takeWhile(() => this.alive))
      .subscribe(data => {
        this.chartPanelSummary=[];
        this.chartPanelSummary.push({title:'Profit', value: data.TOTAL_PROFIT });
        this.chartPanelSummary.push({title:'Number of contracts', value: data.TOTAL_FINISHED });
        this.chartPanelSummary.push({title:'Service with most profit', value: data.PROFIT_SERVICE.service.name });
        this.chartPanelSummary.push({title:'Service with most contracts', value: data.CONTRACTS_SERVICE.service.name });
      });
  }

  getChartData(date: Date[]) {
    this.providerServiceService.getProviderStatistics(this.datepipe.transform(date[0], 'dd/MM/yyyy'), this.datepipe.transform(date[1], 'dd/MM/yyyy'))
      .pipe(takeWhile(() => this.alive))
      .subscribe(data => {

        this.ordersChartData.chartLabel=Object.keys(data.CONTRACTS_HISTORY);
        this.ordersChartData.linesData=Object.values(data.CONTRACTS_HISTORY).map(Number);
        this.profitChartData.chartLabel=Object.keys(data.PROFIT_HISTORY);
        this.profitChartData.linesData=Object.values(data.PROFIT_HISTORY).map(Number);

        this.option_order= {
          grid: {
            left: 40,
            top: 40,
            right: 40,
            bottom: 40,
          },
          tooltip: {
            trigger: 'item',
            axisPointer: {
              type: 'line',
              lineStyle: {
                color: this.eTheme.tooltipLineColor,
                width: this.eTheme.tooltipLineWidth,
              },
            },
            textStyle: {
              color: this.eTheme.tooltipTextColor,
              fontSize: this.eTheme.tooltipFontSize,
              fontWeight: this.eTheme.tooltipFontWeight,
            },
            position: 'top',
            backgroundColor: this.eTheme.tooltipBg,
            borderColor: this.eTheme.tooltipBorderColor,
            borderWidth: 1,
            formatter: (params) => {
              return Math.round(parseInt(params.value, 10));
            },
            extraCssText: this.eTheme.tooltipExtraCss,
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            offset: 5,
            data: this.ordersChartData.chartLabel,
            axisTick: {
              show: false,
            },
            axisLabel: {
              color: this.eTheme.axisTextColor,
              fontSize: this.eTheme.axisFontSize,
            },
            axisLine: {
              lineStyle: {
                color: this.eTheme.axisLineColor,
                width: '2',
              },
            },
          },
          yAxis: {
            type: 'value',
            boundaryGap: false,
            axisLine: {
              lineStyle: {
                color: this.eTheme.axisLineColor,
                width: '1',
              },
            },
            axisLabel: {
              color: this.eTheme.axisTextColor,
              fontSize: this.eTheme.axisFontSize,
            },
            axisTick: {
              show: false,
            },
            splitLine: {
    
              lineStyle: {
                color: this.eTheme.yAxisSplitLine,
                width: '1',
              },
            },
          },
          series: [{
              data: this.ordersChartData.linesData,
              type: 'line',
              color: 'rgb(0, 214, 143)',
              smooth: true
          },
          {
            data: this.profitChartData.linesData,
            type: 'line',
            color: 'rgb(51, 102, 255)',
            smooth: true
        }
        ]
          
        };
      });
  }



  ngOnDestroy() {
    this.alive = false;
  }

  getDates(period): Date[]{
    let end = new Date();
    end.setDate(end.getDate() +1)
    let start =new Date();
    if(period=="week"){
      
      start.setDate(start.getDate() - 7);
    }
    if(period=="month"){
      
      start.setMonth(start.getMonth() - 1);
    }
    if(period=="year"){
      start.setFullYear(start.getFullYear() - 1);
    }
    return [start, end]
  }

  

}
