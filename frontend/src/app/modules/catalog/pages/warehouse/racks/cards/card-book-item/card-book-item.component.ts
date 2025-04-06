import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BookItem } from '../../../../../../../shared/models/book-item';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-card-book-item',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './card-book-item.component.html',
  styleUrl: './card-book-item.component.css'
})
export class CardBookItemComponent {
  @Input() selectedBookItem: BookItem | undefined;
  @Input() bookItem: BookItem;
  @Output() onSelect = new EventEmitter<BookItem>();

  active(item: BookItem) {
      this.onSelect.emit(item);
  }
}
