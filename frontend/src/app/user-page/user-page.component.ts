import { Component, ElementRef, Renderer2 } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';
import { TranslationService } from '../services/translation.service';
import { TEXT } from '../shared/messages';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrl: './user-page.component.css'
})
export class UserPageComponent {
  TEXT = TEXT;
  isLoggedIn: boolean;
  title = 'Library Management System';
  languages: { name: string, short: string }[];
  selectedLangName: string;

  constructor(
    private authService: AuthenticationService,
    private langService: TranslationService,
    private renderer: Renderer2,
    private el: ElementRef,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
    });
    this.languages = this.langService.getAvailableLangs();
    this.selectedLangName = this.langService.getUserLanguage().name;
  }

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  logout(): void {
    this.authService.logout();
  }

  hasPermissionToWarehouse(): boolean {
    return this.authService.hasUserPermissionToWarehouse();
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  onNavigate(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const value = target.value;
    if (value) {
      this.router.navigate([value]);
    }
  }

  dropdown() {
    const dropdownElement = this.el.nativeElement.querySelector('.dropdown-menu');
    if (dropdownElement.classList.contains('opened')) {
      this.renderer.removeClass(dropdownElement, 'opened');
    } else {
      this.renderer.addClass(dropdownElement, 'opened');
    }
  }

  switchLanguage(language: { name: string, short: string }) {
    this.selectedLangName = language.name;
    this.langService.setLanguage(language.short);
  }
}
