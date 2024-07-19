import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UserListComponent } from './user-list/user-list.component';
import { TEXT } from '../shared/messages';
import { TranslationService } from '../services/translation.service';
import { UserLang } from '../shared/user-lang';
import { AuthenticationService } from '../services/authentication.service';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrl: './admin-page.component.css'
})
export class AdminPageComponent implements OnInit {
  TEXT = TEXT;
  @ViewChild('sidebar') sidebar: ElementRef;
  @ViewChild('mainContent') mainContent: ElementRef;
  options = [
    { obj: new AdminDashboardComponent(), icon: 'fa-tachometer-alt', selected: false },
    { obj: new UserListComponent(), icon: 'fa-user', selected: false }
  ];
  
  languages: UserLang[];
  selectedLang: UserLang;

  constructor(
    private langService: TranslationService,
    private authService: AuthenticationService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.languages = this.langService.getAvailableLangs();
    this.selectedLang = this.langService.getUserLanguage();

    const route = this.route.snapshot.firstChild?.routeConfig?.path || '';
    this.options.forEach(el => {
      el.selected = el.obj.routerLink === route || (el.obj.routerLink === '.' && route === '')
    });
  }

  switch(index: number) {
    this.options.forEach((el, i) => el.selected = i === index);
  }

  showSidebar() {
    this.sidebar.nativeElement.classList.toggle('active');
    this.mainContent.nativeElement.classList.toggle('expanded');
  }

  switchLanguage(language: UserLang) {
    this.selectedLang = language;
    this.langService.setLanguage(language.short);
  }

  logout(): void {
    this.authService.logout();
  }
}
