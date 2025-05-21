import { Component, HostListener, Input } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { TranslationService } from '../../../../../shared/services/translation.service';
import { Chart, registerables, Tooltip, TooltipItem } from 'chart.js';


@Component({
  selector: 'app-weekly-activity-chart',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './weekly-activity-chart.component.html',
  styleUrl: './weekly-activity-chart.component.css'
})
export class WeeklyActivityChartComponent {
  chart: any;
  @Input() newLoans: Map<string, number>;
  @Input() returns: Map<string, number>;
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
    if (this.chart) {
      this.chart.destroy();
    }

    const order = this.getOrder();
    const loans = this.getOrderedValuesFromMap(this.newLoans, order);
    const returns = this.getOrderedValuesFromMap(this.returns, order);
    let dayLabels = order.map(day => this.getDayShort(day));
    let dayNames = order.map(day => this.getDayName(day));

    this.chart = new Chart('weeklyActivityChart', {
      type: 'line',
      data: {
        labels: dayLabels,
        datasets: [
          {
            label: this.translationService.translate('CAT.USER.ACCOUNT.LENDINGS'),
            data: loans,
            tension: 0.4,
            borderColor: '#0d6efd',
            backgroundColor: 'rgba(13, 110, 253, 0.25)',
            borderWidth: 2,
            fill: true,
          },
          {
            label: this.translationService.translate('CAT.USER.ACCOUNT.RETURNS'),
            data: returns,
            tension: 0.4,
            borderColor: '#198754',
            backgroundColor: 'rgba(25, 135, 84, 0.25)',
            borderWidth: 2,
            fill: true,
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          tooltip: {
            mode: 'index',
            intersect: false,
            callbacks: {
              title: (context: TooltipItem<any>[]) => {
                return dayNames[context[0].dataIndex];
              }
            },
          }
        },
        interaction: {
          mode: 'index',
          intersect: false,
        },
        scales: {
          x: {
            grid: {
              display: false,
            },
          },
        },
      },
      plugins: [
        {
          id: 'verticalLine',
          beforeDraw: (chart) => {
            const activeElements = chart?.tooltip?.getActiveElements();
            if (activeElements && activeElements.length) {
              const ctx = chart.ctx;
              const activePoint = activeElements[0].element;
              const x = activePoint.x;

              const yScale = chart.scales['y'];
              const bottomY = yScale.getPixelForValue(yScale.min);
              const topY = yScale.getPixelForValue(yScale.max);

              ctx.save();
              ctx.setLineDash([7, 7]);
              ctx.beginPath();
              ctx.moveTo(x, bottomY);
              ctx.lineTo(x, topY);
              ctx.lineWidth = 1;
              ctx.strokeStyle = '#6c757d';
              ctx.stroke();
              ctx.restore();
            }
          },
        },
      ],
    });
  }

  private getOrder(): number[] {
    const now = new Date().getDay();
    const days: number[] = [1, 2, 3, 4, 5, 6, 7];
    let firstPart = days.slice(now)
    let secondPart = days.slice(0, now)
    return firstPart.concat(secondPart);
  }

  private getOrderedValuesFromMap(data: Map<string, number>, order: number[]): number[] {
    const orderedValues: number[] = [];

    for (const orderValue of order) {
      const keyAsString = orderValue.toString();
      const value = data.get(keyAsString);
      orderedValues.push(value ?? -1);
    }

    return orderedValues;
  }

  private getDayName(day: number): string {
    let dayName = '';
    switch (day) {
      case 1: dayName = this.translationService.translate('DATA.DAY.MON.NAME'); break;
      case 2: dayName = this.translationService.translate('DATA.DAY.TUE.NAME'); break;
      case 3: dayName = this.translationService.translate('DATA.DAY.WED.NAME'); break;
      case 4: dayName = this.translationService.translate('DATA.DAY.THU.NAME'); break;
      case 5: dayName = this.translationService.translate('DATA.DAY.FRI.NAME'); break;
      case 6: dayName = this.translationService.translate('DATA.DAY.SAT.NAME'); break;
      case 7: dayName = this.translationService.translate('DATA.DAY.SUN.NAME'); break;
      default: dayName = ''; break;
    }
    return dayName;
  }

  private getDayShort(day: number): string {
    let dayShort = '';
    switch (day) {
      case 1: dayShort = this.translationService.translate('DATA.DAY.MON.SHORT'); break;
      case 2: dayShort = this.translationService.translate('DATA.DAY.TUE.SHORT'); break;
      case 3: dayShort = this.translationService.translate('DATA.DAY.WED.SHORT'); break;
      case 4: dayShort = this.translationService.translate('DATA.DAY.THU.SHORT'); break;
      case 5: dayShort = this.translationService.translate('DATA.DAY.FRI.SHORT'); break;
      case 6: dayShort = this.translationService.translate('DATA.DAY.SAT.SHORT'); break;
      case 7: dayShort = this.translationService.translate('DATA.DAY.SUN.SHORT'); break;
      default: dayShort = ''; break;
    }
    return dayShort;
  }

  @HostListener('window:resize')
  onResize(): void {
    if (this.chart) {
      this.chart.resize();
    }
  }
}
