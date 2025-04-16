import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardShelfComponent } from './card-shelf.component';

describe('CardShelfComponent', () => {
  let component: CardShelfComponent;
  let fixture: ComponentFixture<CardShelfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardShelfComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardShelfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
