import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-confirmation-toast',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './confirmation-toast.component.html',
  styleUrl: './confirmation-toast.component.css'
})
export class ConfirmationToastComponent {
  @ViewChild('toastElement') toastEl!: ElementRef;
  isVisible: boolean = false;
  progressWidth: number = 100;

  private duration = 3000;
  private intervalTime = 50;
  private intervalId: any;

  showToast() {
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
