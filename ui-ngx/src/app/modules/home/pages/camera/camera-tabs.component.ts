import { Component, OnInit } from '@angular/core';
import {EntityTabsComponent} from "@home/components/entity/entity-tabs.component";
import {DeviceInfo} from "@shared/models/device.models";
import {Store} from "@ngrx/store";
import {AppState} from "@core/core.state";

@Component({
  selector: 'tb-camera-tabs',
  templateUrl: './camera-tabs.component.html',
  styleUrls: ['./camera-tabs.component.scss']
})
export class CameraTabsComponent extends EntityTabsComponent<DeviceInfo> {

  constructor(protected store: Store<AppState>) {
    super(store);
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
