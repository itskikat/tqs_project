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
  NbSelectModule
} from '@nebular/theme';
import { LoginComponent } from './login/login.component';
import { RegisterProviderComponent } from './register-provider/register-provider.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProviderServiceListComponent } from './provider-service-list/provider-service-list.component';
import { BusinessStatsComponent } from './business/business-stats/business-stats.component';
import { BusinessStatsProgressSection } from './business/business-stats/progress-section/progress-section.component';

@NgModule({
  declarations: [
    AppComponent, 
    LoginComponent, 
    RegisterProviderComponent, 
    ProviderServiceListComponent,
    BusinessStatsComponent,
    BusinessStatsProgressSection
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
    ReactiveFormsModule,
    NbLayoutModule,
    NbButtonModule,
    NbInputModule,
    NbProgressBarModule,
    NbSelectModule
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
