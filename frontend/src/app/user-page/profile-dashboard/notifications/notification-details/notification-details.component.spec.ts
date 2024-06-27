import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationDetailsComponent } from './notification-details.component';
import { describe, beforeEach, it } from 'node:test';
import { NotificationService } from '../../../services/notification.service';
import { of } from 'rxjs';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';

class MockNotificationService {
  getSingleNotificationById() {
    return of({ 
      id: 1, 
      subject: 'Test Notification', 
      createdAt: new Date(), 
      bookTitle: 'Test Book', 
      content: 'This is a test notification content'
    });
  }
}

describe('NotificationDetailsComponent', () => {
  let component: NotificationDetailsComponent;
  let fixture: ComponentFixture<NotificationDetailsComponent>;
  let notificationService: NotificationService;
  let location: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotificationDetailsComponent],
      providers: [
        { provide: NotificationService, useClass: MockNotificationService },
        { provide: Location, useValue: { back: jasmine.createSpy('back') } }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationDetailsComponent);
    component = fixture.componentInstance;
    notificationService = TestBed.inject(NotificationService);
    location = TestBed.inject(Location);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch notification on init', () => {
    const mockNotification = {
      id: 1,
      subject: 'Test Notification',
      createdAt: new Date(),
      bookTitle: 'Test Book',
      content: 'This is a test notification content'
    };

    component.ngOnInit();
    expect(component.notification).toEqual(mockNotification);
  });

  it('should display notification details', () => {
    fixture.detectChanges(); // Update the view

    const debugElement: DebugElement = fixture.debugElement;
    const subjectElement: HTMLElement = debugElement.query(By.css('h1')).nativeElement;
    const creationDateElement: HTMLElement = debugElement.query(By.css('p:nth-child(2)')).nativeElement;
    const bookTitleElement: HTMLElement = debugElement.query(By.css('p:nth-child(3)')).nativeElement;
    const contentElement: HTMLElement = debugElement.query(By.css('p:nth-child(4)')).nativeElement;

    expect(subjectElement.textContent).toContain('Test Notification');
    expect(creationDateElement.textContent).toContain('Creation date:');
    expect(bookTitleElement.textContent).toContain('Document: Test Book');
    expect(contentElement.textContent).toContain('Content: This is a test notification content');
  });

  it('should navigate back on button click', () => {
    const button: HTMLElement = fixture.debugElement.query(By.css('button')).nativeElement;
    button.click();

    expect(location.back()).toHaveBeenCalled();
  });
});

