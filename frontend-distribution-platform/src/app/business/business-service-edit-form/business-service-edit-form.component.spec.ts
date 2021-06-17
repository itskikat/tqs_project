import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessServiceEditFormComponent } from './business-service-edit-form.component';

describe('ProviderServiceFormComponent', () => {
  let component: BusinessServiceEditFormComponent;
  let fixture: ComponentFixture<BusinessServiceEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BusinessServiceEditFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessServiceEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
