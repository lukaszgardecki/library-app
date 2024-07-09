import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowedItemsComponent } from './borrowed-items.component';

describe('BorrowedItemsComponent', () => {
  let component: BorrowedItemsComponent;
  let fixture: ComponentFixture<BorrowedItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BorrowedItemsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BorrowedItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
