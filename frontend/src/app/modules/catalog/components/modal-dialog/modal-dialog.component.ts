import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Modal } from 'bootstrap';

@Component({
  selector: 'app-modal-dialog',
  imports: [CommonModule, TranslateModule],
  templateUrl: './modal-dialog.component.html',
  styleUrl: './modal-dialog.component.css'
})
export class ModalDialogComponent {
  @Input() id: string;
  @Input() title: string;
  @Input() submitBtnDisabled: boolean = false;
  @Output() onConfirm = new EventEmitter();

  @ViewChild('modalRef', { static: true }) modalElement!: ElementRef;
  private modalInstance!: Modal;

  ngOnInit() {
    // to wywala navbar...
    this.modalInstance = new Modal(this.modalElement.nativeElement);
  }

  open() {
    this.modalInstance.show();
  }

  close() {
    this.modalInstance.hide();
  }
}
