import { TestBed } from '@angular/core/testing';

import { ReservarService } from './reservar.service';

describe('ReservarService', () => {
  let service: ReservarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
