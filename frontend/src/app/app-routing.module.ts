import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookItemListComponent } from './book-item-list/book-item-list.component';

const routes: Routes = [
  {path: "book-items", component: BookItemListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
