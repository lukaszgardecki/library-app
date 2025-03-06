import { Routes } from "@angular/router";
import { AuthGuardComponent as AuthGuard } from "./core/guards/auth-guard.component";

export const catalogRoutes: Routes = [
  { path: 'login', loadComponent: () => import('./pages/login/login.component').then((c) => c.LoginComponent) },
  { path: 'register', loadComponent: () => import('./pages/register/register.component').then((c) => c.RegisterComponent) },
  { path: '', loadComponent: () => import('./catalog.component').then((c) => c.CatalogComponent),
    canActivate: [AuthGuard],
    children: [
      { path: 'dashboard', loadComponent: () => import('./pages/dashboard/dashboard.component').then((c) => c.DashboardComponent) },
      { path: 'notifications', loadComponent: () => import('./pages/notifications/notifications.component').then((c) => c.NotificationsComponent) },
      { path: 'messages', loadComponent: () => import('./pages/messages/messages.component').then((c) => c.MessagesComponent) },
      {
        path: 'users', loadComponent: () => import('./pages/users/users.component').then((c) => c.UsersComponent),
        data: { expectedRoles: ['ADMIN'] }
      },
      { path: 'users/:id', loadComponent: () => import('./pages/user-details/user-details.component').then((c) => c.UserDetailsComponent) },
      { path: 'books', loadComponent: () => import('./pages/books/books.component').then((c) => c.BooksComponent) },
      {
        path: 'cashier', loadComponent: () => import('./pages/cashier/cashier.component').then((c) => c.CashierComponent),
        data: { expectedRoles: ['ADMIN', 'CASHIER'] }
      },
      { 
        path: 'warehouse',
        data: { expectedRoles: ['ADMIN', 'WAREHOUSE'] },
        children: [
          { path: 'transactions', loadComponent: () => import('./pages/warehouse/transactions/transactions.component').then((c) => c.TransactionsComponent) },
          { path: 'history', loadComponent: () => import('./pages/warehouse/history/history.component').then((c) => c.HistoryComponent) },
          { path: 'racks', loadComponent: () => import('./pages/warehouse/racks/racks.component').then((c) => c.RacksComponent) }
        ]
      },
      {
        path: 'reports', loadComponent: () => import('./pages/reports/reports.component').then((c) => c.ReportsComponent),
        data: { expectedRoles: ['ADMIN', 'WAREHOUSE', 'CASHIER'] }
      },
      { path: 'settings', loadComponent: () => import('./pages/settings/settings.component').then((c) => c.SettingsComponent) },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full', },
    ],
  },
];