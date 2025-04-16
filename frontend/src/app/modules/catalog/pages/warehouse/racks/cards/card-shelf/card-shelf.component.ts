import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Shelf } from '../../../../../../../shared/models/rack';

@Component({
  selector: 'app-card-shelf',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './card-shelf.component.html',
  styleUrl: './card-shelf.component.css'
})
export class CardShelfComponent {
  @Input() selectedShelf: Shelf | undefined;
  @Input() shelf: Shelf;
  @Output() onSelect = new EventEmitter<Shelf>();

  active(item: Shelf) {
      this.onSelect.emit(item);
  }
}
