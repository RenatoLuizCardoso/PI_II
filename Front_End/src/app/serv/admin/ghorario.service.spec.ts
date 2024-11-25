import { TestBed } from '@angular/core/testing';

import { GhorarioService } from './ghorario.service';

describe('GhorarioService', () => {
  let service: GhorarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GhorarioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
