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
import { AuthInterceptorProvider } from './services/custom.interceptor';

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
    ProfileDashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch()),
    AuthInterceptorProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
