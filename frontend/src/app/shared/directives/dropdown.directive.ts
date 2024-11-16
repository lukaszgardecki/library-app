import { Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';

@Directive({
  selector: '[dropdown]'
})
export class DropdownDirective {

constructor(private el: ElementRef) { }

 @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event) {
    this.closeAllDropdowns();
  }

  @HostListener('click', ['$event'])
  toggleDropdown(event: MouseEvent) {
    event.stopPropagation();
    const dropdown = this.el.nativeElement.querySelector('.dropdown');
    const isOpen = dropdown.classList.contains('show');
    this.closeAllDropdowns();
    if (isOpen) {
      dropdown.classList.remove('show');
    } else {
      dropdown.classList.add('show');
    }
  }

  private closeAllDropdowns() {
    const openDropdowns = document.querySelectorAll('.dropdown.show');
    openDropdowns.forEach(openDropdown => openDropdown.classList.remove('show'));
  }
}
