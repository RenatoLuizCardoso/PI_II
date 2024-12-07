import { TestBed } from '@angular/core/testing';

import { ChoraService } from './chora.service';

describe('ChoraService', () => {
  let service: ChoraService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChoraService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
