import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardInProgressComponent } from './card-in-progress.component';

describe('CardInProgressComponent', () => {
  let component: CardInProgressComponent;
  let fixture: ComponentFixture<CardInProgressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardInProgressComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardInProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
