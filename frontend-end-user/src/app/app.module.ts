import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";

// components for views and layouts

import { ServiceIndexComponent } from "./service/index/index.component";
import { ServiceNavbarComponent } from "./service/navbar/index-navbar.component";
import { ServiceFooterComponent } from "./service/footer/footer.component";
import { ServiceProviderComponent } from "./service/provider/provider.component";
import { PastServicesComponent } from './service/past-services/past-services.component';
import { ServiceDetailsComponent } from './service/service-details/service-details.component';
import { ServiceLoginComponent } from "./service/auth/login/login.component";
import { ServiceRegisterComponent } from "./service/auth/register/register.component";
import { ServiceDashboardComponent } from "./service/dashboard/dashboard.component";

import { SharedModule } from './shared/shared.module';

@NgModule({
  declarations: [
    AppComponent,
    // Service
    ServiceFooterComponent,
    ServiceNavbarComponent,
    ServiceIndexComponent,
    ServiceDashboardComponent,
    ServiceProviderComponent,
    PastServicesComponent,
    ServiceDetailsComponent,
    ServiceLoginComponent,
    ServiceRegisterComponent
  ],
  imports: [
    BrowserModule, 
    AppRoutingModule,
    // Authentication
    SharedModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
