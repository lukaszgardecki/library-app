import { CommonModule } from '@angular/common';
import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { BasicSectionComponent } from "../../../components/sections/basic-section/basic-section.component";
import { BehaviorSubject, Observable } from 'rxjs';
import { Page } from '../../../../../shared/models/page';
import { Rack, Shelf, ShelfToSave } from '../../../../../shared/models/rack';
import { CardRackComponent } from "./cards/card-rack/card-rack.component";
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CardShelfComponent } from "./cards/card-shelf/card-shelf.component";
import { WarehouseService } from '../../../core/services/warehouse.service';
import { CardBookItemComponent } from "./cards/card-book-item/card-book-item.component";
import { BookItemWithBook } from '../../../../../shared/models/book-item';
import { EnumNamePipe } from "../../../../../shared/pipes/enum-name.pipe";
import { ModalDialogComponent } from '../../../components/modal-dialog/modal-dialog.component';
import { ToastContainerComponent } from "../../../components/toasts/toast-container/toast-container.component";
import { NullPlaceholderPipe } from "../../../../../shared/pipes/null-placeholder.pipe";
import { ModalHostComponent } from "../../../components/modal-dialog/modal-host/modal-host.component";
import { ModalService } from '../../../core/services/modal.service';
import { FormService } from '../../../core/services/form.service';
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-racks',
  standalone: true,
  imports: [
    CommonModule, TranslateModule, ReactiveFormsModule, FormsModule,
    BasicSectionComponent, CardRackComponent, CardShelfComponent, CardBookItemComponent,
    EnumNamePipe, NullPlaceholderPipe,
    ToastContainerComponent, ModalHostComponent, NgSelectModule
],
  templateUrl: './racks.component.html',
  styleUrl: './racks.component.css'
})
export class RacksComponent implements OnInit {
  racksPage$: Observable<Page<Rack>> = this.warehouseService.racks$;
  shelvesPage$: Observable<Page<Shelf>> = this.warehouseService.shelves$;
  bookItemsPage$: Observable<Page<BookItemWithBook>> = this.warehouseService.bookItems$;
  private moveBookItemsShelvesSubject: BehaviorSubject<Shelf[]> = new BehaviorSubject<Shelf[]>([]);
  private moveBookItemsRacksSubject: BehaviorSubject<Rack[]> = new BehaviorSubject<Rack[]>([]);

  moveBookItemsRacks$ = this.moveBookItemsRacksSubject.asObservable();
  moveBookItemsShelves$ = this.moveBookItemsShelvesSubject.asObservable();
  selectedRack: Rack | undefined;
  selectedShelf: Shelf | undefined;
  selectedBookItem: BookItemWithBook | undefined;
  searchRacks = new FormControl('');
  searchShelves = new FormControl('');
  searchBookItems = new FormControl('');
  racksLoading = false;
  shelvesLoading = false;
  bookItemsLoading = false;
  newRackForm: FormGroup;
  editRackForm: FormGroup;
  newShelfForm: FormGroup;
  editShelfForm: FormGroup;
  moveBookItemForm: FormGroup;
  @ViewChild('toastContainer') toastContainer!: ToastContainerComponent;

  @ViewChild('addRackDialogBody') addRackDialogBody: TemplateRef<any>;
  @ViewChild('editRackDialogBody') editRackDialogBody: TemplateRef<any>;
  @ViewChild('addShelfDialogBody') addShelfDialogBody: TemplateRef<any>;
  @ViewChild('editShelfDialogBody') editShelfDialogBody: TemplateRef<any>;
  @ViewChild('moveBookItemDialogBody') moveBookItemDialogBody: TemplateRef<any>;

  constructor(
    private warehouseService: WarehouseService,
    private modalService: ModalService,
    private translate: TranslateService,
    private formService: FormService
  ) {}

  ngOnInit(): void {
      this.warehouseService.loadPageOfRacks();
      this.warehouseService.loadPageOfShelves();
      this.warehouseService.loadPageOfBookItems();

      this.formService.subscribeSearchField(this.searchRacks, (searchQuery) => {
        this.warehouseService.loadPageOfRacks({ query: searchQuery ?? undefined});
        this.warehouseService.loadPageOfShelves();
        this.selectedShelf = undefined;
      });
      this.formService.subscribeSearchField(this.searchShelves, (searchQuery) => {
        this.warehouseService.loadPageOfShelves({ rackId: this.selectedRack?.id, query: searchQuery ?? undefined });
        this.warehouseService.loadPageOfBookItems();
        this.selectedShelf = undefined;
      });
      this.formService.subscribeSearchField(this.searchBookItems, (searchQuery) => {
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

  activeBookItem(bookItem: BookItemWithBook) {
    this.selectedBookItem == bookItem ? this.unselectBookItem() : this.selectBookItem(bookItem);
  }

  loadValuesToShelfEditForm() {
    this.editShelfForm = new FormGroup({
      name: new FormControl(this.selectedShelf?.name, Validators.required),
    });
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

  openAddRackModal() {
    this.newRackForm = this.formService.createNewRackForm();
    this.modalService.openModal({ title: "CAT.DIALOG.WAREHOUSE.ADD_RACK.TITLE", body: this.addRackDialogBody, form: this.newRackForm, onConfirm: () => this.addRack()})
  }

  openAddShelfModal() {
    this.newShelfForm = this.formService.createNewShelfForm();
    this.modalService.openModal({ title: "CAT.DIALOG.WAREHOUSE.ADD_SHELF.TITLE", body: this.addShelfDialogBody, form: this.newShelfForm, onConfirm: () => this.addShelf()})
  }

  openEditRackModal() {
    this.editRackForm = this.formService.createEditRackForm(this.selectedRack);
    this.modalService.openModal({title: "CAT.DIALOG.WAREHOUSE.EDIT_RACK.TITLE", body: this.editRackDialogBody, form: this.editRackForm, onConfirm: () => this.editRack()});
  }
  
  openEditShelfModal() {
    this.editShelfForm = this.formService.createEditShelfForm(this.selectedShelf);
    this.modalService.openModal({ title: "CAT.DIALOG.WAREHOUSE.EDIT_SHELF.TITLE", body: this.editShelfDialogBody, form: this.editShelfForm, onConfirm: () => this.editShelf()})
  }
  
  openDeleteRackModal() {
    if (!this.selectedRack || this.selectedRack.shelvesCount > 0) {
      this.toastContainer.showError('CAT.TOAST.WAREHOUSE.RACK.DELETE.FAILURE.HAS_BOOKS')
      return;
    }
    this.modalService.openModal({
      title: "CAT.DIALOG.WAREHOUSE.DELETE_RACK.TITLE",
      body: this.translate.instant('CAT.DIALOG.WAREHOUSE.DELETE_RACK.BODY', { item: this.selectedRack?.name }),
      onConfirm: () => this.deleteRack()
    });
  }

  openDeleteShelfModal() {
    if (!this.selectedShelf || this.selectedShelf.bookItemsCount > 0) {
      this.toastContainer.showError('CAT.TOAST.WAREHOUSE.SHELF.DELETE.FAILURE.HAS_BOOKS');
      return;
    }
    this.modalService.openModal({
      title: "CAT.DIALOG.WAREHOUSE.DELETE_SHELF.TITLE",
      body: this.translate.instant('CAT.DIALOG.WAREHOUSE.DELETE_SHELF.BODY', { item: this.selectedShelf?.name }),
      onConfirm: () => this.deleteShelf()
    });
  }

  openMoveBookItemModal() {
    this.moveBookItemForm = this.formService.createMoveItemForm(this.selectedBookItem?.rackId ?? -1, this.selectedBookItem?.shelfId ?? -1);
    this.loadMoveItemModalRacks();
    this.loadMoveItemModalShelves(this.selectedBookItemRackId);
    this.moveBookItemForm.get('rack')?.valueChanges.subscribe({
      next: rackId => {
        this.moveBookItemForm.get('shelf')?.reset();
        this.loadMoveItemModalShelves(rackId);
      }
    });
    this.modalService.openModal({
      title: "CAT.DIALOG.WAREHOUSE.MOVE_BOOK_ITEM.TITLE",
      body: this.moveBookItemDialogBody,
      form: this.moveBookItemForm,
      onConfirm: () => this.editBookItemLocation(this.selectedBookItem?.id ?? -1)
    });
  }

  getRackLabel(racks: Rack[]): string {
    const rack = racks.find(e => e.id == this.moveBookItemForm.get('rack')?.value);
    return rack?.name || this.translate.instant('CAT.DIALOG.WAREHOUSE.MOVE_BOOK_ITEM.SELECT_RACK');
  }

  getShelfLabel(shelves: Shelf[]): string {
    const shelf = shelves.find(e => e.id == this.moveBookItemForm.get('shelf')?.value);
    return shelf?.name || this.translate.instant('CAT.DIALOG.WAREHOUSE.MOVE_BOOK_ITEM.SELECT_SHELF');
  }

  isSelectedRackInMoveItemForm(rack: Rack) {
    return this.moveBookItemForm.get('rack')?.value == rack.id;
  }

  isSelectedShelfInMoveItemForm(shelf: Shelf) {
    return this.moveBookItemForm.get('shelf')?.value == shelf.id;
  }

  loadRackToForm(rack: Rack) {
    this.moveBookItemForm.get('rack')?.setValue(rack.id);
  }

  loadShelfToForm(shelf: Shelf) {
    this.moveBookItemForm.get('shelf')?.setValue(shelf.id);
  }

  private addRack() {
    if(this.newRackForm.invalid) {
      this.toastContainer.showError('CAT.TOAST.FORM_INVALID');
      return
    };
    const newRack: Rack = new Rack();
    newRack.name = this.newRackForm.value.name;
    newRack.location = this.newRackForm.value.location;
    this.warehouseService.addNewRack(newRack).subscribe({
      next: () => {
        this.toastContainer.showSuccess('CAT.TOAST.WAREHOUSE.RACK.CREATE.SUCCESS');
        this.newRackForm.reset();
      },
      error: () => this.toastContainer.showError('CAT.TOAST.WAREHOUSE.RACK.CREATE.FAILURE')
    });
  }

  private addShelf() {
    if(this.newShelfForm.invalid || !this.selectedRack) {
      this.toastContainer.showError('CAT.TOAST.FORM_INVALID')
      return;
    }
    const newShelf: ShelfToSave = new ShelfToSave();
    newShelf.name = this.newShelfForm.value.name;
    newShelf.rackId = this.selectedRack.id;
    this.warehouseService.addNewShelf(newShelf).subscribe({
      next: () => {
        this.toastContainer.showSuccess('CAT.TOAST.WAREHOUSE.SHELF.CREATE.SUCCESS')
        this.newShelfForm.reset();
        if (this.selectedRack) this.selectedRack.shelvesCount += 1;
      },
      error: () => this.toastContainer.showError('CAT.TOAST.WAREHOUSE.SHELF.CREATE.FAILURE')
    });
  }

  private editRack() {
    if (this.editRackForm.invalid || !this.selectedRack) {
      this.toastContainer.showError('CAT.TOAST.WAREHOUSE.RACK.EDIT.FAILURE');
      return
    }
    const rackToUpdate = new Rack()
    rackToUpdate.name = this.editRackForm.value.name;
    rackToUpdate.location = this.editRackForm.value.location;
    this.warehouseService.editRack(this.selectedRack.id, rackToUpdate).subscribe({
      next: updatedRack => {
        this.selectedRack = updatedRack;
        this.toastContainer.showSuccess('CAT.TOAST.WAREHOUSE.RACK.EDIT.SUCCESS');
      },
      error: () => this.toastContainer.showError('CAT.TOAST.WAREHOUSE.RACK.EDIT.FAILURE')
    });
  }

  private editShelf() {
    if (this.editShelfForm.invalid || !this.selectedShelf) {
      this.toastContainer.showError('CAT.TOAST.WAREHOUSE.SHELF.EDIT.FAILURE');
      return
    }
    const shelfToUpdate = new Shelf();
    shelfToUpdate.name = this.editShelfForm.value.name;
    this.warehouseService.editShelf(this.selectedShelf.id, shelfToUpdate).subscribe({
      next: updatedShelf => {
        this.selectedShelf = updatedShelf;
        this.toastContainer.showSuccess('CAT.TOAST.WAREHOUSE.SHELF.EDIT.SUCCESS');
      },
      error: () => this.toastContainer.showError('CAT.TOAST.WAREHOUSE.SHELF.EDIT.FAILURE')
    });
  }

  private editBookItemLocation(bookItemId: number) {
    const newRackId: number = this.moveBookItemForm.get('rack')?.value;
    const newShelfId: number = this.moveBookItemForm.get('shelf')?.value;
    if (newRackId == null || newShelfId == null) return;
    const options = { rackSelected: this.selectedRack != undefined, shelfSelected: this.selectedShelf != undefined }
    this.warehouseService.editBookItemLocation(bookItemId, newRackId, newShelfId, options).subscribe({
      next: updatedBookItem => {
        if (this.selectedBookItem) {
          const rackChanged = updatedBookItem.rackId !== this.selectedBookItem.rackId;
          const shelfChanged = updatedBookItem.shelfId !== this.selectedBookItem.shelfId;
          this.selectedBookItem.rackId = updatedBookItem.rackId;
          this.selectedBookItem.shelfId = updatedBookItem.shelfId;

          const shouldUnselectBookItem =
          (this.isSelectedRack() && this.isSelectedShelf() && (rackChanged || shelfChanged)) ||
          (this.isSelectedRack() && rackChanged) ||
          (this.isSelectedShelf() && (rackChanged || shelfChanged));

          if (shouldUnselectBookItem) {
            this.unselectBookItem()
          }
        }
      }
    });
  }

  private deleteRack() {
    if(!this.selectedRack) return;
    this.warehouseService.deleteRack(this.selectedRack).subscribe({
      next: () => {
        this.toastContainer.showSuccess('CAT.TOAST.WAREHOUSE.RACK.DELETE.SUCCESS');
        this.selectedRack = undefined;
      },
      error: () => this.toastContainer.showError('CAT.TOAST.WAREHOUSE.RACK.DELETE.FAILURE.MAIN')
    })
  }

  private deleteShelf() {
    if(!this.selectedShelf) return;
    this.warehouseService.deleteShelf(this.selectedShelf).subscribe({
      next: () => {
        this.toastContainer.showSuccess('CAT.TOAST.WAREHOUSE.SHELF.DELETE.SUCCESS');
        this.selectedShelf = undefined;
      },
      error: () => this.toastContainer.showError('CAT.TOAST.WAREHOUSE.SHELF.DELETE.FAILURE.MAIN')
    })
  }

  // private openModal(options: { title?: string, body?: any, form?: FormGroup, onConfirm?: () => void}) {
  //   const modalRef = this.modalService.open(ModalDialogComponent, {
  //     title: options?.title,
  //     body: options?.body,
  //     submitBtnDisabled: options?.form ? options?.form.invalid : false,
  //   });

  //   options?.form?.statusChanges.subscribe(() => {
  //     modalRef.instance.submitBtnDisabled = options?.form ? options?.form.invalid : true;
  //   });

  //   modalRef.instance.onConfirm.subscribe(() => {
  //     if (options?.onConfirm) {
  //       options.onConfirm();
  //     }
  //   });
  //   modalRef.instance.close = () => modalRef.destroy();
  // }

  get selectedBookItemRackId(): number {
    return this.selectedBookItem?.rackId ?? -1;
  }

  get selectedBookItemShelfId(): number {
    return this.selectedBookItem?.shelfId ?? -1;
  }

  private loadMoveItemModalRacks(): void {
    this.warehouseService.getRacksList().subscribe({
      next: racks => this.moveBookItemsRacksSubject.next(racks)
    });
  }

  private loadMoveItemModalShelves(rackId: number): void {
    this.warehouseService.getShelvesList({rackId: rackId}).subscribe({
      next: shelves => this.moveBookItemsShelvesSubject.next(shelves)
    })
  }

  private selectRack = (rack: Rack) => this.selectedRack = rack;
  private unselectRack = () => this.selectedRack = undefined;
  private isSelectedRack = () => this.selectedRack != undefined;

  private selectShelf = (shelf: Shelf) => this.selectedShelf = shelf;
  private unselectShelf = () => this.selectedShelf = undefined;
  private isSelectedShelf = () => this.selectedShelf != undefined;

  private selectBookItem = (bookItem: BookItemWithBook) => this.selectedBookItem = bookItem;
  private unselectBookItem = () => this.selectedBookItem = undefined;

  private resestLoadings() {
    this.racksLoading = false;
    this.shelvesLoading = false;
    this.bookItemsLoading = false;
  }
}