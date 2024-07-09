import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'enumName'
})
export class EnumNamePipe implements PipeTransform {
  private gender: { [key: string]: string } = {
    MALE: 'PROFILE.DETAILS.GENDER.MALE',
    FEMALE: 'PROFILE.DETAILS.GENDER.FEMALE',
    OTHER: 'PROFILE.DETAILS.GENDER.OTHER'
  };
  private cardStatus: { [key: string]: string } = {
    ACTIVE: 'PROFILE.DETAILS.CARD.STATUS.ACTIVE',
    INACTIVE: 'PROFILE.DETAILS.CARD.STATUS.INACTIVE',
    LOST: 'PROFILE.DETAILS.CARD.STATUS.LOST'
  }

  private accountStatus: { [key: string]: string } = {
    ACTIVE: 'PROFILE.DETAILS.ACCOUNT.STATUS.ACTIVE',
    INACTIVE: 'PROFILE.DETAILS.ACCOUNT.STATUS.INACTIVE',
    CLOSED: 'PROFILE.DETAILS.ACCOUNT.STATUS.CLOSED',
    SUSPENDED: 'PROFILE.DETAILS.ACCOUNT.STATUS.SUSPENDED',
    PENDING: 'PROFILE.DETAILS.ACCOUNT.STATUS.PENDING',
  }

  transform(value?: string, type?: string): string | undefined {
    if(value == undefined) return undefined;

    switch(type) {
      case "account": return this.accountStatus[value];
      case "card": return this.cardStatus[value];
      case "gender": return this.gender[value];
      default: return "Unknown value";
    }
  }
}
