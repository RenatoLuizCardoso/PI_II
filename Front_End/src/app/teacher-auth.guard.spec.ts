import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { teacherAuthGuard } from './teacher-auth.guard';

describe('teacherAuthGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => teacherAuthGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
