import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardBookItemComponent } from './card-book-item.component';

describe('CardBookItemComponent', () => {
  let component: CardBookItemComponent;
  let fixture: ComponentFixture<CardBookItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardBookItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardBookItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
