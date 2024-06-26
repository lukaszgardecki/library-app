import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'enumName'
})
export class EnumNamePipe implements PipeTransform {
  private enumName: { [key: string]: string } = {

    //gender
    MALE: 'Mężczyzna',
    FEMALE: 'Kobieta',
    OTHER: 'Inne',

    //account / card statuses
    ACTIVE: 'Aktywne',
    INACTIVE: 'Nieaktywne',
    SUSPENDED: 'Zawieszone',
    CLOSED: 'Zamknięte',
    PENDING: 'Oczekujące',
    LOST: 'Zgubiona'
  };

  transform(value: string | undefined): string | undefined {
    if(value == undefined) return undefined;
    return this.enumName[value] || 'Nieznana wartość';
  }
}
