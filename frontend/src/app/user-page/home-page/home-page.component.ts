import { AfterViewInit, Component } from '@angular/core';
declare var $: any; // Deklaracja $ jako zmiennej globalnej


@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements AfterViewInit{

  // NALEŻY ZAINICJOWAĆ SLAJDER PO TYM JAK ANGULAR STWORZY WIDOK,
  // PONIEWAŻ SLIDER JEST DODATKOWO OPAKOWYWANY W DODATKOWY DIV,
  // KTÓREGO NIE MA W SZABLONIE HTML I ANGULAR WYKRYWA BŁĄD,
  // PONIEWAŻ TEMPLATE HTML, KTÓRY MA ZDEFINIOWANY NIE ZGADZA SIĘ
  // Z TEMPLATEM WYGENEROWANYM
  ngAfterViewInit(): void {
    $('.basicslider').flexslider({
      slideshow: true, // Change to "true" to make the slides slide automatically
      animation: "slide",
      animationLoop: false,
      pauseOnHover: true,
      controlNav: false,
      directionNav: true,
      prevText: "Prev",
      nextText: "Next",
      smoothHeight: true,
      start: function() {
        $('body').removeClass('loading');
      }
    });
  }
}
