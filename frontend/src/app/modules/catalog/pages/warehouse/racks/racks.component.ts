import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { BasicSectionComponent } from "../../../components/sections/basic-section/basic-section.component";
import { debounceTime, distinctUntilChanged, Observable } from 'rxjs';
import { Page } from '../../../../../shared/models/page';
import { Rack, Shelf, ShelfToSave } from '../../../../../shared/models/rack';
import { CardRackComponent } from "./cards/card-rack/card-rack.component";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CardShelfComponent } from "./cards/card-shelf/card-shelf.component";
import { WarehouseService } from '../../../core/services/warehouse.service';
import { CardBookItemComponent } from "./cards/card-book-item/card-book-item.component";
import { BookItem } from '../../../../../shared/models/book-item';
import { EnumNamePipe } from "../../../../../shared/pipes/enum-name.pipe";
import { ModalDialogComponent } from '../../../components/modal-dialog/modal-dialog.component';
import { SuccessToastComponent } from '../../../components/toasts/success-toast/success-toast.component';
import { FailureToastComponent } from "../../../components/toasts/failure-toast/failure-toast.component";

@Component({
  selector: 'app-racks',
  standalone: true,
  imports: [
    CommonModule, TranslateModule, ReactiveFormsModule,
    BasicSectionComponent, CardRackComponent, CardShelfComponent, CardBookItemComponent,
    EnumNamePipe,
    ModalDialogComponent,
    SuccessToastComponent,
    FailureToastComponent
],
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
  newRackForm: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    location: new FormControl('', Validators.required)
  });
  editRackForm: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    location: new FormControl('', Validators.required)
  });
  newShelfForm: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required)
  });
  editShelfForm: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required)
  });
  @ViewChild('successToast') successToast!: SuccessToastComponent;
  @ViewChild('failureToast') failureToast!: FailureToastComponent;
  @ViewChild('rackDeleteModal') rackDeleteModal!: ModalDialogComponent;
  @ViewChild('shelfDeleteModal') shelfDeleteModal!: ModalDialogComponent;

  constructor(
    private warehouseService: WarehouseService,
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

  addRack() {
    if(this.newRackForm.invalid) {
      this.failureToast.showToast('CAT.TOAST.FORM_INVALID');
      return
    };
    const newRack: Rack = new Rack();
    newRack.name = this.newRackForm.value.name;
    newRack.location = this.newRackForm.value.location;
    this.warehouseService.addNewRack(newRack).subscribe({
      next: () => {
        this.successToast.showToast('CAT.TOAST.WAREHOUSE.RACK.CREATE.SUCCESS')
        this.newRackForm.reset();
      },
      error: () => this.failureToast.showToast('CAT.TOAST.WAREHOUSE.RACK.CREATE.FAILURE')
    });
  }

  addShelf() {
    if(this.newShelfForm.invalid || !this.selectedRack) {
      this.failureToast.showToast('CAT.TOAST.FORM_INVALID');
      return;
    }
    const newShelf: ShelfToSave = new ShelfToSave();
    newShelf.name = this.newShelfForm.value.name;
    newShelf.rackId = this.selectedRack.id;
    this.warehouseService.addNewShelf(newShelf).subscribe({
      next: () => {
        this.successToast.showToast('CAT.TOAST.WAREHOUSE.SHELF.CREATE.SUCCESS');
        this.newShelfForm.reset();
        if (this.selectedRack) this.selectedRack.shelvesCount += 1;
      },
      error: () => this.failureToast.showToast('CAT.TOAST.WAREHOUSE.SHELF.CREATE.FAILURE')
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

  loadValuesToRackEditForm() {
    this.editRackForm = new FormGroup({
      name: new FormControl(this.selectedRack?.name, Validators.required),
      location: new FormControl(this.selectedRack?.location, Validators.required)
    });
  }

  loadValuesToShelfEditForm() {
    this.editShelfForm = new FormGroup({
      name: new FormControl(this.selectedShelf?.name, Validators.required),
    });
  }

  editRack() {
    if (this.editRackForm.invalid || !this.selectedRack) {
      this.failureToast.showToast('CAT.TOAST.WAREHOUSE.RACK.EDIT.FAILURE');
      return
    }
    const rackToUpdate = new Rack()
    rackToUpdate.name = this.editRackForm.value.name;
    rackToUpdate.location = this.editRackForm.value.location;
    this.warehouseService.editRack(this.selectedRack.id, rackToUpdate).subscribe({
      next: updatedRack => {
        this.selectedRack = updatedRack;
        this.successToast.showToast('CAT.TOAST.WAREHOUSE.RACK.EDIT.SUCCESS');
      },
      error: () => this.failureToast.showToast('CAT.TOAST.WAREHOUSE.RACK.EDIT.FAILURE')
    });
  }

  editShelf() {
    if (this.editShelfForm.invalid || !this.selectedShelf) {
      this.failureToast.showToast('CAT.TOAST.WAREHOUSE.SHELF.EDIT.FAILURE');
      return
    }
    const shelfToUpdate = new Shelf();
    shelfToUpdate.name = this.editShelfForm.value.name;
    this.warehouseService.editShelf(this.selectedShelf.id, shelfToUpdate).subscribe({
      next: updatedShelf => {
        this.selectedShelf = updatedShelf;
        this.successToast.showToast('CAT.TOAST.WAREHOUSE.SHELF.EDIT.SUCCESS');
      },
      error: () => this.failureToast.showToast('CAT.TOAST.WAREHOUSE.SHELF.EDIT.FAILURE')
    });
  }

  validateRackDeletion(event: Event) {
    event.stopPropagation();
    if (!this.selectedRack || this.selectedRack.shelvesCount > 0) {
      this.failureToast.showToast('CAT.TOAST.WAREHOUSE.RACK.DELETE.FAILURE.HAS_BOOKS')
      return;
    }
    this.rackDeleteModal.open();
  }


  validateShelfDeletion(event: Event) {
    event.stopPropagation();
    if (!this.selectedShelf || this.selectedShelf.bookItemsCount > 0) {
      this.failureToast.showToast('CAT.TOAST.WAREHOUSE.SHELF.DELETE.FAILURE.HAS_BOOKS');
      return;
    }
    this.shelfDeleteModal.open();
  }

  deleteRack() {
    if(!this.selectedRack) return;
    this.warehouseService.deleteRack(this.selectedRack).subscribe({
      next: () => {
        this.successToast.showToast('CAT.TOAST.WAREHOUSE.RACK.DELETE.SUCCESS');
        this.selectedRack = undefined;
      },
      error: () => this.failureToast.showToast('CAT.TOAST.WAREHOUSE.RACK.DELETE.FAILURE.MAIN')
    })
  }

  deleteShelf() {
    if(!this.selectedShelf) return;
    this.warehouseService.deleteShelf(this.selectedShelf).subscribe({
      next: () => {
        this.successToast.showToast('CAT.TOAST.WAREHOUSE.SHELF.DELETE.SUCCESS');
        this.selectedShelf = undefined;
      },
      error: () => this.failureToast.showToast('CAT.TOAST.WAREHOUSE.SHELF.DELETE.FAILURE.MAIN')
    })
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
