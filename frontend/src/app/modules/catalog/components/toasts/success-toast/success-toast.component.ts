import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-success-toast',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './success-toast.component.html',
  styleUrl: './success-toast.component.css'
})
export class SuccessToastComponent {
  @ViewChild('toastElement') toastEl!: ElementRef;
  isVisible: boolean = false;
  progressWidth: number = 100;
  message: string = '';

  private duration = 3000;
  private intervalTime = 50;
  private intervalId: any;

  showToast(message: string) {
    this.message = message;
    if (this.intervalId) clearInterval(this.intervalId)
    this.isVisible = true;
    this.progressWidth = 100;

    const step = 100 / (this.duration / this.intervalTime);

    this.intervalId = setInterval(() => {
      this.progressWidth -= step;
      if (this.progressWidth <= 0) {
        this.progressWidth = 0;
        clearInterval(this.intervalId);
        this.closeToast(); 
      }
    }, this.intervalTime);
  }

  closeToast() {
    setTimeout(() => {
      this.toastEl.nativeElement.classList.add('fadeOut');
      setTimeout(() => {
        this.isVisible = false;
        this.toastEl.nativeElement.classList.remove('fadeOut');
      }, 500);
    }, 200);
  }
}
