import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestedItemsUnsentComponent } from './requested-items-unsent.component';

describe('RequestedItemsUnsentComponent', () => {
  let component: RequestedItemsUnsentComponent;
  let fixture: ComponentFixture<RequestedItemsUnsentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RequestedItemsUnsentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RequestedItemsUnsentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
