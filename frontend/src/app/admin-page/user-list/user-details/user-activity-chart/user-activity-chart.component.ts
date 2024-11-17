import { Component, inject, Input } from '@angular/core';
import { Chart, ChartArea, registerables, Tooltip, TooltipItem } from 'chart.js';
import { TEXT } from '../../../../shared/messages';
import { TranslationService } from '../../../../services/translation.service';
import { Subscription } from 'rxjs';

Chart.register(...registerables, Tooltip);

@Component({
  selector: 'app-user-activity-chart',
  templateUrl: './user-activity-chart.component.html',
  styleUrl: './user-activity-chart.component.css'
})
export class UserActivityChartComponent  {
  TEXT = TEXT;
  activityChart: any;
  translationService = inject(TranslationService)
  @Input() data: Array<number>;
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
    let chartLabel = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_ANNUAL_LENDING_ACTIVITY)
    let monthLabels = this.getMonthLabels();

    if (this.activityChart) {
      this.activityChart.destroy();
    }

    this.activityChart = new Chart('activityChart', {
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
    const months = this.getLast12MonthsNums();
    const monthLabels = months.map(month => {
      let monthName = this.getMonthName(month);
      return monthName.length > 3 ? monthName.substring(0, 3) : monthName;
    });
    return monthLabels;
  }

  private getMonthName(monthNum: number): string {
    let monthName = '';
    switch (monthNum) {
      case 1: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_JAN); break;
      case 2: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_FEB); break;
      case 3: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_MAR); break;
      case 4: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_APR); break;
      case 5: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_MAI); break;
      case 6: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_JUN); break;
      case 7: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_JUL); break;
      case 8: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_AUG); break;
      case 9: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_SEP); break;
      case 10: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_OCT); break;
      case 11: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_NOV); break;
      case 12: monthName = this.translationService.translate(TEXT.ADMIN_USER_DETAILS_MONTH_DEC); break;
      default: monthName = ''; break;
    }
    return monthName;
  }

  private getLast12MonthsNums(): number[] {
    const now = new Date().getMonth() + 1; // 0-indexed
    const months: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    let firstPart = months.slice(now)
    let secondPart = months.slice(0, now)
    return firstPart.concat(secondPart);
  }

}



