import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BookItemListComponent } from './book-item-list/book-item-list.component';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { PaginationComponent } from './pagination/pagination.component';
import { BookListComponent } from './book-list/book-list.component';
import { BookDetailsComponent } from './book-details/book-details.component';
import { HomePageComponent } from './home-page/home-page.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { ProfileDashboardComponent } from './profile-dashboard/profile-dashboard.component';
import { FormsModule } from '@angular/forms';
import { AuthInterceptorProvider } from './services/custom.interceptor';
import { PersonalDetailsComponent } from './profile-dashboard/personal-details/personal-details.component';
import { NotificationsComponent } from './profile-dashboard/notifications/notifications.component';
import { BorrowedItemsComponent } from './profile-dashboard/borrowed-items/borrowed-items.component';
import { OnSiteItemsComponent } from './profile-dashboard/on-site-items/on-site-items.component';
import { RequestedItemsCompletedComponent } from './profile-dashboard/requested-items/requested-items-completed/requested-items-completed.component';
import { RequestedItemsPendingComponent } from './profile-dashboard/requested-items/requested-items-pending/requested-items-pending.component';
import { RequestedItemsUnsentComponent } from './profile-dashboard/requested-items/requested-items-unsent/requested-items-unsent.component';
import { RequestedItemsComponent } from './profile-dashboard/requested-items/requested-items.component';
import { ReservationsComponent } from './profile-dashboard/reservations/reservations.component';
import { RenewableItemsComponent } from './profile-dashboard/renewable-items/renewable-items.component';
import { UserHistoryComponent } from './profile-dashboard/user-history/user-history.component';
import { FeesAccountedComponent } from './profile-dashboard/fees/fees-accounted/fees-accounted.component';
import { FeesNotAccountedComponent } from './profile-dashboard/fees/fees-not-accounted/fees-not-accounted.component';
import { FeesComponent } from './profile-dashboard/fees/fees.component';
import { EditEmailComponent } from './profile-dashboard/edit/edit-email/edit-email.component';
import { EditPasswordComponent } from './profile-dashboard/edit/edit-password/edit-password.component';
import { EditPhoneNumberComponent } from './profile-dashboard/edit/edit-phone-number/edit-phone-number.component';
import { EditComponent } from './profile-dashboard/edit/edit.component';
import { NullPlaceholderPipe } from './shared/pipes/null-placeholder.pipe';
import { EnumNamePipe } from './shared/pipes/enum-name.pipe';
import { WebsocketService } from './services/websocket.service';

@NgModule({
  declarations: [
    AppComponent,
    BookItemListComponent,
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
    EnumNamePipe
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
