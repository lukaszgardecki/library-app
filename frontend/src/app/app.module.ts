import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { PaginationComponent } from './pagination/pagination.component';
import { BookListComponent } from './user-page/book-list/book-list.component';
import { BookDetailsComponent } from './user-page/book-details/book-details.component';
import { HomePageComponent } from './user-page/home-page/home-page.component';
import { FooterComponent } from './user-page/footer/footer.component';
import { LoginComponent } from './user-page/login/login.component';
import { ProfileDashboardComponent } from './user-page/profile-dashboard/profile-dashboard.component';
import { FormsModule } from '@angular/forms';
import { AuthInterceptorProvider } from './services/custom.interceptor';
import { PersonalDetailsComponent } from './user-page/profile-dashboard/personal-details/personal-details.component';
import { NotificationsComponent } from './user-page/profile-dashboard/notifications/notifications.component';
import { BorrowedItemsComponent } from './user-page/borrowed-items/borrowed-items.component';
import { OnSiteItemsComponent } from './user-page/profile-dashboard/on-site-items/on-site-items.component';
import { RequestedItemsCompletedComponent } from './user-page/profile-dashboard/requested-items/requested-items-completed/requested-items-completed.component';
import { RequestedItemsPendingComponent } from './user-page/profile-dashboard/requested-items/requested-items-pending/requested-items-pending.component';
import { RequestedItemsUnsentComponent } from './user-page/profile-dashboard/requested-items/requested-items-unsent/requested-items-unsent.component';
import { RequestedItemsComponent } from './user-page/profile-dashboard/requested-items/requested-items.component';
import { ReservationsComponent } from './user-page/profile-dashboard/reservations/reservations.component';
import { RenewableItemsComponent } from './user-page/profile-dashboard/renewable-items/renewable-items.component';
import { UserHistoryComponent } from './user-page/profile-dashboard/user-history/user-history.component';
import { FeesAccountedComponent } from './user-page/profile-dashboard/fees/fees-accounted/fees-accounted.component';
import { FeesNotAccountedComponent } from './user-page/profile-dashboard/fees/fees-not-accounted/fees-not-accounted.component';
import { FeesComponent } from './user-page/profile-dashboard/fees/fees.component';
import { EditEmailComponent } from './user-page/profile-dashboard/edit/edit-email/edit-email.component';
import { EditPasswordComponent } from './user-page/profile-dashboard/edit/edit-password/edit-password.component';
import { EditPhoneNumberComponent } from './user-page/profile-dashboard/edit/edit-phone-number/edit-phone-number.component';
import { EditComponent } from './user-page/profile-dashboard/edit/edit.component';
import { NullPlaceholderPipe } from './shared/pipes/null-placeholder.pipe';
import { EnumNamePipe } from './shared/pipes/enum-name.pipe';
import { WebsocketService } from './services/websocket.service';
import { NotificationDetailsComponent } from './user-page/profile-dashboard/notifications/notification-details/notification-details.component';
import { UserPageComponent } from './user-page/user-page.component';
import { WarehousePageComponent } from './warehouse-page/warehouse-page.component';
import { NotAuthorizedPageComponent } from './not-authorized-page/not-authorized-page.component';

@NgModule({
  declarations: [
    AppComponent,
    PaginationComponent,
    BookListComponent,
    BookDetailsComponent,
    HomePageComponent,
    FooterComponent,
    LoginComponent,
    ProfileDashboardComponent,
    PersonalDetailsComponent,
    NotificationsComponent,
    BorrowedItemsComponent,
    OnSiteItemsComponent,
    RequestedItemsComponent,
    RequestedItemsUnsentComponent,
    RequestedItemsPendingComponent,
    RequestedItemsCompletedComponent,
    ReservationsComponent,
    RenewableItemsComponent,
    UserHistoryComponent,
    FeesComponent,
    FeesNotAccountedComponent,
    FeesAccountedComponent,
    EditComponent,
    EditPasswordComponent,
    EditEmailComponent,
    EditPhoneNumberComponent,
    NullPlaceholderPipe,
    EnumNamePipe,
    NotificationDetailsComponent,
    UserPageComponent,
    WarehousePageComponent,
    NotAuthorizedPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch()),
    AuthInterceptorProvider,
    WebsocketService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
