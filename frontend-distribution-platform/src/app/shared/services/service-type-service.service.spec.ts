import { TestBed } from '@angular/core/testing';

import { ServiceTypeServiceService } from './service-type-service.service';

describe('ServiceTypeServiceService', () => {
  let service: ServiceTypeServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceTypeServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
