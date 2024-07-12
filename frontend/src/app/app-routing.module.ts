import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './user-page/book-list/book-list.component';
import { BookDetailsComponent } from './user-page/book-details/book-details.component';
import { HomePageComponent } from './user-page/home-page/home-page.component';
import { LoginComponent } from './user-page/login/login.component';
import { ProfileDashboardComponent } from './user-page/profile-dashboard/profile-dashboard.component';
import { PersonalDetailsComponent } from './user-page/profile-dashboard/personal-details/personal-details.component';
import { NotificationsComponent } from './user-page/profile-dashboard/notifications/notifications.component';
import { BorrowedItemsComponent } from './user-page/profile-dashboard/borrowed-items/borrowed-items.component';
import { OnSiteItemsComponent } from './user-page/profile-dashboard/on-site-items/on-site-items.component';
import { RequestedItemsCompletedComponent } from './user-page/profile-dashboard/requested-items/requested-items-completed/requested-items-completed.component';
import { RequestedItemsPendingComponent } from './user-page/profile-dashboard/requested-items/requested-items-pending/requested-items-pending.component';
import { RequestedItemsUnsentComponent } from './user-page/profile-dashboard/requested-items/requested-items-unsent/requested-items-unsent.component';
import { ReservationsComponent } from './user-page/profile-dashboard/reservations/reservations.component';
import { RenewableItemsComponent } from './user-page/profile-dashboard/renewable-items/renewable-items.component';
import { UserHistoryComponent } from './user-page/profile-dashboard/user-history/user-history.component';
import { FeesAccountedComponent } from './user-page/profile-dashboard/fees/fees-accounted/fees-accounted.component';
import { FeesNotAccountedComponent } from './user-page/profile-dashboard/fees/fees-not-accounted/fees-not-accounted.component';
import { EditEmailComponent } from './user-page/profile-dashboard/edit/edit-email/edit-email.component';
import { EditPasswordComponent } from './user-page/profile-dashboard/edit/edit-password/edit-password.component';
import { EditPhoneNumberComponent } from './user-page/profile-dashboard/edit/edit-phone-number/edit-phone-number.component';
import { NotificationDetailsComponent } from './user-page/profile-dashboard/notifications/notification-details/notification-details.component';
import { UserPageComponent } from './user-page/user-page.component';
import { WarehousePageComponent } from './warehouse-page/warehouse-page.component';
import { RoleGuard } from './services/role-guard';
import { NotAuthorizedPageComponent } from './not-authorized-page/not-authorized-page.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { UserListComponent } from './admin-page/user-list/user-list.component';

const routes: Routes = [
  {path: "", component: UserPageComponent, children: [
    {path: "", component: HomePageComponent},
    {path: "login", component: LoginComponent},
    {path: "books", component: BookListComponent},
    {path: "books/:id", component: BookDetailsComponent},
    {
      path: "userprofile",
      component: ProfileDashboardComponent,
      canActivate: [RoleGuard],
      canActivateChild: [RoleGuard],

      children: [
        {path: "details", component: PersonalDetailsComponent},
        {path: "notifications", component: NotificationsComponent},
        {path: "notifications/:id", component: NotificationDetailsComponent},
        {path: "borrowed-items", component: BorrowedItemsComponent},
        {path: "on-site-items", component: OnSiteItemsComponent},

        {path: "requested-items/unsent", component: RequestedItemsUnsentComponent},
        {path: "requested-items/pending", component: RequestedItemsPendingComponent},
        {path: "requested-items/completed", component: RequestedItemsCompletedComponent},
        
        {path: "reservations", component: ReservationsComponent},
        {path: "renewable-items", component: RenewableItemsComponent},
        {path: "history", component: UserHistoryComponent},

        {path: "fees/not-accounted", component: FeesNotAccountedComponent},
        {path: "fees/accounted", component: FeesAccountedComponent},

        {path: "edit/password", component: EditPasswordComponent},
        {path: "edit/email", component: EditEmailComponent},
        {path: "edit/phone", component: EditPhoneNumberComponent},
      ]
    },
    {
      path: "admin",
      component: AdminPageComponent,
      canActivate: [RoleGuard],
      data: { expectedRoles: 'ADMIN'},
      children: [
        {path: "users", component: UserListComponent}
      ]
    }
  ]},
  {
    path: "warehouse",
    component: WarehousePageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: ['ADMIN', 'WAREHOUSE'] }
  },
  {path: "not-authorized", component: NotAuthorizedPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
