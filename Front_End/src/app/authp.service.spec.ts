import { TestBed } from '@angular/core/testing';

import { AuthpService } from './authp.service';

describe('AuthpService', () => {
  let service: AuthpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
