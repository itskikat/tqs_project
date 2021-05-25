import { ExtraOptions, RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import {
  NbAuthComponent,
  NbLoginComponent,
  NbLogoutComponent,
  NbRegisterComponent,
  NbRequestPasswordComponent,
  NbResetPasswordComponent,
} from '@nebular/auth';
import { LoginComponent } from './login/login.component';
import {RegisterProviderComponent} from './register-provider/register-provider.component';
import {ProviderServiceListComponent} from './provider-service-list/provider-service-list.component';
import {ProviderServiceFormComponent} from './provider-service-form/provider-service-form.component';
import { RegisterBusinessComponent } from './register-business/register-business.component';


export const routes: Routes = [
  {
    path: 'pages',
    loadChildren: () => import('./pages/pages.module')
      .then(m => m.PagesModule),
  },
  {
    path: 'auth',
    component: NbAuthComponent,
    children: [
      {
        path: '',
        component: NbLoginComponent,
      },
      {
        path: 'login',
        component: NbLoginComponent,
      },
      {
        path: 'register',
        component: NbRegisterComponent,
      },
      {
        path: 'logout',
        component: NbLogoutComponent,
      },
      {
        path: 'request-password',
        component: NbRequestPasswordComponent,
      },
      {
        path: 'reset-password',
        component: NbResetPasswordComponent,
      },
      
    ],
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path:'services/add', component: ProviderServiceFormComponent
  },
  {
    path:'services', component: ProviderServiceListComponent
  },
  {
    path: 'regist/provider', component: RegisterProviderComponent
  },
  {
    path: 'regist/business', component: RegisterBusinessComponent
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'pages' },
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
