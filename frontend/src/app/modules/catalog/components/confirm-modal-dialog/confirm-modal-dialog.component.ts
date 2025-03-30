import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-confirm-modal-dialog',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './confirm-modal-dialog.component.html',
  styleUrl: './confirm-modal-dialog.component.css'
})
export class ConfirmModalDialogComponent {
  @Input() title: string;
  @Input() body: string;
  @Output() onConfirm = new EventEmitter();
}
