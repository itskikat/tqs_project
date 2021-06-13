import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { FormsModule } from '@angular/forms';
import { NbCardModule, NbLayoutModule } from '@nebular/theme';


@NgModule({
  declarations: [LoginComponent, RegisterComponent, ResetPasswordComponent],
  imports: [
    CommonModule,
    AuthRoutingModule,
    FormsModule,
    // Nebular
    NbLayoutModule,
    NbCardModule,
  ],
  exports: [
    LoginComponent, 
    RegisterComponent, 
    ResetPasswordComponent
  ]
})
export class AuthModule { }
