import { CommonModule } from '@angular/common';
import { Component, ElementRef, QueryList, ViewChildren } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-toast-container',
  imports: [CommonModule, TranslateModule],
  templateUrl: './toast-container.component.html',
  styleUrl: './toast-container.component.css'
})
export class ToastContainerComponent {
  toasts: ActiveToast[] = [];
  private toastIdCounter = 0;
  @ViewChildren('toastElement') toastElements!: QueryList<ElementRef>;

  show(type: ToastType) {
    const toast = new ActiveToast(++this.toastIdCounter, type.message, type);
    this.toasts.push(toast);

    const step = 100 / (toast.duration / toast.intervalTime);

    toast.intervalId = setInterval(() => {
      toast.progressWidth -= step;
      if (toast.progressWidth <= 0) {
        toast.progressWidth = 0;
        clearInterval(toast.intervalId);
        this.close(toast); 
      }
    }, toast.intervalTime);
  }

  close(toast: ActiveToast) {
    const index = this.toasts.indexOf(toast);
    const toastElement = this.toastElements.get(index);
    if (toastElement) {
      setTimeout(() => {
        toastElement.nativeElement.classList.add('fadeOut');
        setTimeout(() => {
          toast.isVisible = false;
          toastElement.nativeElement.classList.remove('fadeOut');
          this.toasts = this.toasts.filter(t => t.id !== toast.id);
        }, 500);
      }, 200);
    }
  }
}

class ActiveToast {
  id: number;
  message: string;
  type: ToastType;
  duration = 3000;
  intervalTime = 50;
  progressWidth: number = 100;
  isVisible: boolean = true;
  intervalId: any = null;

  constructor(id: number, message: string, type: ToastType) {
    this.id = id;
    this.message = message;
    this.type = type;
  }
}

export abstract class ToastType {
  message: string;
  abstract readonly icon: string;
  abstract readonly color: string;

  constructor(message: string) {
    this.message = message;
  }
}

export class SuccessToast extends ToastType {
  icon = 'bi-check-circle';
  color = '#28a745';
}

export class ErrorToast extends ToastType {
  icon = 'bi-x-circle';
  color = '#dc3545';
}

export class InfoToast extends ToastType {
  icon = 'bi-info-circle';
  color = '#17a2b8';
}

