import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

// layouts
import { ServiceIndexComponent } from "./service/index/index.component";
import { ServiceProviderComponent } from "./service/provider/provider.component";
import { PastServicesComponent} from "./service/past-services/past-services.component";
import { ServiceDetailsComponent} from "./service/service-details/service-details.component";

// no layouts views
import { ServiceLoginComponent } from "./service/auth/login/login.component";
import { ServiceRegisterComponent } from "./service/auth/register/register.component";
import { ServiceDashboardComponent } from "./service/dashboard/dashboard.component";

// guards
import { ClientGuard } from './shared/guard/client.guard';

const routes: Routes = [
  // Service
  { path: "", component: ServiceIndexComponent },
  { path: "services/:id", component: ServiceDetailsComponent, canActivate: [ClientGuard] },
  { path: "dashboard", component: ServiceDashboardComponent, canActivate: [ClientGuard] },
  { path: "services/:id/provider", component: ServiceProviderComponent, canActivate: [ClientGuard] },
  { path: "past", component: PastServicesComponent, canActivate: [ClientGuard]  },
  { path: "profile", component: ServiceRegisterComponent, canActivate: [ClientGuard]  },
  { path: "login", component: ServiceLoginComponent  },
  { path: "register", component: ServiceRegisterComponent  },
  { path: "**", redirectTo: "", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
