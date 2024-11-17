import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavGenreChartComponent } from './fav-genre-chart.component';

describe('FavGenreChartComponent', () => {
  let component: FavGenreChartComponent;
  let fixture: ComponentFixture<FavGenreChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FavGenreChartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FavGenreChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
