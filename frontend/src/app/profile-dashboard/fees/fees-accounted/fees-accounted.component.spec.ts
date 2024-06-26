import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeesAccountedComponent } from './fees-accounted.component';

describe('FeesAccountedComponent', () => {
  let component: FeesAccountedComponent;
  let fixture: ComponentFixture<FeesAccountedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FeesAccountedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FeesAccountedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
