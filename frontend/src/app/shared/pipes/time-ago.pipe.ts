import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timeAgo',
  standalone: true,
  pure: false
})
export class TimeAgoPipe implements PipeTransform {

transform(value: Date): { value: number, unit: string } {
    if (!value) return { value: 0, unit: '' };

    const now = new Date().getTime();
    const timestamp = new Date(value).getTime();
    const diffInSeconds = Math.floor((now - timestamp) / 1000);
    const diffInMinutes = Math.floor(diffInSeconds / 60);
    const diffInHours = Math.floor(diffInMinutes / 60);

    if (diffInSeconds < 60) {
      return { value: diffInSeconds, unit: 'DATA.TIME.SECONDS_AGO' };
    } else if (diffInMinutes < 60) {
      return { value: diffInMinutes, unit: 'DATA.TIME.MINUTES_AGO' };
    } else {
      return { value: diffInHours, unit: 'DATA.TIME.HOURS_AGO' };
    }
  }

}
