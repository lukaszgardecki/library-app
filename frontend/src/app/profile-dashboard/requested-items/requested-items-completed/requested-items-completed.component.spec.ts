import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestedItemsCompletedComponent } from './requested-items-completed.component';

describe('RequestedItemsCompletedComponent', () => {
  let component: RequestedItemsCompletedComponent;
  let fixture: ComponentFixture<RequestedItemsCompletedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RequestedItemsCompletedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RequestedItemsCompletedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
