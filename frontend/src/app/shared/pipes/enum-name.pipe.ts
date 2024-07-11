import { Pipe, PipeTransform } from '@angular/core';
import { TEXT } from '../messages';

@Pipe({
  name: 'enumName'
})
export class EnumNamePipe implements PipeTransform {
  TEXT = TEXT;
  private gender: { [key: string]: string } = {
    MALE: TEXT.PROFILE_DETAILS_GENDER_MALE,
    FEMALE: TEXT.PROFILE_DETAILS_GENDER_FEMALE,
    OTHER: TEXT.PROFILE_DETAILS_GENDER_OTHER
  };
  private cardStatus: { [key: string]: string } = {
    ACTIVE: TEXT.PROFILE_DETAILS_CARD_STATUS_ACTIVE,
    INACTIVE: TEXT.PROFILE_DETAILS_CARD_STATUS_INACTIVE,
    LOST: TEXT.PROFILE_DETAILS_CARD_STATUS_LOST
  }

  private accountStatus: { [key: string]: string } = {
    ACTIVE: TEXT.PROFILE_DETAILS_ACCOUNT_STATUS_ACTIVE,
    INACTIVE: TEXT.PROFILE_DETAILS_ACCOUNT_STATUS_INACTIVE,
    CLOSED: TEXT.PROFILE_DETAILS_ACCOUNT_STATUS_CLOSED,
    SUSPENDED: TEXT.PROFILE_DETAILS_ACCOUNT_STATUS_SUSPENDED,
    PENDING: TEXT.PROFILE_DETAILS_ACCOUNT_STATUS_PENDING,
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
