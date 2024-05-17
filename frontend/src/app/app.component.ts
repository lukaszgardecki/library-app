import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Library Management System';

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
