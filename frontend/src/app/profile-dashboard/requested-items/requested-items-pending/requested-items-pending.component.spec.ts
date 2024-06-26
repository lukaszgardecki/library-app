import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestedItemsPendingComponent } from './requested-items-pending.component';

describe('RequestedItemsPendingComponent', () => {
  let component: RequestedItemsPendingComponent;
  let fixture: ComponentFixture<RequestedItemsPendingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RequestedItemsPendingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RequestedItemsPendingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
