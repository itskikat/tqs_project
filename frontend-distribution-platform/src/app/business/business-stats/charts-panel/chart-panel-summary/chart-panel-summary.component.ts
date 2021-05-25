import { Component, Input } from '@angular/core';

@Component({
  selector: 'ngx-business-stats-charts-chart-panel-summary',
  styleUrls: ['./chart-panel-summary.component.scss'],
  template: `
    <div class="summary-container">
      <div *ngFor="let item of summary">
        <div>{{ item.title }}</div>
        <div class="h6">{{ item.value }}</div>
      </div>
    </div>
  `,
})
export class BusinessStatsChartsChartPanelSummaryComponent {
  @Input() summary: {title: string; value: number}[];
}

