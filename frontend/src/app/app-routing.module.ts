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
      {path: "borrowed-items", component: BorrowedItemsComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
