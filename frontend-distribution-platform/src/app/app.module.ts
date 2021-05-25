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
} from '@nebular/theme';
import { LoginComponent } from './login/login.component';
import { RegisterProviderComponent } from './register-provider/register-provider.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProviderServiceListComponent } from './provider-service-list/provider-service-list.component';
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
import { ProviderServiceFormComponent } from './provider-service-form/provider-service-form.component';

@NgModule({
  declarations: [
    AppComponent, 
    LoginComponent, 
    RegisterProviderComponent, 
    ProviderServiceListComponent,
    RegisterBusinessComponent,
    ProviderServiceFormComponent,
    BusinessStatsComponent,
    BusinessStatsProgressSection,
    BusinessStatsCharts,
    BusinessStatsChartsProfitChartComponent,
    BusinessStatsChartsOrdersChartComponent,
    BusinessStatsChartsChartPanelSummaryComponent,
    BusinessStatsChartsChartPanelHeaderComponent,
    BusinessStatsChartsLegendChartComponent
  ],
  imports: [
    BrowserModule,
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
    NbAlertModule
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
