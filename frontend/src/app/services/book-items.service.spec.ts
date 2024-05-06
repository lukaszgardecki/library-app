import { TestBed } from '@angular/core/testing';

import { BookItemsService } from './book-items.service';

describe('BookItemsService', () => {
  let service: BookItemsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookItemsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
