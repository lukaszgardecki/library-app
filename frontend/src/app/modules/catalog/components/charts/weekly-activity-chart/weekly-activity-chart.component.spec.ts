import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WeeklyActivityChartComponent } from './weekly-activity-chart.component';

describe('WeeklyActivityChartComponent', () => {
  let component: WeeklyActivityChartComponent;
  let fixture: ComponentFixture<WeeklyActivityChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WeeklyActivityChartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WeeklyActivityChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
