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
    RequestedItemsCompletedComponent
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
    AuthInterceptorProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
