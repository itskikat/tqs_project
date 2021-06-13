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
import { ProviderProfileComponent } from './provider-profile/provider-profile.component';
import { ProviderRequestsListComponent } from './provider-requests-list /provider-requests-list.component';
import { ProviderStatsComponent } from './provider-stats/provider-stats.component';
import { ProviderStatsCharts} from './provider-stats/charts-panel/charts-panel.component';
import { ProviderStatsProgressSection } from './provider-stats/progress-section/progress-section.component';
import { ProviderStatsChartsProfitChartComponent } from './provider-stats/charts-panel/charts/profit-chart.component';
import { ProviderStatsChartsOrdersChartComponent } from './provider-stats/charts-panel/charts/orders-chart.component';
import { ProviderStatsChartsChartPanelSummaryComponent } from './provider-stats/charts-panel/chart-panel-summary/chart-panel-summary.component';
import { ProviderStatsChartsChartPanelHeaderComponent } from './provider-stats/charts-panel/chart-panel-header/chart-panel-header.component';
import { ProviderStatsChartsLegendChartComponent } from './provider-stats/charts-panel/legend-chart/legend-chart.component';
import { BusinessProfileComponent } from './business/business-profile/business-profile.component';
import { BusinessAPIComponent } from './business/business-api/business-api.component';
import { AuthModule } from './auth/auth.module';
import { RouterModule } from '@angular/router';
import { SharedModule } from './shared/shared.module';

@NgModule({
  declarations: [
    AppComponent, 
    LoginComponent, 
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
    ProviderStatsProgressSection
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
    NbAlertModule,
    NbTagModule,
    NbUserModule,
    // Authentication
    AuthModule,
    SharedModule
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
