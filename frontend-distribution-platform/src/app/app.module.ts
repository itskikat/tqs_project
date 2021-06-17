/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { CoreModule } from './@core/core.module';
import { ThemeModule } from './@theme/theme.module';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  NbChatModule,
  NbDatepickerModule,
  NbDialogModule,
  NbMenuModule,
  NbSidebarModule,
  NbToastrModule,
  NbWindowModule,
  NbCardModule,
  NbProgressBarModule,
  NbStepperModule,
  NbLayoutModule,
  NbButtonModule,
  NbInputModule,
  NbSelectModule,
  NbUserModule,
  NbIconModule,
  NbTabsetModule,
  NbListModule,
  NbAlertModule,
  NbPopoverModule,
  NbCheckboxModule,
  NbTagModule,
  NbAutocompleteModule,
} from '@nebular/theme';
import { RegisterProviderComponent } from './register-provider/register-provider.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProviderServiceListComponent } from './provider/provider-service-list/provider-service-list.component';
import { BusinessStatsComponent } from './business/business-stats/business-stats.component';
import { BusinessStatsProgressSection } from './business/business-stats/progress-section/progress-section.component';
import { BusinessStatsCharts } from './business/business-stats/charts-panel/charts-panel.component';
import { BusinessStatsChartsProfitChartComponent } from './business/business-stats/charts-panel/charts/profit-chart.component';
import { BusinessStatsChartsOrdersChartComponent } from './business/business-stats/charts-panel/charts/orders-chart.component';
import { BusinessStatsChartsChartPanelSummaryComponent } from './business/business-stats/charts-panel/chart-panel-summary/chart-panel-summary.component';
import { BusinessStatsChartsChartPanelHeaderComponent } from './business/business-stats/charts-panel/chart-panel-header/chart-panel-header.component';
import { BusinessStatsChartsLegendChartComponent } from './business/business-stats/charts-panel/legend-chart/legend-chart.component';
import { ChartModule } from 'angular2-chartjs';
import { NgxEchartsModule } from 'ngx-echarts';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { RegisterBusinessComponent } from './register-business/register-business.component';
import { ProviderServiceFormComponent } from './provider/provider-service-form/provider-service-form.component';
import { ProviderProfileComponent } from './provider/provider-profile/provider-profile.component';
import { ProviderRequestsListComponent } from './provider/provider-requests-list /provider-requests-list.component';
import { ProviderStatsComponent } from './provider/provider-stats/provider-stats.component';
import { ProviderStatsCharts} from './provider/provider-stats/charts-panel/charts-panel.component';
import { ProviderStatsProgressSection } from './provider/provider-stats/progress-section/progress-section.component';
import { ProviderStatsChartsProfitChartComponent } from './provider/provider-stats/charts-panel/charts/profit-chart.component';
import { ProviderStatsChartsOrdersChartComponent } from './provider/provider-stats/charts-panel/charts/orders-chart.component';
import { ProviderStatsChartsChartPanelSummaryComponent } from './provider/provider-stats/charts-panel/chart-panel-summary/chart-panel-summary.component';
import { ProviderStatsChartsChartPanelHeaderComponent } from './provider/provider-stats/charts-panel/chart-panel-header/chart-panel-header.component';
import { ProviderStatsChartsLegendChartComponent } from './provider/provider-stats/charts-panel/legend-chart/legend-chart.component';
import { BusinessProfileComponent } from './business/business-profile/business-profile.component';
import { BusinessAPIComponent } from './business/business-api/business-api.component';
import { SharedModule } from './shared/shared.module';
import { BusinessServiceComponent } from './business/business-service/business-service.component';
import { BusinessServiceFormComponent } from './business/business-service-form/business-service-form.component';
import { BusinessServiceEditFormComponent } from './business/business-service-edit-form/business-service-edit-form.component';

@NgModule({
  declarations: [
    AppComponent, 
    RegisterProviderComponent, 
    ProviderServiceListComponent,
    ProviderRequestsListComponent,
    RegisterBusinessComponent,
    ProviderServiceFormComponent,
    BusinessStatsComponent,
    BusinessStatsProgressSection,
    BusinessStatsCharts,
    BusinessStatsChartsProfitChartComponent,
    BusinessStatsChartsOrdersChartComponent,
    BusinessStatsChartsChartPanelSummaryComponent,
    BusinessStatsChartsChartPanelHeaderComponent,
    BusinessStatsChartsLegendChartComponent,
    BusinessProfileComponent,
    BusinessAPIComponent,
    ProviderProfileComponent,
    ProviderStatsCharts,
    ProviderStatsChartsChartPanelHeaderComponent,
    ProviderStatsChartsChartPanelSummaryComponent,
    ProviderStatsChartsLegendChartComponent,
    ProviderStatsChartsOrdersChartComponent,
    ProviderStatsChartsProfitChartComponent,
    ProviderStatsComponent,
    ProviderStatsProgressSection,
    BusinessServiceComponent,
    BusinessServiceFormComponent,
    BusinessServiceEditFormComponent
  ],
  imports: [
    BrowserModule,
    NbAutocompleteModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    NbCardModule,
    NbStepperModule,
    NbSidebarModule.forRoot(),
    NbMenuModule.forRoot(),
    NbDatepickerModule.forRoot(),
    NbDialogModule.forRoot(),
    NbWindowModule.forRoot(),
    NbToastrModule.forRoot(),
    NbChatModule.forRoot({
      messageGoogleMapKey: 'AIzaSyA_wNuCzia92MAmdLRzmqitRGvCF7wCZPY',
    }),
    CoreModule.forRoot(),
    ThemeModule.forRoot(),
    NgbModule,
    FormsModule,
    NbPopoverModule,
    ReactiveFormsModule,
    NbLayoutModule,
    NbButtonModule,
    NbInputModule,
    NbProgressBarModule,
    NbSelectModule,
    ThemeModule,
    NbCardModule,
    NbUserModule,
    NbButtonModule,
    NbIconModule,
    NbTabsetModule,
    NbSelectModule,
    NbListModule,
    ChartModule,
    NbProgressBarModule,
    NgxEchartsModule,
    NgxChartsModule,
    LeafletModule,
    NbCheckboxModule,
    NbAlertModule,
    NbTagModule,
    NbUserModule,
    // Authentication
    SharedModule
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
