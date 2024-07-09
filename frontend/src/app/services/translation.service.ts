import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  private readonly LANGUAGES = [
    { name: "English", short: "en" },
    { name: "Polski", short: "pl" }
  ];

  constructor(
    private translateService: TranslateService,
    private storage: StorageService
  ) {
    const lang = this.storage.getUserLanguage();
    this.translateService.use(lang || "en");
    this.translateService.setDefaultLang(lang || "en");
  }

  setLanguage(lang: string) {
    this.translateService.use(lang);
    this.storage.saveUserLanguage(lang);
  }

  getUserLanguage() {
    const userLang = this.storage.getUserLanguage() || "en";
    return this.LANGUAGES.find(el => el.short == userLang) || this.LANGUAGES[0];
  }

  getAvailableLangs() {
    return this.LANGUAGES;
  }
}
