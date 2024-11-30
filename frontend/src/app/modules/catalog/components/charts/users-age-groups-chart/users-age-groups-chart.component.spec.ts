import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersAgeGroupsChartComponent } from './users-age-groups-chart.component';

describe('UsersAgeGroupsChartComponent', () => {
  let component: UsersAgeGroupsChartComponent;
  let fixture: ComponentFixture<UsersAgeGroupsChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsersAgeGroupsChartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersAgeGroupsChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
