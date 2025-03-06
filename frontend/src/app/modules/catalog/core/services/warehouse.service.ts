import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { WebsocketService } from './websocket.service';
import { BookItemRequest } from '../../shared/models/book-item-request';
import { BehaviorSubject } from 'rxjs';
import { Page, Pageable } from '../../../../shared/models/page';
import { BookItemRequestService } from './book-item-request.service';
import { BookItemRequestStatus } from '../../shared/enums/book-item-request-status';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class WarehouseService {
  private pendingRequestsSubject = new BehaviorSubject<Page<BookItemRequest>>(new Page<BookItemRequest>);
  private inProgressRequestsSubject = new BehaviorSubject<Page<BookItemRequest>>(new Page<BookItemRequest>);
  private allRequestsSubject = new BehaviorSubject<Page<BookItemRequest>>(new Page<BookItemRequest>);
  pendingRequests$ = this.pendingRequestsSubject.asObservable();
  inProgressRequests$ = this.inProgressRequestsSubject.asObservable();
  allRequests$ = this.allRequestsSubject.asObservable();


  constructor(
    private http: HttpClient,
    private configService: ConfigService,
    private authService: AuthenticationService,
    private websocketService: WebsocketService,
    private bookItemRequestService: BookItemRequestService,
  ) {
    if (authService.hasUserPermissionToWarehouse()) {
      this.websocketService.subscribeToTopic<BookItemRequest>("/queue/warehouse/pending").subscribe({
        next: request => this.addTo(this.pendingRequestsSubject, request)
      });
      this.websocketService.subscribeToTopic<BookItemRequest>("/queue/warehouse/remove_from_pending").subscribe({
        next: request => {
          this.removeFrom(this.pendingRequestsSubject, request);
          this.addTo(this.inProgressRequestsSubject, request);
          this.updateIn(this.allRequestsSubject, request);
        }
      });
      this.websocketService.subscribeToTopic<BookItemRequest>("/queue/warehouse/remove_from_in-progress").subscribe({
        next: request => {
          this.addTo(this.pendingRequestsSubject, request);
          this.removeFrom(this.inProgressRequestsSubject, request);
          this.updateIn(this.allRequestsSubject, request);
        }
      });
    }
  }

  getRequests(status: BookItemRequestStatus | null = null, query: string = "", pageable: Pageable = new Pageable()) {
    this.bookItemRequestService.getRequests(status, query, pageable).subscribe({
      next: page => {
        switch (status) {
          case BookItemRequestStatus.PENDING: this.pendingRequestsSubject.next(page); break;
          case BookItemRequestStatus.IN_PROGRESS: this.inProgressRequestsSubject.next(page); break;
          case null: this.allRequestsSubject.next(page); break;
          default: console.warn(`Nieznany status: ${status}`);
        }
      },
      error: err => console.error("Błąd pobierania danych:", err)
    });
  }

  select(reservation: BookItemRequest): void {

  }

  moveToInProgress(bookItemRequest: BookItemRequest): void {
    this.websocketService.sendToTopic('/app/warehouse/move_to_in_progress', bookItemRequest);
  }

  moveToPending(bookItemRequest: BookItemRequest): void {
    this.websocketService.sendToTopic('/app/warehouse/move_to_pending', bookItemRequest);
  }

  completeRequest(bookItemRequest: BookItemRequest): void { 
    this.bookItemRequestService.completeRequest(bookItemRequest.id).subscribe({
      next: () => this.removeFrom(this.allRequestsSubject, bookItemRequest)
    });
  }

  private addTo(subject: BehaviorSubject<Page<BookItemRequest>>, bookItemRequest: BookItemRequest): void {
    const currentPage = subject.value;
    const updatedContent = [...currentPage.content, bookItemRequest];
    const contentSortedByDate = this.sortByDateAsc(updatedContent);
    const updatedPage = {
      ...currentPage,
      content: contentSortedByDate,
      totalElements: currentPage.totalElements + 1
    };
    subject.next(updatedPage);
  }
  
  private removeFrom(subject: BehaviorSubject<Page<BookItemRequest>>, bookItemRequest: BookItemRequest): void {
    const currentPage = subject.value;
    const updatedContent = currentPage.content.filter(item => item.id !== bookItemRequest.id);
    const updatedPage = {
      ...currentPage,
      content: updatedContent,
      totalElements: currentPage.totalElements - 1
    };
    subject.next(updatedPage);
  }

  private updateIn(subject: BehaviorSubject<Page<BookItemRequest>>, bookItemRequest: BookItemRequest): void {
    const currentPage = subject.value;
    const existingItemIndex = currentPage.content.findIndex(item => item.id === bookItemRequest.id);
    let updatedContent;
    
    if (existingItemIndex !== -1) {
      updatedContent = [
        ...currentPage.content.slice(0, existingItemIndex),
        bookItemRequest,
        ...currentPage.content.slice(existingItemIndex + 1)
      ];
    } else {
      updatedContent = [...currentPage.content, bookItemRequest];
    }
    
    const updatedPage = {
      ...currentPage,
      content: updatedContent,
      totalElements: updatedContent.length
    };
    subject.next(updatedPage);
  }
  

  private sortByDateAsc(reservations: BookItemRequest[]): BookItemRequest[] {
    return reservations.sort((a, b) => new Date(a.creationDate).getTime() - new Date(b.creationDate).getTime());
  }
}
