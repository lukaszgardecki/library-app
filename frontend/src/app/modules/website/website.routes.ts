import { Routes } from "@angular/router";


export const websiteRoutes: Routes = [
    { path: '', loadComponent: () => import('./pages/home/home.component').then(c => c.HomeComponent) },
    { path: 'about', loadComponent: () => import('./pages/about/about.component').then(c => c.AboutComponent) },
];