import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EntitiesTableComponent } from '../../components/entity/entities-table.component';
import {Authority} from "../../../../shared/models/authority.enum";
import {CameraTableConfigResolver} from "./camera-table-config.resolver";

const routes: Routes = [
  {
    path: 'camera',
    component: EntitiesTableComponent,
    data: {
      auth: [Authority.TENANT_ADMIN, Authority.CUSTOMER_USER],
      title: 'camera',
      devicesType: 'tenant',
      breadcrumb: {
        label: 'Camera',
        icon: 'devices_other'
      }
    },
    resolve: {
      entitiesTableConfig: CameraTableConfigResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [
    CameraTableConfigResolver
  ]
})
export class CameraRoutingModule { }
