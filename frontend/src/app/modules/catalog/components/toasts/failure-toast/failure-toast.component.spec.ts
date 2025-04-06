import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FailureToastComponent } from './failure-toast.component';

describe('FailureToastComponent', () => {
  let component: FailureToastComponent;
  let fixture: ComponentFixture<FailureToastComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FailureToastComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FailureToastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
