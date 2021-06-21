import { ExtraOptions, RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import {RegisterProviderComponent} from './register-provider/register-provider.component';
import {ProviderServiceListComponent} from './provider/provider-service-list/provider-service-list.component';
import {ProviderServiceFormComponent} from './provider/provider-service-form/provider-service-form.component';
import {ProviderStatsComponent} from './provider/provider-stats/provider-stats.component';
import { RegisterBusinessComponent } from './register-business/register-business.component';
import { ProviderProfileComponent } from './provider/provider-profile/provider-profile.component';
import { ProviderRequestsListComponent } from './provider/provider-requests-list /provider-requests-list.component';
import { BusinessProfileComponent } from './business/business-profile/business-profile.component';
import { BusinessAPIComponent } from './business/business-api/business-api.component';
import { ProviderGuard } from './shared/guard/provider.guard';
import { BusinessGuard } from './shared/guard/business.guard';
import { BusinessServiceComponent } from './business/business-service/business-service.component';
import {BusinessServiceFormComponent} from './business/business-service-form/business-service-form.component';
import {BusinessServiceEditFormComponent} from './business/business-service-edit-form/business-service-edit-form.component';
import { BusinessStatsComponent } from './business/business-stats/business-stats.component';


export const routes: Routes = [
  // Provider
  {
    path:'provider/services/add', component: ProviderServiceFormComponent, canActivate: [ProviderGuard]
  },
  {
    path:'provider/services/edit/:id', component: ProviderServiceFormComponent, canActivate: [ProviderGuard]
  },
  {
    path:'provider/services', component: ProviderServiceListComponent, canActivate: [ProviderGuard]
  },
  {
    path:'provider/requests', component: ProviderRequestsListComponent, canActivate: [ProviderGuard]
  },
  {
    path: 'provider/stats', component: ProviderStatsComponent, canActivate: [ProviderGuard]
  },
  {
    path: 'provider/profile', component: ProviderProfileComponent, canActivate: [ProviderGuard]
  },
  // Business
  {
    path:'business/stats', component: BusinessStatsComponent, canActivate: [BusinessGuard]
  },
  {
    path:'business/services', component: BusinessServiceComponent, canActivate: [BusinessGuard]
  },
  {
    path:'business/services/add', component: BusinessServiceFormComponent, canActivate: [BusinessGuard]
  },
  {
    path:'business/services/edit/:id', component: BusinessServiceEditFormComponent, canActivate: [BusinessGuard]
  },
  {
    path:'business/profile', component: BusinessProfileComponent, canActivate: [BusinessGuard]
  },
  {
    path:'business/api', component: BusinessAPIComponent, canActivate: [BusinessGuard]
  },
  // Public
  {
    path: 'regist/provider', component: RegisterProviderComponent
  },
  {
    path: 'regist/business', component: RegisterBusinessComponent
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  // { path: '**', redirectTo: 'login' },
];

const config: ExtraOptions = {
  useHash: false,
};

@NgModule({
  imports: [RouterModule.forRoot(routes, config)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
