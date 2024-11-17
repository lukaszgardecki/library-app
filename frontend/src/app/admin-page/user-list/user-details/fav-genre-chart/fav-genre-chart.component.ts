import { Component, inject, Input } from '@angular/core';
import { TEXT } from '../../../../shared/messages';
import { TranslationService } from '../../../../services/translation.service';
import { Subscription } from 'rxjs';
import { Chart, registerables, Tooltip, TooltipItem } from 'chart.js';

Chart.register(...registerables, Tooltip);

@Component({
  selector: 'app-fav-genre-chart',
  templateUrl: './fav-genre-chart.component.html',
  styleUrl: './fav-genre-chart.component.css'
})
export class FavGenreChartComponent {
  TEXT = TEXT;
  chart: any;
  translationService = inject(TranslationService)
  @Input() data: Map<string, number>;
  private langChangeSubscription: Subscription;

  ngOnInit(): void {
    this.createChart();
    this.langChangeSubscription = this.translationService.onLangChange$.subscribe(onLangChange => {
      if (onLangChange){
        this.createChart();
      }
    })
  }

  ngOnDestroy() {
    if (this.langChangeSubscription) {
      this.langChangeSubscription.unsubscribe();
    }
  }

  createChart() {
    const genreLabels = Array.from(this.data.keys());
    const genreData = Array.from(this.data.values());
    const colors = genreLabels.map((_, index) => {
      return `hsl(${(index * 360) / genreLabels.length}, 70%, 50%)`;
    });

    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart('genreChart', {
      type: 'doughnut',
      data: {
        labels: genreLabels,
        datasets: [{
          data: genreData,
          backgroundColor: colors,
          borderColor: colors.map(color => color.replace('70%', '90%')),
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'bottom'
          },
          tooltip: {
            enabled: true,
            callbacks: {
              label: (context: TooltipItem<any>) => {
                const value = context.raw;
                const label = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_CHART_LENDINGS_COUNT);
                return `${label}: ${value}`;
              }
            }
          }
        }
      }
    });
  }
}
