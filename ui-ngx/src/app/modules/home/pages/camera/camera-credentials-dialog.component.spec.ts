import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CameraCredentialsDialogComponent } from './camera-credentials-dialog.component';

describe('CameraCredentialsDialogComponent', () => {
  let component: CameraCredentialsDialogComponent;
  let fixture: ComponentFixture<CameraCredentialsDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CameraCredentialsDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CameraCredentialsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
