import { Component, Input } from '@angular/core';
import { ProfileSetting } from '../user-page/profile-dashboard/profile-dashboard.component';

@Component({
  selector: 'app-side-accordion',
  templateUrl: './side-accordion.component.html',
  styleUrl: './side-accordion.component.css'
})
export class SideAccordionComponent {

  @Input()
  options: ProfileSetting[];
}
