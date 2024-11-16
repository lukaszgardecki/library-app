import { Pipe, PipeTransform } from '@angular/core';
import { TEXT } from '../messages';
import { AccountStatus, Gender, Role } from '../../models/user-details';
import { CardStatus } from '../../models/library-card';

@Pipe({
  name: 'enumName'
})
export class EnumNamePipe implements PipeTransform {
  TEXT = TEXT;

  private genderMap: { [key in Gender]: string } = {
    [Gender.MALE]: TEXT.PROFILE_DETAILS_GENDER_MALE,
    [Gender.FEMALE]: TEXT.PROFILE_DETAILS_GENDER_FEMALE,
    [Gender.OTHER]: TEXT.PROFILE_DETAILS_GENDER_OTHER
  };

  private cardStatusMap: { [key in CardStatus]: string } = {
    [CardStatus.ACTIVE]: TEXT.PROFILE_DETAILS_CARD_STATUS_ACTIVE,
    [CardStatus.INACTIVE]: TEXT.PROFILE_DETAILS_CARD_STATUS_INACTIVE,
    [CardStatus.LOST]: TEXT.PROFILE_DETAILS_CARD_STATUS_LOST
  };

  private accountStatusMap: { [key in AccountStatus]: string } = {
    [AccountStatus.ACTIVE]: TEXT.PROFILE_DETAILS_ACCOUNT_STATUS_ACTIVE,
    [AccountStatus.INACTIVE]: TEXT.PROFILE_DETAILS_ACCOUNT_STATUS_INACTIVE,
    [AccountStatus.CLOSED]: TEXT.PROFILE_DETAILS_ACCOUNT_STATUS_CLOSED,
    [AccountStatus.SUSPENDED]: TEXT.PROFILE_DETAILS_ACCOUNT_STATUS_SUSPENDED,
    [AccountStatus.PENDING]: TEXT.PROFILE_DETAILS_ACCOUNT_STATUS_PENDING
  };

  private rolesMap: { [key in Role]: string } = {
    [Role.USER]: TEXT.PROFILE_DETAILS_ACCOUNT_ROLE_USER,
    [Role.ADMIN]: TEXT.PROFILE_DETAILS_ACCOUNT_ROLE_ADMIN,
    [Role.CASHIER]: TEXT.PROFILE_DETAILS_ACCOUNT_ROLE_CASHIER,
    [Role.WAREHOUSE]: TEXT.PROFILE_DETAILS_ACCOUNT_ROLE_WAREHOUSE
  };

  transform(value?: any, type?: string): string | undefined {
    if (!value) return undefined;

    switch(type) {
      case "account": return this.accountStatusMap[value as AccountStatus] || 'Unknown account status';
      case "card": return this.cardStatusMap[value as CardStatus] || 'Unknown card status';
      case "gender": return this.genderMap[value as Gender] || 'Unknown gender';
      case "role": return this.rolesMap[value as Role] || 'Unknown role';
      default: return "Unknown value";
    }
    return 'Unknown value';
  }
}
