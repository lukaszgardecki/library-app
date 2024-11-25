import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule, TranslateModule, FormsModule, RouterModule, SidebarComponent, NavbarComponent],
  templateUrl: './catalog.component.html',
  styleUrl: './catalog.component.css'
})
export class CatalogComponent {
  isSidebarActive = true;
  isFullscreen = false;
  
  toggleSidebar() {
    this.isSidebarActive = !this.isSidebarActive;
  }

  toggleFullScreen(): void {
    if (document.fullscreenEnabled) {
      if (document.fullscreenElement) {
        this.exitFullscreen();
        this.isFullscreen = false;
      } else {
        this.enterFullscreen();
        this.isFullscreen = true;
      }
    }
  }

  private enterFullscreen(): void {
    const docElement = document.documentElement;
    if (docElement.requestFullscreen) {
      docElement.requestFullscreen();
    }
  }

  private exitFullscreen(): void {
    if (document.exitFullscreen) {
      document.exitFullscreen();
    }
  }
}