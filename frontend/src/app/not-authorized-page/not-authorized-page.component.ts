import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { StorageService } from '../services/storage.service';
import { TEXT } from '../shared/messages';

@Component({
  selector: 'app-not-authorized-page',
  templateUrl: './not-authorized-page.component.html',
  styleUrl: './not-authorized-page.component.css',
})
export class NotAuthorizedPageComponent {
  TEXT = TEXT;

  constructor(private translate: TranslateService) {
    translate.use(localStorage.getItem(StorageService.SELECTED_LANG) || "en");
  }
}
