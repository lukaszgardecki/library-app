import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
@Component({
  selector: 'app-modal-dialog',
  imports: [CommonModule, TranslateModule],
  templateUrl: './modal-dialog.component.html',
  styleUrl: './modal-dialog.component.css'
})
export class ModalDialogComponent {
  @Input() title: string = '';
  @Input() body: any;
  @Input() submitBtnDisabled: boolean = true;
  @Output() onConfirm = new EventEmitter<void>();
  public close!: () => void;

  confirm() {
    this.onConfirm.emit();
    if (this.close) this.close();
  }

  isString(value: any): boolean {
    return typeof value === 'string';
  }
}
