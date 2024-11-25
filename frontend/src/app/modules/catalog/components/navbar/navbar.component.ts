import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslationService, UserLang } from '../../../../shared/services/translation.service';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { AuthenticationService } from '../../core/services/authentication.service';
import { EnumNamePipe } from '../../../../shared/pipes/enum-name.pipe';
import { NullPlaceholderPipe } from '../../../../shared/pipes/null-placeholder.pipe';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, TranslateModule, RouterModule , EnumNamePipe, NullPlaceholderPipe],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  languages: UserLang[];
  selectedLang: UserLang;

  @Input() isSidebarActive: boolean = true;
  @Input() isFullscreen: boolean = false;
  @Output() toggleSidebar = new EventEmitter<void>();
  @Output() toggleFullscreen = new EventEmitter<void>();

  user$ = this.authService.currentUser$;

  constructor(
    private authService: AuthenticationService,
    private langService: TranslationService,
  ) { }

  ngOnInit(): void {
    this.languages = this.langService.getAvailableLangs();
    this.selectedLang = this.langService.getUserLanguage();
  }

  switchLanguage(language: UserLang) {
    this.selectedLang = language;
    this.langService.setLanguage(language.short);
  }

  logout(): void {
    this.authService.logout();
  }

  toggleSidebarVisibility() {
    this.toggleSidebar.emit();
  }

  toggleFullScreen() {
    this.toggleFullscreen.emit();
  }
}
