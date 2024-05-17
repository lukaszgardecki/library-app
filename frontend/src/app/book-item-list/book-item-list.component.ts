import { Component, OnInit } from '@angular/core';
import { BookItemsService } from '../services/book-items.service';
import { BookItem } from '../models/book-item';
import { Page } from '../shared/page';
import { HypermediaCollection } from '../shared/hypermedia-collection';
import { ActivatedRoute, Params } from '@angular/router';
import { ListComponent } from '../shared/list-component';

@Component({
  selector: 'app-book-item-list',
  templateUrl: './book-item-list.component.html',
  styleUrl: './book-item-list.component.css'
})
export class BookItemListComponent implements ListComponent, OnInit {
  page: Page = new Page();
  routeName: string = "book-items";
  links: HypermediaCollection;
  bookItems: Array<BookItem>;
  sortTypes = [
    {name: "---", queryParam: undefined},
    {name: "Tytuł rosnąco", queryParam: "book.title,asc"},
    {name: "Tytuł malejąco", queryParam: "book.title,desc"},
    {name: "Cena rosnąco", queryParam: "price,asc"},
    {name: "Cena malejąco", queryParam: "price,desc"}
  ];

  constructor(private bookItemsService: BookItemsService, private route: ActivatedRoute) { }
  getAllByParams(queryParams?: Params | undefined) {
    throw new Error('Method not implemented.');
  }
  

  ngOnInit(): void {
    const pageNo = this.route.snapshot.queryParams["page"];
    const pageSize = this.route.snapshot.queryParams["size"];
    // this.getAll(pageNo, pageSize);
  }

  // getAll(page: number, size: number, sort: string="asc") {
  //   this.bookItemsService.getAllBookItems(page, size, sort).subscribe(p => {
  //     this.page = p.page;
  //     this.bookItems = p._embedded.bookItemDtoList;
  //     this.links = p._links;
  //   });
  // }

  sort(sort: any) {
      
  }


  changeSize(size: number) {
      
  }
}
