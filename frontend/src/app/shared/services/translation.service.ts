import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { StorageService } from '../../modules/catalog/core/services/storage.service';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  private currentLangSubject = new BehaviorSubject<string>("en");
  currentLang$ = this.currentLangSubject.asObservable();
  
  onLangChange$: Observable<any> = this.translateService.onLangChange;
  private readonly LANGUAGES: UserLang[] = [
    { name: "English (GB)", short: "en-gb", icon: "gb.svg" },
    { name: "English (US)", short: "en-us", icon: "us.svg" },
    { name: "Polski", short: "pl", icon: "pl.svg" },
    { name: "Українська", short: "uk", icon: "uk.svg" },
    { name: "Deutsch", short: "de", icon: "de.svg" },
    { name: "Español", short: "es", icon: "es.svg" },
    { name: "Čeština", short: "cs", icon: "cs.svg" },
    { name: "Slovenčina", short: "sk", icon: "sk.svg" },
    { name: "Français", short: "fr", icon: "fr.svg" },
    { name: "Italiano", short: "it", icon: "it.svg" }
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

export interface UserLang {
  name: string;
  short: string;
  icon: string;
}