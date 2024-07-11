import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeesNotAccountedComponent } from './fees-not-accounted.component';

describe('FeesNotAccountedComponent', () => {
  let component: FeesNotAccountedComponent;
  let fixture: ComponentFixture<FeesNotAccountedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FeesNotAccountedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FeesNotAccountedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
