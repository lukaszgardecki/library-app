import { TestBed } from '@angular/core/testing';

import { LoanService } from './book-item-loan.service';

describe('LendingService', () => {
  let service: LoanService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
