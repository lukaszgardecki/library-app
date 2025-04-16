import { Component, Input } from '@angular/core';
import { TranslationService } from '../../../../../shared/services/translation.service';
import { Subscription } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { Chart, registerables, Tooltip, TooltipItem } from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';


@Component({
  selector: 'app-users-age-groups-chart',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './users-age-groups-chart.component.html',
  styleUrl: './users-age-groups-chart.component.css'
})
export class UsersAgeGroupsChartComponent {
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

    this.chart = new Chart('ageGroupsChart', {
      type: 'pie',
      data: {
        labels: genreLabels,
        datasets: [{
          data: genreData,
          backgroundColor: colors,
          borderColor: colors.map(color => color.replace('70%', '90%')),
          borderWidth: 1
        }]
      },
      plugins: [ChartDataLabels],
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
              title: (context: TooltipItem<any>[]) => {
                const value = context[0].label;
                const title = this.translationService.translate('CAT.STATS.USERS_AGE_GROUPS.LABEL');
                return `${title}: ${value}`;
              },
              label: (context: any) => {
                const value = context.raw;
                const total = context.dataset.data.reduce((sum: number, val: number) => sum + val, 0);
                const percentage = ((value / total) * 100).toFixed(1) + '%';
                const label = this.translationService.translate('CAT.STATS.AMOUNT');
                return `${label}: ${value} (${percentage})`;
              }
            }
          },
          datalabels: {
            formatter: (value: number, context: any) => {
              const total = context.chart.data.datasets[0].data.reduce((sum: number, val: number) => sum + val, 0);
              const percentage = ((value / total) * 100).toFixed(1) + '%';
              return percentage;
            },
            color: '#000',
            font: {
              weight: 'bold',
            },
            anchor: 'center',
            align: 'end',
            offset: 10,
            clamp: true,
          }
        }
      }
    });
  }
}
