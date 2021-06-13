import { ExtraOptions, RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import {RegisterProviderComponent} from './register-provider/register-provider.component';
import {ProviderServiceListComponent} from './provider-service-list/provider-service-list.component';
import {ProviderServiceFormComponent} from './provider-service-form/provider-service-form.component';
import {ProviderStatsComponent} from './provider-stats/provider-stats.component';
import { RegisterBusinessComponent } from './register-business/register-business.component';
import { BusinessStatsComponent } from './business/business-stats/business-stats.component';
import { ProviderProfileComponent } from './provider-profile/provider-profile.component';
import { ProviderRequestsListComponent } from './provider-requests-list /provider-requests-list.component';
import { BusinessProfileComponent } from './business/business-profile/business-profile.component';
import { BusinessAPIComponent } from './business/business-api/business-api.component';


export const routes: Routes = [
  {
    path: 'loginoriginal', component: LoginComponent
  },
  {
    path:'services/add', component: ProviderServiceFormComponent
  },
  {
    path:'services', component: ProviderServiceListComponent
  },
  {
    path:'requests', component: ProviderRequestsListComponent
  },
  {
    path: 'provider/stats', component: ProviderStatsComponent
  },
  {
    path:'business/stats', component: BusinessStatsComponent
  },
  {
    path:'business/profile', component: BusinessProfileComponent
  },
  {
    path:'business/api', component: BusinessAPIComponent
  },
  {
    path: 'regist/provider', component: RegisterProviderComponent
  },
  {
    path: 'profile/provider', component: ProviderProfileComponent
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
