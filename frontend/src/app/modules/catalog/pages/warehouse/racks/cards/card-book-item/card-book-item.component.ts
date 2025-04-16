import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BookItemWithBook } from '../../../../../../../shared/models/book-item';
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
  @Input() selectedBookItem: BookItemWithBook | undefined;
  @Input() bookItem: BookItemWithBook;
  @Output() onSelect = new EventEmitter<BookItemWithBook>();

  active(item: BookItemWithBook) {
      this.onSelect.emit(item);
  }
}
