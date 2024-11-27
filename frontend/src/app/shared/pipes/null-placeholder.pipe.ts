import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'nullPlaceholder',
  standalone: true
})
export class NullPlaceholderPipe implements PipeTransform {

  transform(value: any, placeholder: string = '-'): any {
    return value === null || value === undefined ? placeholder : value;
  }
}
