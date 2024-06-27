import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OnSiteItemsComponent } from './on-site-items.component';

describe('OnSiteItemsComponent', () => {
  let component: OnSiteItemsComponent;
  let fixture: ComponentFixture<OnSiteItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OnSiteItemsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OnSiteItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
