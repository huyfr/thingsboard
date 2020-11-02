import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CameraTabsComponent } from './camera-tabs.component';

describe('CameraTabsComponent', () => {
  let component: CameraTabsComponent;
  let fixture: ComponentFixture<CameraTabsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CameraTabsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CameraTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
