import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RenewableItemsComponent } from './renewable-items.component';

describe('RenewableItemsComponent', () => {
  let component: RenewableItemsComponent;
  let fixture: ComponentFixture<RenewableItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RenewableItemsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RenewableItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
