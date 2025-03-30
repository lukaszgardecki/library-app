import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmModalDialogComponent } from './confirm-modal-dialog.component';

describe('ConfirmModalDialogComponent', () => {
  let component: ConfirmModalDialogComponent;
  let fixture: ComponentFixture<ConfirmModalDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmModalDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmModalDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
