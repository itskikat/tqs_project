import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessServiceComponent } from './business-service.component';

describe('BusinessServiceComponent', () => {
  let component: BusinessServiceComponent;
  let fixture: ComponentFixture<BusinessServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BusinessServiceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
