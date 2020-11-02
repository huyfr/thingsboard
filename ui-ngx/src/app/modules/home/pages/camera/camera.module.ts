import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CameraRoutingModule} from "./camera-routing.module";
import {SharedModule} from "../../../../shared/shared.module";
import {HomeComponentsModule} from "../../components/home-components.module";
import {HomeDialogsModule} from "../../dialogs/home-dialogs.module";
import { CameraComponent } from './camera.component';
import { CameraTabsComponent } from './camera-tabs.component';
import { CameraCredentialsDialogComponent } from './camera-credentials-dialog.component';

@NgModule({
  declarations: [
  CameraComponent,
  CameraTabsComponent,
  CameraCredentialsDialogComponent
  ],
  imports: [
    CommonModule,
    CameraRoutingModule,
    SharedModule,
    HomeComponentsModule,
    HomeDialogsModule,
  ]
})
export class CameraModule { }
