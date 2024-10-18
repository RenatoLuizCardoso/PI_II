import { TestBed } from '@angular/core/testing';

import { CcursoService } from './ccurso.service';

describe('CcursoService', () => {
  let service: CcursoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CcursoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
