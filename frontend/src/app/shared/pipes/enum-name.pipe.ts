import { Pipe, PipeTransform } from '@angular/core';
import { Gender } from '../../modules/catalog/shared/enums/gender.enum';
import { CardStatus } from '../../modules/catalog/shared/enums/card-status.enum';
import { AccountStatus } from '../../modules/catalog/shared/enums/account-status.enum';
import { Role } from '../../modules/catalog/shared/enums/role.enum';
import { BookItemLoanStatus } from '../../modules/catalog/shared/enums/book-item-loan-status';
import { BookItemRequestStatus } from '../../modules/catalog/shared/enums/book-item-request-status';
import { BookFormat } from '../enums/book-format';

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

  private loanStatusMap: { [key in BookItemLoanStatus]: string } = {
    [BookItemLoanStatus.CURRENT]: 'CAT.LOAN.STATUS.CURRENT',
    [BookItemLoanStatus.COMPLETED]: 'CAT.LOAN.STATUS.COMPLETED'
  }

  private requestStatusMap: { [key in BookItemRequestStatus]: string} = {
    [BookItemRequestStatus.COMPLETED]: 'CAT.REQUEST.STATUS.COMPLETED',
    [BookItemRequestStatus.READY]: 'CAT.REQUEST.STATUS.READY',
    [BookItemRequestStatus.IN_PROGRESS]: 'CAT.REQUEST.STATUS.IN_PROGRESS',
    [BookItemRequestStatus.PENDING]: 'CAT.REQUEST.STATUS.PENDING',
    [BookItemRequestStatus.RESERVED]: 'CAT.REQUEST.STATUS.RESERVED',
    [BookItemRequestStatus.CANCELED]: 'CAT.REQUEST.STATUS.CANCELED'
  }

  private bookItemFormatMap: { [key in BookFormat]: string } = {
    [BookFormat.HARDCOVER]: 'CAT.BOOK_ITEM.FORMAT.HARDCOVER',
    [BookFormat.PAPERBACK]: 'CAT.BOOK_ITEM.FORMAT.PAPERBACK',
    [BookFormat.AUDIO_BOOK]: 'CAT.BOOK_ITEM.FORMAT.AUDIO_BOOK',
    [BookFormat.EBOOK]: 'CAT.BOOK_ITEM.FORMAT.EBOOK',
    [BookFormat.NEWSPAPER]: 'CAT.BOOK_ITEM.FORMAT.NEWSPAPER',
    [BookFormat.MAGAZINE]: 'CAT.BOOK_ITEM.FORMAT.MAGAZINE',
    [BookFormat.JOURNAL]: 'CAT.BOOK_ITEM.FORMAT.JOURNAL'
  }

  transform(value?: any, type?: string): string {
    if (!type) return value;

    switch(type) {
      case "account": return this.accountStatusMap[value as AccountStatus] || 'Unknown account status';
      case "card": return this.cardStatusMap[value as CardStatus] || 'Unknown card status';
      case "gender": return this.genderMap[value as Gender] || 'Unknown gender';
      case "role": return this.rolesMap[value as Role] || 'Unknown role';
      case "book-item-loan-status": return this.loanStatusMap[value as BookItemLoanStatus] || 'Unknown book item loan status';
      case "book-item-request-status": return this.requestStatusMap[value as BookItemRequestStatus] || 'Unknown book item request status';
      case "book-format": return this.bookItemFormatMap[value as BookFormat] || 'Unknown book item format';
      default: return value;
    }
  }
}
