import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { BasicSectionComponent } from "../../../components/sections/basic-section/basic-section.component";
import { debounceTime, distinctUntilChanged, Observable, Subscription } from 'rxjs';
import { Page } from '../../../../../shared/models/page';
import { Rack, Shelf } from '../../../../../shared/models/rack';
import { CardRackComponent } from "./cards/card-rack/card-rack.component";
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CardShelfComponent } from "./cards/card-shelf/card-shelf.component";
import { WarehouseService } from '../../../core/services/warehouse.service';
import { CardBookItemComponent } from "./cards/card-book-item/card-book-item.component";
import { BookItem } from '../../../../../shared/models/book-item';
import { EnumNamePipe } from "../../../../../shared/pipes/enum-name.pipe";

@Component({
  selector: 'app-racks',
  standalone: true,
  imports: [CommonModule, TranslateModule, ReactiveFormsModule, BasicSectionComponent, CardRackComponent, CardShelfComponent, CardBookItemComponent, EnumNamePipe],
  templateUrl: './racks.component.html',
  styleUrl: './racks.component.css'
})
export class RacksComponent implements OnInit {
  racksPage$: Observable<Page<Rack>> = this.warehouseService.racks$;
  shelvesPage$: Observable<Page<Shelf>> = this.warehouseService.shelves$;
  bookItemsPage$: Observable<Page<BookItem>> = this.warehouseService.bookItems$;
  selectedRack: Rack | undefined;
  selectedShelf: Shelf | undefined;
  selectedBookItem: BookItem | undefined;
  searchRacks = new FormControl('');
  searchShelves = new FormControl('');
  searchBookItems = new FormControl('');
  racksLoading = false;
  shelvesLoading = false;
  bookItemsLoading = false;

  constructor(
    private warehouseService: WarehouseService
  ) {}

  ngOnInit(): void {
      this.warehouseService.loadPageOfRacks();
      this.warehouseService.loadPageOfShelves();
      this.warehouseService.loadPageOfBookItems();

      this.searchRacks.valueChanges.pipe(
          debounceTime(500),
          distinctUntilChanged()
      ).subscribe(searchQuery => {
        this.warehouseService.loadPageOfRacks({ query: searchQuery ?? undefined});
        this.warehouseService.loadPageOfShelves();
        this.selectedShelf = undefined;
      });
      
      this.searchShelves.valueChanges.pipe(
        debounceTime(500),
        distinctUntilChanged()
      ).subscribe(searchQuery => {
        this.warehouseService.loadPageOfShelves({ rackId: this.selectedRack?.id, query: searchQuery ?? undefined });
        this.warehouseService.loadPageOfBookItems();
        this.selectedShelf = undefined;
      });

      this.searchBookItems.valueChanges.pipe(
        debounceTime(500),
        distinctUntilChanged()
      ).subscribe(searchQuery => {
        this.warehouseService.loadPageOfBookItems({ rackId: this.selectedRack?.id, shelfId: this.selectedShelf?.id, query: searchQuery ?? undefined });
      });
  }

  activeRack(rack: Rack) {
    if (this.selectedRack==rack) {
      this.unselectRack();
      this.warehouseService.loadPageOfShelves();
      this.warehouseService.loadPageOfBookItems();
    } else {
      this.selectRack(rack);
      this.warehouseService.loadPageOfShelves({ rackId: rack.id });
      this.warehouseService.loadPageOfBookItems({ rackId: rack.id, shelfId: this.selectedShelf?.id });
    }
    this.unselectShelf();
    this.unselectBookItem();
    this.resestLoadings();
  }

  activeShelf(shelf: Shelf) {
    if (this.selectedShelf==shelf) {
      this.unselectShelf();
      this.warehouseService.loadPageOfBookItems();
    } else {
      this.selectShelf(shelf);
      this.warehouseService.loadPageOfBookItems({ rackId: this.selectedRack?.id, shelfId: shelf.id });
    }
    this.unselectBookItem();
  }

  activeBookItem(bookItem: BookItem) {
    this.selectedBookItem == bookItem ? this.unselectBookItem() : this.selectBookItem(bookItem);
  }









  loadNextPageOfRacks(event: any): void {
    const element = event.target;
    if (this.racksLoading) return;
  
    if (element.scrollHeight - element.scrollTop <= element.clientHeight + 50) {
      this.racksLoading = true;
      this.warehouseService.addNextPageToRacks()?.add(() => {
        this.racksLoading = false;
      })
    }
  }

  

  loadNextPageOfShelves(event: any): void {
    const element = event.target;
    if (this.shelvesLoading) return;
  
    if (element.scrollHeight - element.scrollTop <= element.clientHeight + 50) {
      this.shelvesLoading = true;
      this.warehouseService.addNextPageToShelves()?.add(() => {
        this.shelvesLoading = false;
      })
    }
  }

  loadNextPageOfBookItems(event: any): void {
    const element = event.target;
    if (this.bookItemsLoading) return;

    if (element.scrollHeight - element.scrollTop <= element.clientHeight + 50) {
      this.bookItemsLoading = true;
      this.warehouseService.addNextPageToBookItems()?.add(() => {
        this.bookItemsLoading = false;
      })
    }
  }

  private selectRack = (rack: Rack) => this.selectedRack = rack;
  private unselectRack = () => this.selectedRack = undefined;
  
  private selectShelf = (shelf: Shelf) => this.selectedShelf = shelf;
  private unselectShelf = () => this.selectedShelf = undefined;

  private selectBookItem = (bookItem: BookItem) => this.selectedBookItem = bookItem;
  private unselectBookItem = () => this.selectedBookItem = undefined;

  private resestLoadings() {
    this.racksLoading = false;
    this.shelvesLoading = false;
    this.bookItemsLoading = false;
  }
}
