import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { StorageService } from './storage.service';
import { UserLang } from '../shared/user-lang';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  private currentLangSubject = new BehaviorSubject<string>("en");
  currentLang$ = this.currentLangSubject.asObservable();
  onLangChange$: Observable<any> = this.translateService.onLangChange;
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
    const lang = this.storage.getUserLanguage() || "en";
    this.translateService.use(lang);
    this.translateService.setDefaultLang(lang);
    this.currentLangSubject.next(lang);
  }

  setLanguage(lang: string) {
    this.translateService.use(lang);
    this.storage.saveUserLanguage(lang);
    this.currentLangSubject.next(lang);
  }

  getUserLanguage() {
    const userLang = this.storage.getUserLanguage() || "en";
    return this.LANGUAGES.find(el => el.short == userLang) || this.LANGUAGES[0];
  }

  getAvailableLangs() {
    return this.LANGUAGES;
  }

  translate(path: string): string {
    return this.translateService.instant(path);
  }
}
