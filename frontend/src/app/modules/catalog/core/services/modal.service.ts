import { ComponentRef, Injectable, Type, ViewContainerRef } from '@angular/core';
import { ModalDialogComponent } from '../../components/modal-dialog/modal-dialog.component';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  private viewContainerRef?: ViewContainerRef;

  registerViewContainer(vc: ViewContainerRef) {
    this.viewContainerRef = vc;
  }

  openModal(options: { title?: string, body?: any, form?: FormGroup, onConfirm?: () => void}) {
    const modalRef = this.open(ModalDialogComponent, {
      title: options?.title,
      body: options?.body,
      submitBtnDisabled: options?.form ? options?.form.invalid : false,
    });

    options?.form?.statusChanges.subscribe(() => {
      modalRef.instance.submitBtnDisabled = options?.form ? options?.form.invalid : true;
    });

    modalRef.instance.onConfirm.subscribe(() => {
      if (options?.onConfirm) {
        options.onConfirm();
      }
    });
    modalRef.instance.close = () => modalRef.destroy();
  }

  private open<T extends object>(component: Type<T>, inputs?: Partial<T>): ComponentRef<T> {
    if (!this.viewContainerRef) {
      throw new Error('Modal container not registered.');
    }

    const componentRef = this.viewContainerRef.createComponent(component);

    if (inputs) {
      Object.assign(componentRef.instance, inputs);
    }

    const cleanup = () => componentRef.destroy();
    const instance = componentRef.instance as any;
    instance.closeModal = cleanup;

    return componentRef;
  }
}
