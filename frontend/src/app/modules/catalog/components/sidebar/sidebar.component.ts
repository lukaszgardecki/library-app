import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, TranslateModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  @Input() isActive: boolean = true;
  menuItemGroups: MenuItemGroup[] = [
    { items: [
      { name: 'CAT.SIDEBAR.DASHBOARD', routerLink: '/library-app/dashboard', icon: 'bi-speedometer', selected: false }
    ]},
    { items: [
      { name: 'CAT.SIDEBAR.NOTIFICATIONS', routerLink: '/library-app/notifications', icon: 'bi-bell-fill', selected: false },
      { name: 'CAT.SIDEBAR.MESSAGES', routerLink: '/library-app/messages', icon: 'bi-chat-text', selected: false }
    ]},
    { items: [
      { name: 'CAT.SIDEBAR.USERS', routerLink: '/library-app/users', icon: 'bi-people-fill', selected: false },
      { name: 'CAT.SIDEBAR.BOOKS', routerLink: '/library-app/books', icon: 'bi-book', selected: false },
    ] },
    { items: [
      { name: 'CAT.SIDEBAR.CASHIER', routerLink: '/library-app/cashier', icon: 'bi-cash-coin', selected: false },
      { name: 'CAT.SIDEBAR.WAREHOUSE', routerLink: '/library-app/warehouse', icon: 'bi-stack', selected: false },
      { name: 'CAT.SIDEBAR.REPORTS', routerLink: '/library-app/reports', icon: 'bi-speedometer', selected: false, submenu: [
        { name: 'Reports1', routerLink: '/library-app/reports', selected: false },
        { name: 'Reports2', routerLink: '/library-app/reports', selected: false },
        { name: 'Reports3', routerLink: '/library-app/reports', selected: false },
      ] },
    ] },
    { items: [
      { name: 'CAT.SIDEBAR.SETTINGS', routerLink: '/library-app/settings', icon: 'bi-gear-fill', selected: false },
    ] },
  ];
}

interface SubmenuItem {
  name: string;
  routerLink: string;
  selected: boolean;
}


interface MenuItem {
  name: string;
  icon: string;
  routerLink: string;
  selected: boolean;
  submenu?: SubmenuItem[];
}

interface MenuItemGroup {
  items: MenuItem[];
}
