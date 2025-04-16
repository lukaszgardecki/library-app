import { TestBed } from '@angular/core/testing';

import { BookItemRequestService } from './book-item-request.service';

describe('BookItemRequestService', () => {
  let service: BookItemRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookItemRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
