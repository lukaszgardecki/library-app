import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { WebsocketService } from './websocket.service';
import { BookItemRequest } from '../../shared/models/book-item-request';
import { Rack, Shelf, ShelfToSave, WarehouseBookItemRequestListView, WarehouseItem } from "../../../../shared/models/rack";
import { BehaviorSubject, catchError, Observable, Subscription, tap, throwError } from 'rxjs';
import { Page, Pageable } from '../../../../shared/models/page';
import { BookItemRequestStatus } from '../../shared/enums/book-item-request-status';
import { AuthenticationService } from './authentication.service';
import { BookItemService } from './book-item.service';
import { BookItem } from '../../../../shared/models/book-item';

@Injectable({
  providedIn: 'root'
})
export class WarehouseService {
  private baseURL;
  private pendingRequestsSubject = new BehaviorSubject<Page<WarehouseBookItemRequestListView>>(new Page<WarehouseBookItemRequestListView>);
  private inProgressRequestsSubject = new BehaviorSubject<Page<WarehouseBookItemRequestListView>>(new Page<WarehouseBookItemRequestListView>);
  private allRequestsSubject = new BehaviorSubject<Page<WarehouseBookItemRequestListView>>(new Page<WarehouseBookItemRequestListView>);
  private rackSubject = new BehaviorSubject<Page<Rack>>(new Page<Rack>());
  private shelfSubject = new BehaviorSubject<Page<Shelf>>(new Page<Shelf>());
  private bookItemsSubject = new BehaviorSubject<Page<BookItem>>(new Page<BookItem>);
  pendingRequests$ = this.pendingRequestsSubject.asObservable();
  inProgressRequests$ = this.inProgressRequestsSubject.asObservable();
  allRequests$ = this.allRequestsSubject.asObservable();
  racks$ = this.rackSubject.asObservable();
  shelves$ = this.shelfSubject.asObservable();
  bookItems$ = this.bookItemsSubject.asObservable();

  constructor(
    private http: HttpClient,
    private configService: ConfigService,
    private authService: AuthenticationService,
    private bookItemService: BookItemService,
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
          this.sortByDateAsc(this.pendingRequestsSubject.value.content);
          this.removeFrom(this.inProgressRequestsSubject, request);
          this.updateIn(this.allRequestsSubject, request);
        }
      });
    }
  }

  loadPageOfRequests(options: { status?: BookItemRequestStatus, query?: string, pageable?: Pageable } = {}) {
    this.getPageOfRequests(options).subscribe({
      next: page => {
        switch (options.status) {
          case BookItemRequestStatus.PENDING: this.pendingRequestsSubject.next(page); break;
          case BookItemRequestStatus.IN_PROGRESS: this.inProgressRequestsSubject.next(page); break;
          case null: this.allRequestsSubject.next(page); break;
          default: console.warn(`Nieznany status: ${options.status}`);
        }
      },
      error: err => console.error("Błąd pobierania danych:", err)
    });
  }

  loadPageOfRacks(options: { query?: string, pageable?: Pageable } = {}) {
    this.getPageOfRacks(options).subscribe({
      next: page => this.rackSubject.next(page),
      error: err => console.error("Błąd pobierania danych:", err)
    })
  }

  loadPageOfShelves(options: { rackId?: number, query?: string, pageable?: Pageable } = {}) {
    this.getPageOfShelves(options).subscribe({
      next: page => this.shelfSubject.next(page),
      error: err => console.error("Błąd pobierania danych:", err)
    });
  }

  loadPageOfBookItems(options: { rackId?: number, shelfId?: number,  query?: string, pageable?: Pageable } = {}) {
    this.getPageOfBookItems(options).subscribe({
      next: page => this.bookItemsSubject.next(page),
      error: err => console.error("Błąd pobierania danych:", err)
    });
  }


  addNextPageToPendingRequests(): Subscription | undefined {
    return this.addNextPageTo(this.pendingRequestsSubject, (pageable) => 
      this.getPageOfRequests({ status: BookItemRequestStatus.PENDING, pageable })
    );
  }
  
  addNextPageToRacks(): Subscription | undefined {
    return this.addNextPageTo(this.rackSubject, (pageable) => 
      this.getPageOfRacks({ pageable })
    );
  }
  
  addNextPageToShelves(): Subscription | undefined {
    return this.addNextPageTo(this.shelfSubject, (pageable) => 
      this.getPageOfShelves({ pageable })
    );
  }

  addNextPageToBookItems(): Subscription | undefined {
    return this.addNextPageTo(this.bookItemsSubject, (pageable) =>
      this.getPageOfBookItems({ pageable })
    );
  }

  moveRequestToInProgress(bookItemRequest: WarehouseBookItemRequestListView): void {
    this.websocketService.sendToTopic('/app/warehouse/move_to_in_progress', bookItemRequest);
  }

  moveRequestToPending(bookItemRequest: WarehouseBookItemRequestListView): void {
    this.websocketService.sendToTopic('/app/warehouse/move_to_pending', bookItemRequest);
  }

  completeRequest(bookItemRequest: WarehouseBookItemRequestListView): Observable<BookItemRequest | null> { 
    return this.http.post<BookItemRequest>(`${this.baseURL}/book-requests/${bookItemRequest.id}/ready`, {}, { withCredentials: true }).pipe(
      tap(() => this.removeFrom(this.inProgressRequestsSubject, bookItemRequest)),
      catchError(err => {
        console.error('Błąd podczas oznaczania książki jako gotowej:', err);
        return throwError(() => err);
      })
    );
  }

  addNewRack(rack: Rack): Observable<Rack> {
    return this.http.post<Rack>(`${this.baseURL}/racks`, rack, { withCredentials: true }).pipe(
      tap(newRack => this.addTo(this.rackSubject, newRack))
    );
  }

  addNewShelf(shelf: ShelfToSave): Observable<Shelf> {
    return this.http.post<Shelf>(`${this.baseURL}/shelves`, shelf, { withCredentials: true }).pipe(
      tap(newShelf => this.addTo(this.shelfSubject, newShelf))
    );
  }

  editRack(id: number, rack: Rack): Observable<Rack> {
    return this.http.patch<Rack>(`${this.baseURL}/racks/${id}`, rack, { withCredentials: true }).pipe(
      tap(updatedRack => {
        this.updateIn(this.rackSubject, updatedRack);
        // this.addTo(this.rackSubject, updatedRack);
      })
    );
  }

  editShelf(id: number, shelf: Shelf): Observable<Shelf> {
    return this.http.patch<Shelf>(`${this.baseURL}/shelves/${id}`, shelf, { withCredentials: true }).pipe(
      tap(updatedShelf => {
        this.updateIn(this.shelfSubject, updatedShelf);
        // this.addTo(this.shelfSubject, updatedShelf);
      })
    );
  }

  deleteRack(rack: Rack): Observable<void> {
    return this.http.delete<void>(`${this.baseURL}/racks/${rack.id}`, { withCredentials: true }).pipe(
      tap(() => this.removeFrom(this.rackSubject, rack))
    );
  }

  deleteShelf(shelf: Shelf): Observable<void> {
    return this.http.delete<void>(`${this.baseURL}/shelves/${shelf.id}`, { withCredentials: true }).pipe(
      tap(() => this.removeFrom(this.shelfSubject, shelf))
    );
  }

  private addNextPageTo<T>(
    subject: BehaviorSubject<Page<T>>, 
    fetchPage: (pageable: Pageable) => Observable<Page<T>>
  ): Subscription | undefined {
    const currentPage = subject.value;
  
    if (currentPage.number + 1 === currentPage.totalPages) return undefined;
  
    const pageable = new Pageable(currentPage.number + 1, currentPage.size);
  
    return fetchPage(pageable).subscribe({
      next: page => this.addNewPageTo(subject, page),
      error: err => console.error("Błąd pobierania danych:", err)
    });
  }

  private getPageOfRequests(options: { status?: BookItemRequestStatus, query?: string, pageable?: Pageable}): Observable<Page<WarehouseBookItemRequestListView>> {
    let params = this.createParams(options.query, options.pageable);
    if (options.status) { params = params.set("status", options.status)}
    return this.http.get<Page<WarehouseBookItemRequestListView>>(`${this.baseURL}/book-requests/list`, { params: params, withCredentials: true });
  }

  private getPageOfRacks(options: { query?: string, pageable?: Pageable }): Observable<Page<Rack>> {
    let params = this.createParams(options.query, options.pageable);
    return this.http.get<Page<Rack>>(`${this.baseURL}/racks`, { params: params, withCredentials: true });
  }

  private getPageOfShelves(options: { rackId?: number, query?: string, pageable?: Pageable }): Observable<Page<Shelf>> {
    let params = this.createParams(options.query, options.pageable);
    if (options.rackId) params = params.set("rack_id", options.rackId);
    return this.http.get<Page<Shelf>>(`${this.baseURL}/shelves`, { params: params, withCredentials: true });
  }

  private getPageOfBookItems(options: { rackId?: number, shelfId?: number, query?: string, pageable?: Pageable }): Observable<Page<BookItem>> {
    return this.bookItemService.getBookItems(options);
  }

  private addNewPageTo<T>(subject: BehaviorSubject<Page<T>>, newPage: Page<T>): void {
    const currentPage = subject.value;
    const updatedContent = [...currentPage.content, ...newPage.content];
    // const contentSortedByDate = this.sortByDateAsc(updatedContent);
  
    const updatedPage: Page<T> = {
      content: updatedContent,
      totalElements: newPage.totalElements,
      empty: newPage.empty, 
      first: newPage.first,
      last: newPage.last,
      number: newPage.number,
      numberOfElements: updatedContent.length,
      size: newPage.size,
      totalPages: newPage.totalPages
    };
    subject.next(updatedPage);
  }

  private addTo<T extends WarehouseItem>(subject: BehaviorSubject<Page<T>>, item: T): void {
    const currentPage = subject.value;
    const updatedContent = [...currentPage.content, item];
    // const contentSortedByDate: T[] = this.sortByDateAsc(updatedContent);
    const updatedPage: Page<T> = {
      ...currentPage,
      content: updatedContent,
      totalElements: currentPage.totalElements + 1
    };
    subject.next(updatedPage);
  }
  
  private removeFrom<T extends WarehouseItem>(subject: BehaviorSubject<Page<T>>, item: T): void {
    const currentPage = subject.value;
    const updatedContent = currentPage.content.filter(it => it.id !== item.id);
    const updatedPage = {
      ...currentPage,
      content: updatedContent,
      totalElements: currentPage.totalElements - 1
    };
    subject.next(updatedPage);
  }

  private  updateIn<T extends WarehouseItem>(subject: BehaviorSubject<Page<T>>, item: T): void {
    const currentPage = subject.value;
    const existingItemIndex = currentPage.content.findIndex(it => it.id === item.id);
    let updatedContent;
    
    if (existingItemIndex !== -1) {
      updatedContent = [
        ...currentPage.content.slice(0, existingItemIndex),
        item,
        ...currentPage.content.slice(existingItemIndex + 1)
      ];
    } else {
      updatedContent = [...currentPage.content, item];
    }
    
    const updatedPage = {
      ...currentPage,
      content: updatedContent,
      totalElements: updatedContent.length
    };
    subject.next(updatedPage);
  }

  private sortByDateAsc(items: WarehouseBookItemRequestListView[]): void {
    items.sort((a, b) => new Date(a.creationDate).getTime() - new Date(b.creationDate).getTime());
  }

  private createParams(query?: string, pageable?: Pageable): HttpParams {
    let params = new HttpParams();
    if (query) { params = params.set("q", query); }
    if (pageable != undefined) {
      if (pageable.page) { params = params.set("page", pageable.page)}
      if (pageable.size) { params = params.set("size", pageable.size)}
      if (pageable.sort?.direction) {
        const sortParam = pageable.sort.columnKey;
        const sortValue = `${sortParam},${pageable.sort.direction}`;
        params = params.set("sort", sortValue);
      }
    }
    return params;
  }
}
