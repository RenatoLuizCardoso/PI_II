import { TestBed } from '@angular/core/testing';

import { AuthaService } from './autha.service';

describe('AuthaService', () => {
  let service: AuthaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
