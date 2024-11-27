import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'library-app', pathMatch: 'full' },
  { path: 'library-app', loadChildren: () => import('./modules/catalog/catalog.routes').then(m => m.catalogRoutes) }
];