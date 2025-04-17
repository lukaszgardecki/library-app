import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { ModalService } from '../../core/services/modal.service';
@Component({
  selector: 'app-modal-dialog',
  imports: [CommonModule, TranslateModule],
  templateUrl: './modal-dialog.component.html',
  styleUrl: './modal-dialog.component.css'
})
export class ModalDialogComponent {

  @Input() title = '';
  @Input() body: any;
  submitBtnDisabled = true;
  onConfirm: (() => void) | undefined = undefined;

  visible = false;

  constructor(private modalService: ModalService) {}

  ngOnInit() {
    this.modalService.register(this);
  }

  show() {
    this.visible = true;
  }

  close() {
    this.visible = false;
  }

  confirm() {
    if (this.onConfirm) this.onConfirm();
    this.close();
  }

  isString(value: any): boolean {
    return typeof value === 'string';
  }
}
