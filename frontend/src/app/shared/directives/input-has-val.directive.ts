import { Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';

@Directive({
  selector: '[hasVal]',
  standalone: true
})
export class InputHasValDirective {
  constructor(private el: ElementRef, private renderer: Renderer2) {}

  @HostListener('blur') onBlur() {
    this.toggleClass();
  }

  @HostListener('input') onInput() {
    this.toggleClass();
  }

  private toggleClass() {
    const inputElement = this.el.nativeElement;
    if (inputElement.value.trim() !== '') {
      this.renderer.addClass(inputElement, 'has-val');
    } else {
      this.renderer.removeClass(inputElement, 'has-val');
    }
  }
}
