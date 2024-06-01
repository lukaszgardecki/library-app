import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookItemListComponent } from './book-item-list/book-item-list.component';
import { BookListComponent } from './book-list/book-list.component';
import { BookDetailsComponent } from './book-details/book-details.component';
import { HomePageComponent } from './home-page/home-page.component';
import { LoginComponent } from './login/login.component';
import { ProfileDashboardComponent } from './profile-dashboard/profile-dashboard.component';
import { PersonalDetailsComponent } from './profile-dashboard/personal-details/personal-details.component';
import { NotificationsComponent } from './profile-dashboard/notifications/notifications.component';
import { BorrowedItemsComponent } from './profile-dashboard/borrowed-items/borrowed-items.component';
import { OnSiteItemsComponent } from './profile-dashboard/on-site-items/on-site-items.component';
import { RequestedItemsComponent } from './profile-dashboard/requested-items/requested-items.component';
import { RequestedItemsCompletedComponent } from './profile-dashboard/requested-items/requested-items-completed/requested-items-completed.component';
import { RequestedItemsPendingComponent } from './profile-dashboard/requested-items/requested-items-pending/requested-items-pending.component';
import { RequestedItemsUnsentComponent } from './profile-dashboard/requested-items/requested-items-unsent/requested-items-unsent.component';
import { ReservationsComponent } from './profile-dashboard/reservations/reservations.component';

const routes: Routes = [
  {path: "", component: HomePageComponent},
  {path: "login", component: LoginComponent},
  {path: "book-items", component: BookItemListComponent},
  {path: "books", component: BookListComponent},
  {path: "books/:id", component: BookDetailsComponent},
  {
    path: "userprofile",
    component: ProfileDashboardComponent,
    children: [
      {path: "details", component: PersonalDetailsComponent},
      {path: "notifications", component: NotificationsComponent},
      {path: "borrowed-items", component: BorrowedItemsComponent},
      {path: "on-site-items", component: OnSiteItemsComponent},
      {
        path: "requested-items", 
        component: RequestedItemsComponent, 
        children: [
          {path: "unsent", component: RequestedItemsUnsentComponent},
          {path: "pending", component: RequestedItemsPendingComponent},
          {path: "completed", component: RequestedItemsCompletedComponent},
        ]
      },
      {path: "reservations", component: ReservationsComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
