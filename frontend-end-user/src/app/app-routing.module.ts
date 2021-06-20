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

const routes: Routes = [
  // Service
  { path: "", component: ServiceIndexComponent },
  { path: "services/:id", component: ServiceDetailsComponent },
  { path: "dashboard", component: ServiceDashboardComponent },
  { path: "provider", component: ServiceProviderComponent },
  { path: "past", component: PastServicesComponent  },
  { path: "login", component: ServiceLoginComponent  },
  { path: "register", component: ServiceRegisterComponent  },
  { path: "**", redirectTo: "", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
