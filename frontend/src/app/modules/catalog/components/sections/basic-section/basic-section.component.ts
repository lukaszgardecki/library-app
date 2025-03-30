import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-basic-section',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './basic-section.component.html',
  styleUrl: './basic-section.component.css'
})
export class BasicSectionComponent {
  @Input() name: string;
  @Output() onScroll = new EventEmitter();


  scroll(event: any): void {
    this.onScroll.emit(event)
  }
}
