import { Injectable } from '@angular/core';
import { ModalDialogComponent } from '../../components/modal-dialog/modal-dialog.component';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  private modalComponent?: ModalDialogComponent;

  register(modalComponent: ModalDialogComponent) {
    this.modalComponent = modalComponent;
  }

  openModal(options: { title?: string; body?: any; form?: FormGroup; onConfirm?: () => void }) {
    if (!this.modalComponent) return;

    this.modalComponent.title = options.title || '';
    this.modalComponent.body = options.body;
    this.modalComponent.submitBtnDisabled = options.form ? options.form.invalid : false;

    options.form?.statusChanges.subscribe(() => {
      this.modalComponent!.submitBtnDisabled = options.form?.invalid || false;
    });

    this.modalComponent.onConfirm = options.onConfirm;
    this.modalComponent.show();
  }
}
