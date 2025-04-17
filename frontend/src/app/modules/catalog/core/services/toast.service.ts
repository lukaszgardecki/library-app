import { Injectable } from '@angular/core';
import { ToastContainerComponent } from '../../components/toast/toast-container.component';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  private container?: ToastContainerComponent;

  register(container: ToastContainerComponent) {
    this.container = container;
  }

  showSuccess(message: string) {
    this.container?.showSuccess(message);
  }

  showError(message: string) {
    this.container?.showError(message);
  }

  showInfo(message: string) {
    this.container?.showInfo(message);
  }
}
