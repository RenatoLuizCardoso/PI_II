import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { authpGuard } from './authp.guard';

describe('authpGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => authpGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
