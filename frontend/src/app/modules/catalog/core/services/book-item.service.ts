import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Page, Pageable } from '../../../../shared/models/page';
import { ConfigService } from './config.service';
import { BookItem, BookItemWithBook } from '../../../../shared/models/book-item';

@Injectable({
  providedIn: 'root'
})
export class BookItemService {
  private baseURL;

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { 
    let baseURL = configService.getApiUrl();
    this.baseURL = `${baseURL}/catalog/book-items`;
  }

  getBookItems(options: { rackId?: number, shelfId?: number, query?: string, pageable?: Pageable }) {
    let params = this.createParams(options.query, options.pageable);
    if (options.rackId) params = params.set("rack_id", options.rackId);
    if (options.shelfId) params = params.set("shelf_id", options.shelfId);
    return this.http.get<Page<BookItemWithBook>>(`${this.baseURL}`, { params: params, withCredentials: true });
  }

  updateBookItem(id:number, rackId: number, shelfId: number) {
    const fieldsToUpdate = new BookItem()
    fieldsToUpdate.rackId = rackId;
    fieldsToUpdate.shelfId = shelfId
    return this.http.patch<BookItemWithBook>(`${this.baseURL}/${id}`, fieldsToUpdate, { withCredentials: true });
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
