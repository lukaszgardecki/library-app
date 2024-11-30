import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-top-cities',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './top-cities.component.html',
  styleUrl: './top-cities.component.css'
})
export class TopCitiesComponent {
  @Input() columns: string[] = [];
  @Input() data: Map<string, number>;
  cities: string[] = [];
  values: number[] = [];


  ngOnInit(): void {
    const sortedEntries = Array.from(this.data.entries()).sort((a, b) => b[1] - a[1]);
    this.cities = sortedEntries.map(entry => entry[0]);
    this.values = sortedEntries.map(entry => entry[1]);
  }
}
