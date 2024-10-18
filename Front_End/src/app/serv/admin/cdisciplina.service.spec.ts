import { TestBed } from '@angular/core/testing';

import { CdisciplinaService } from './cdisciplina.service';

describe('CdisciplinaService', () => {
  let service: CdisciplinaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CdisciplinaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
