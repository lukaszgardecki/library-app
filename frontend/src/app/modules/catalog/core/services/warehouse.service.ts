import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { WebsocketService } from './websocket.service';
import { BookItemRequest, WarehouseBookItemRequestListView } from '../../shared/models/book-item-request';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { Page, Pageable } from '../../../../shared/models/page';
import { BookItemRequestStatus } from '../../shared/enums/book-item-request-status';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class WarehouseService {
  private baseURL;
  private pendingRequestsSubject = new BehaviorSubject<Page<WarehouseBookItemRequestListView>>(new Page<WarehouseBookItemRequestListView>);
  private inProgressRequestsSubject = new BehaviorSubject<Page<WarehouseBookItemRequestListView>>(new Page<WarehouseBookItemRequestListView>);
  private allRequestsSubject = new BehaviorSubject<Page<WarehouseBookItemRequestListView>>(new Page<WarehouseBookItemRequestListView>);
  pendingRequests$ = this.pendingRequestsSubject.asObservable();
  inProgressRequests$ = this.inProgressRequestsSubject.asObservable();
  allRequests$ = this.allRequestsSubject.asObservable();


  constructor(
    private http: HttpClient,
    private configService: ConfigService,
    private authService: AuthenticationService,
    private websocketService: WebsocketService,
  ) {
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/warehouse`;

    if (authService.hasUserPermissionToWarehouse()) {
      this.websocketService.subscribeToTopic<WarehouseBookItemRequestListView>("/queue/warehouse/pending").subscribe({
        next: request => this.addTo(this.pendingRequestsSubject, request)
      });
      this.websocketService.subscribeToTopic<WarehouseBookItemRequestListView>("/queue/warehouse/remove_from_pending").subscribe({
        next: request => {
          this.removeFrom(this.pendingRequestsSubject, request);
          this.addTo(this.inProgressRequestsSubject, request);
          this.updateIn(this.allRequestsSubject, request);
        }
      });
      this.websocketService.subscribeToTopic<WarehouseBookItemRequestListView>("/queue/warehouse/remove_from_in-progress").subscribe({
        next: request => {
          this.addTo(this.pendingRequestsSubject, request);
          this.removeFrom(this.inProgressRequestsSubject, request);
          this.updateIn(this.allRequestsSubject, request);
        }
      });
    }
  }

  loadPageOfRequests(status: BookItemRequestStatus | null = null, query: string = "", pageable: Pageable = new Pageable()) {
    return this.getNewPageOfRequests(status, query, pageable).subscribe({
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

  addNextPageToPendingRequests(): Subscription | undefined {
    const currentPage = this.pendingRequestsSubject.value;
    
    if (currentPage.number + 1 == currentPage.totalPages) return undefined;
    const pageable = new Pageable(currentPage.number+1, currentPage.size)

    return this.getNewPageOfRequests(BookItemRequestStatus.PENDING, "", pageable).subscribe({
      next: page => this.addNewPageTo(this.pendingRequestsSubject, page),
      error: err => console.error("Błąd pobierania danych:", err)
    });
  }

  moveToInProgress(bookItemRequest: WarehouseBookItemRequestListView): void {
    this.websocketService.sendToTopic('/app/warehouse/move_to_in_progress', bookItemRequest);
  }

  moveToPending(bookItemRequest: WarehouseBookItemRequestListView): void {
    this.websocketService.sendToTopic('/app/warehouse/move_to_pending', bookItemRequest);
  }

  completeRequest(bookItemRequest: WarehouseBookItemRequestListView): void { 
    this.http.post<BookItemRequest>(`${this.baseURL}/book-requests/${bookItemRequest.requestId}/ready`, {}, { withCredentials: true }).subscribe({
      next: () => this.removeFrom(this.inProgressRequestsSubject, bookItemRequest)
    });
  }

  private getNewPageOfRequests(status: BookItemRequestStatus | null = null, query: string = "", pageable: Pageable = new Pageable()): Observable<Page<WarehouseBookItemRequestListView>> {
    let params = this.createParams(status, query, pageable);
    return this.http.get<Page<WarehouseBookItemRequestListView>>(`${this.baseURL}/book-requests/list`, { params: params, withCredentials: true });
  }

  private addNewPageTo(subject: BehaviorSubject<Page<WarehouseBookItemRequestListView>>, newPage: Page<WarehouseBookItemRequestListView>): void {
    const currentPage = subject.value;
    const updatedContent = [...currentPage.content, ...newPage.content];
    const contentSortedByDate = this.sortByDateAsc(updatedContent); //to chyba nie jest portzebne
  
    const updatedPage: Page<WarehouseBookItemRequestListView> = {
      content: contentSortedByDate,
      totalElements: newPage.totalElements,
      empty: newPage.empty, 
      first: newPage.first,
      last: newPage.last,
      number: newPage.number,
      numberOfElements: contentSortedByDate.length,
      size: newPage.size,
      totalPages: newPage.totalPages
    };
    subject.next(updatedPage);
  }

  private addTo(subject: BehaviorSubject<Page<WarehouseBookItemRequestListView>>, bookItemRequest: WarehouseBookItemRequestListView): void {
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
  
  private removeFrom(subject: BehaviorSubject<Page<WarehouseBookItemRequestListView>>, bookItemRequest: WarehouseBookItemRequestListView): void {
    const currentPage = subject.value;
    const updatedContent = currentPage.content.filter(item => item.requestId !== bookItemRequest.requestId);
    const updatedPage = {
      ...currentPage,
      content: updatedContent,
      totalElements: currentPage.totalElements - 1
    };
    subject.next(updatedPage);
  }

  private updateIn(subject: BehaviorSubject<Page<WarehouseBookItemRequestListView>>, bookItemRequest: WarehouseBookItemRequestListView): void {
    const currentPage = subject.value;
    const existingItemIndex = currentPage.content.findIndex(item => item.requestId === bookItemRequest.requestId);
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
  

  private sortByDateAsc(reservations: WarehouseBookItemRequestListView[]): WarehouseBookItemRequestListView[] {
    return reservations.sort((a, b) => new Date(a.creationDate).getTime() - new Date(b.creationDate).getTime());
  }

  private createParams(status: BookItemRequestStatus | null, query: string | null, pageable: Pageable): HttpParams {
      let params = new HttpParams();
      const page = pageable.page;
      const size = pageable.size;
      const sort = pageable.sort;
      if (page !== null) { params = params.set("page", page); }
      if (size !== null) { params = params.set("size", size); }
      if (query !== null) { params = params.set("q", query); }
      if (status !== null) { params = params.set("status", status)}
      if (sort?.direction) {
          const sortParam = sort.columnKey;
          const sortValue = `${sortParam},${sort.direction}`;
          params = params.set("sort", sortValue);
      }
      return params;
    }
}
