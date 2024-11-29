import { TestBed } from '@angular/core/testing';

import { GradeFixaService } from './grade-fixa.service';

describe('GradeFixaService', () => {
  let service: GradeFixaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GradeFixaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
