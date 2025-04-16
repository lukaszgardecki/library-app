import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Rack } from '../../../../../../../shared/models/rack';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-card-rack',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './card-rack.component.html',
  styleUrl: './card-rack.component.css'
})
export class CardRackComponent {
  @Input() selectedRack: Rack | undefined;
  @Input() rack: Rack;
  @Output() onSelect = new EventEmitter<Rack>();

  active(item: Rack) {
      this.onSelect.emit(item);
  }
}
