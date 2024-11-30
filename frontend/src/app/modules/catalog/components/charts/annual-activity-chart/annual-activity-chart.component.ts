import { Component, HostListener, Input } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Chart, registerables, Tooltip, TooltipItem } from 'chart.js';
import { Subscription } from 'rxjs';
import { TranslationService } from '../../../../../shared/services/translation.service';

@Component({
  selector: 'app-annual-activity-chart',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './annual-activity-chart.component.html',
  styleUrl: './annual-activity-chart.component.css'
})
export class AnnualActivityChartComponent {
  chart: any;
  @Input() data: Array<number>;
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
    let chartLabel = this.translationService.translate('CAT.USER.ACCOUNT.LENDINGS_ANNUAL')
    let monthLabels = this.getMonthLabels();

    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart('annualActivityChart', {
      type: 'bar',
      data: {
        labels: monthLabels,
        datasets: [{
          label: chartLabel,
          data: this.data,
          backgroundColor: (context: any) => {
            const chart = context.chart;
            const { ctx, chartArea } = chart;
            if (!chartArea) {
              return 'rgba(75, 192, 192, 0.2)';
            }
            const gradient = ctx.createLinearGradient(chartArea.left, chartArea.top, chartArea.right, chartArea.bottom);
            gradient.addColorStop(0, '#2196f3');
            gradient.addColorStop(1, '#1976d2');
            return gradient;
          }
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              display: true,
              stepSize: 2
            }
          },
          x: {
            grid: {
              display: false
            },
          }
        },
        plugins: {
          legend: {
            display: false
          },
          tooltip: {
            enabled: true,
            callbacks: {
              label: (context: TooltipItem<any>) => {
                const index = context.dataIndex;
                const value = context.raw;
                const label = this.getMonthName(index);
                return `${label}: ${value}`;
              }
            }
          }
        }
      }
    });
  }

  private getMonthLabels(): string[] {
    return this.getLast12MonthsNums().map(month => this.getMonthShort(month));
  }

  private getMonthName(monthNum: number): string {
    let monthName = '';
    switch (monthNum) {
      case 1: monthName = this.translationService.translate('DATA.MONTH.JAN.NAME'); break;
      case 2: monthName = this.translationService.translate('DATA.MONTH.FEB.NAME'); break;
      case 3: monthName = this.translationService.translate('DATA.MONTH.MAR.NAME'); break;
      case 4: monthName = this.translationService.translate('DATA.MONTH.APR.NAME'); break;
      case 5: monthName = this.translationService.translate('DATA.MONTH.MAI.NAME'); break;
      case 6: monthName = this.translationService.translate('DATA.MONTH.JUN.NAME'); break;
      case 7: monthName = this.translationService.translate('DATA.MONTH.JUL.NAME'); break;
      case 8: monthName = this.translationService.translate('DATA.MONTH.AUG.NAME'); break;
      case 9: monthName = this.translationService.translate('DATA.MONTH.SEP.NAME'); break;
      case 10: monthName = this.translationService.translate('DATA.MONTH.OCT.NAME'); break;
      case 11: monthName = this.translationService.translate('DATA.MONTH.NOV.NAME'); break;
      case 0: monthName = this.translationService.translate('DATA.MONTH.DEC.NAME'); break;
      default: monthName = ''; break;
    }
    return monthName;
  }

  private getMonthShort(monthNum: number): string {
    let monthShort = '';
    switch (monthNum) {
      case 1: monthShort = this.translationService.translate('DATA.MONTH.JAN.SHORT'); break;
      case 2: monthShort = this.translationService.translate('DATA.MONTH.FEB.SHORT'); break;
      case 3: monthShort = this.translationService.translate('DATA.MONTH.MAR.SHORT'); break;
      case 4: monthShort = this.translationService.translate('DATA.MONTH.APR.SHORT'); break;
      case 5: monthShort = this.translationService.translate('DATA.MONTH.MAI.SHORT'); break;
      case 6: monthShort = this.translationService.translate('DATA.MONTH.JUN.SHORT'); break;
      case 7: monthShort = this.translationService.translate('DATA.MONTH.JUL.SHORT'); break;
      case 8: monthShort = this.translationService.translate('DATA.MONTH.AUG.SHORT'); break;
      case 9: monthShort = this.translationService.translate('DATA.MONTH.SEP.SHORT'); break;
      case 10: monthShort = this.translationService.translate('DATA.MONTH.OCT.SHORT'); break;
      case 11: monthShort = this.translationService.translate('DATA.MONTH.NOV.SHORT'); break;
      case 12: monthShort = this.translationService.translate('DATA.MONTH.DEC.SHORT'); break;
      default: monthShort = ''; break;
    }
    return monthShort;
  }

  private getLast12MonthsNums(): number[] {
    const now = new Date().getMonth() + 1; // 0-indexed
    const months: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    let firstPart = months.slice(now)
    let secondPart = months.slice(0, now)
    return firstPart.concat(secondPart);
  }

  @HostListener('window:resize')
  onResize(): void {
    if (this.chart) {
      this.chart.resize();
    }
  }
}
