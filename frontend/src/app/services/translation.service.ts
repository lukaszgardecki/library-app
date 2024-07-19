import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { StorageService } from './storage.service';
import { UserLang } from '../shared/user-lang';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  private readonly LANGUAGES: UserLang[] = [
    new UserLang("English", "en", "flag-en-gb.png"),
    new UserLang("Polski", "pl", "flag-pl.png"),
    new UserLang("Українська", "uk", "flag-uk.png"),
    new UserLang("Deutsch", "de", "flag-de.png"),
    new UserLang("Español", "es", "flag-es.png"),
    new UserLang("Čeština", "cs", "flag-cs.png"),
    new UserLang("Slovenčina", "sk", "flag-sk.png")
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
