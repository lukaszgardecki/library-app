import { Component, HostListener, Input } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { TranslationService } from '../../../../../shared/services/translation.service';
import { Chart, registerables, Tooltip, TooltipItem } from 'chart.js';

@Component({
  selector: 'app-fav-genre-chart',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './fav-genre-chart.component.html',
  styleUrl: './fav-genre-chart.component.css'
})
export class FavGenreChartComponent {
  chart: any;
  @Input() data: Map<string, number>;
  private langChangeSubscription: Subscription;

  constructor(private translationService: TranslationService) { }

  ngOnInit(): void {
    Chart.register(...registerables, Tooltip);
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
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'right'
          },
          tooltip: {
            enabled: true,
            callbacks: {
              label: (context: TooltipItem<any>) => {
                const value = context.raw;
                const label = this.translationService.translate('CAT.USER.ACCOUNT.LENDINGS_COUNT');
                return `${label}: ${value}`;
              }
            }
          }
        }
      }
    });
  }

  @HostListener('window:resize')
  onResize(): void {
    if (this.chart) {
      this.chart.resize();
    }
  }
}
