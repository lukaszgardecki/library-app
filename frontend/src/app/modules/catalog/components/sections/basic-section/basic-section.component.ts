import { Component, HostBinding, Input } from '@angular/core';

@Component({
  selector: 'app-basic-section',
  standalone: true,
  imports: [],
  templateUrl: './basic-section.component.html',
  styleUrl: './basic-section.component.css'
})
export class BasicSectionComponent {
  @Input() name: String;
  @HostBinding('class') classList = 'p-2';

}
