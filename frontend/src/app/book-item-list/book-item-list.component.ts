import { Component, OnInit } from '@angular/core';
import { BookItemsService } from '../services/book-items.service';
import { BookItem } from '../models/book-item';
import { ActivatedRoute, Router } from '@angular/router';
import { Page } from '../shared/page';
import { HypermediaCollection } from '../shared/hypermedia-collection';

@Component({
  selector: 'app-book-item-list',
  templateUrl: './book-item-list.component.html',
  styleUrl: './book-item-list.component.css'
})
export class BookItemListComponent implements OnInit {
  page: Page;
  links: HypermediaCollection;
  bookItems: Array<BookItem>;

  constructor(private bookItemsService: BookItemsService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    const pageNo = this.route.snapshot.queryParams["page"];
    const pageSize = this.route.snapshot.queryParams["size"]

    this.getAllBookItems(pageNo, pageSize);
  }

  getAllBookItems(page: number, size: number) {
    
    this.bookItemsService.getAllBookItems(page, size).subscribe(p => {
  
      this.page = p.page;
      this.bookItems = p._embedded.bookItemDtoList;
      this.links = p._links;
    });
  }

  
}
