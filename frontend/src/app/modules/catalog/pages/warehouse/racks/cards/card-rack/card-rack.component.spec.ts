import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardRackComponent } from './card-rack.component';

describe('CardRackComponent', () => {
  let component: CardRackComponent;
  let fixture: ComponentFixture<CardRackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardRackComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardRackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
