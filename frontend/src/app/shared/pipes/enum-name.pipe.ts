import { Pipe, PipeTransform } from '@angular/core';
import { Gender } from '../../modules/catalog/shared/enums/gender.enum';
import { CardStatus } from '../../modules/catalog/shared/enums/card-status.enum';
import { AccountStatus } from '../../modules/catalog/shared/enums/account-status.enum';
import { Role } from '../../modules/catalog/shared/enums/role.enum';
import { LendingStatus } from '../../modules/catalog/shared/enums/loan-status.enum';

@Pipe({
  name: 'enumName',
  standalone: true
})
export class EnumNamePipe implements PipeTransform {

  private genderMap: { [key in Gender]: string } = {
    [Gender.MALE]: 'CAT.USER.DETAILS.GENDER.MALE',
    [Gender.FEMALE]: 'CAT.USER.DETAILS.GENDER.FEMALE',
    [Gender.OTHER]: 'CAT.USER.DETAILS.GENDER.OTHER'
  };

  private cardStatusMap: { [key in CardStatus]: string } = {
    [CardStatus.ACTIVE]: 'CAT.USER.CARD.STATUS.ACTIVE',
    [CardStatus.INACTIVE]: 'CAT.USER.CARD.STATUS.INACTIVE',
    [CardStatus.LOST]: 'CAT.USER.CARD.STATUS.LOST'
  };

  private accountStatusMap: { [key in AccountStatus]: string } = {
    [AccountStatus.ACTIVE]: 'CAT.USER.ACCOUNT.STATUS.ACTIVE',
    [AccountStatus.INACTIVE]: 'CAT.USER.ACCOUNT.STATUS.INACTIVE',
    [AccountStatus.CLOSED]: 'CAT.USER.ACCOUNT.STATUS.CLOSED',
    [AccountStatus.SUSPENDED]: 'CAT.USER.ACCOUNT.STATUS.SUSPENDED',
    [AccountStatus.PENDING]: 'CAT.USER.ACCOUNT.STATUS.PENDING'
  };

  private rolesMap: { [key in Role]: string } = {
    [Role.USER]: 'CAT.USER.ROLE.USER',
    [Role.ADMIN]: 'CAT.USER.ROLE.ADMIN',
    [Role.CASHIER]: 'CAT.USER.ROLE.CASHIER',
    [Role.WAREHOUSE]: 'CAT.USER.ROLE.WAREHOUSE'
  };

  private lendingStatusMap: { [key in LendingStatus]: string } = {
    [LendingStatus.CURRENT]: 'CAT.LENDING.STATUS.CURRENT',
    [LendingStatus.COMPLETED]: 'CAT.LENDING.STATUS.COMPLETED'
  }

  transform(value?: any, type?: string): string {
    if (!type) return value;

    switch(type) {
      case "account": return this.accountStatusMap[value as AccountStatus] || 'Unknown account status';
      case "card": return this.cardStatusMap[value as CardStatus] || 'Unknown card status';
      case "gender": return this.genderMap[value as Gender] || 'Unknown gender';
      case "role": return this.rolesMap[value as Role] || 'Unknown role';
      case "lendingStatus": return this.lendingStatusMap[value as LendingStatus] || 'Unknown lending status';
      default: return value;
    }
  }
}
