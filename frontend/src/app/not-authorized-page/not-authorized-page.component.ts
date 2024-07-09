import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-not-authorized-page',
  templateUrl: './not-authorized-page.component.html',
  styleUrl: './not-authorized-page.component.css',
})
export class NotAuthorizedPageComponent {

  constructor(private translate: TranslateService) {
    translate.use(localStorage.getItem(StorageService.SELECTED_LANG) || "en");
  }
}
