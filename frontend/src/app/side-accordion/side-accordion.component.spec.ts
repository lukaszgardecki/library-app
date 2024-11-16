import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SideAccordionComponent } from './side-accordion.component';

describe('SideAccordionComponent', () => {
  let component: SideAccordionComponent;
  let fixture: ComponentFixture<SideAccordionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SideAccordionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SideAccordionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
