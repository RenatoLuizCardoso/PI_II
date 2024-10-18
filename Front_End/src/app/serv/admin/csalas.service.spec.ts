import { TestBed } from '@angular/core/testing';

import { CsalasService } from './csalas.service';

describe('CsalasService', () => {
  let service: CsalasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CsalasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
