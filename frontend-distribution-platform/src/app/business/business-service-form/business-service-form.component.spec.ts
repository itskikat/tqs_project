import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessServiceFormComponent } from './business-service-form.component';

describe('ProviderServiceFormComponent', () => {
  let component: BusinessServiceFormComponent;
  let fixture: ComponentFixture<BusinessServiceFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BusinessServiceFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessServiceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
