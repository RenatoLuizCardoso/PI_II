import { TestBed } from '@angular/core/testing';

import { GradeFixaProfService } from './grade-fixa-prof.service';

describe('GradeFixaProfService', () => {
  let service: GradeFixaProfService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GradeFixaProfService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
