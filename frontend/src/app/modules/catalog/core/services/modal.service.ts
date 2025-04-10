import { ComponentRef, Injectable, Type, ViewContainerRef } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  private viewContainerRef?: ViewContainerRef;

  registerViewContainer(vc: ViewContainerRef) {
    this.viewContainerRef = vc;
  }

  open<T extends object>(component: Type<T>, inputs?: Partial<T>): ComponentRef<T> {
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
