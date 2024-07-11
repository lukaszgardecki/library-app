import { Component, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { Action } from '../../../models/action';
import { ActionService } from '../../../services/action.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { TEXT } from '../../../shared/messages';

@Component({
  selector: 'app-user-history',
  templateUrl: './user-history.component.html',
  styleUrl: './user-history.component.css'
})
export class UserHistoryComponent implements ProfileSetting {
  TEXT = TEXT;
  name: string = TEXT.PROFILE_USER_HISTORY_NAME;
  routerLink: string = "history";
  actions = new Array<Action>;
  actionService = inject(ActionService);
  authService = inject(AuthenticationService);
  isLoading = false;
  currentPageNum = 0;
  currentSort = "desc";
  currentType = "ALL"
  actionTypes = [
    {type: this.currentType, name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_ALL},
    {type: "LOGIN", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_LOGIN},
    {type: "LOGOUT", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_LOGOUT},
    {type: "LOGIN_FAILED", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_LOGIN_FAILED},
    {type: "REGISTER", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_REGISTER},
    {type: "REQUEST_NEW", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_REQUEST_NEW},
    {type: "REQUEST_COMPLETED", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_REQUEST_COMPLETED},
    {type: "REQUEST_CANCEL", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_REQUEST_CANCEL},
    {type: "BOOK_BORROWED", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_BOOK_BORROWED},
    {type: "BOOK_AVAILABLE_TO_BORROW", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_BOOK_AVAILABLE_TO_BORROW},
    {type: "BOOK_RESERVED_FIRST", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_BOOK_RESERVED_FIRST},
    {type: "BOOK_RESERVED_QUEUE", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_BOOK_RESERVED_QUEUE},
    {type: "BOOK_RENEWED", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_BOOK_RENEWED},
    {type: "BOOK_RETURNED", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_BOOK_RETURNED},
    {type: "BOOK_LOST", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_BOOK_LOST},
    {type: "NOTIFICATION_SYSTEM", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_NOTIFICATION_SYSTEM},
    {type: "NOTIFICATION_EMAIL", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_NOTIFICATION_EMAIL},
    {type: "NOTIFICATION_SMS", name: TEXT.PROFILE_USER_HISTORY_ACTION_TYPE_NOTIFICATION_SMS}
  ]

  ngOnInit(): void {
    this.fetchActionPage(this.currentPageNum, this.currentSort, this.currentType);

    window.addEventListener('scroll', () => {
      this.onScroll();
    });
  }

  onScroll(): void {
    const bottomElement = document.getElementById('bottom');
    if (!bottomElement) return;

    const rect = bottomElement.getBoundingClientRect();
    const bottomElementTop = rect.top;
    const bottomElementHeight = rect.height;
    const windowHeight = window.innerHeight;
    const isBottomElementOnScreen = bottomElementTop + bottomElementHeight <= windowHeight;

    if (isBottomElementOnScreen && !this.isLoading) {
        this.isLoading = true;
        this.fetchNextActionPage();
    }
  }

  onSortChange(event: Event): void {
    const order = (event.target as HTMLInputElement).value;
    this.currentSort = order;
    this.fetchActionPage(0, order, this.currentType)
  }

  onGroupChange(event: Event): void {
    const type = (event.target as HTMLInputElement).value;
    this.currentType = type;
    this.fetchActionPage(0, this.currentSort, type);
  }

  private fetchActionPage(page: number, sort: string, type: string): void {
    const userId = this.authService.currentUserId;
    this.actionService.getActionsByUserId(userId, page, sort, type).subscribe({
      next: actionPage => {
        if (actionPage._embedded) {
          this.currentPageNum = actionPage.page.number;
          this.actions = actionPage._embedded.actionDtoList;
          this.isLoading = false;
        } else {
          this.actions = [];
        }
      }
    });
  }

  private fetchNextActionPage(): void {
    const userId = this.authService.currentUserId;
    this.actionService.getActionsByUserId(userId, this.currentPageNum + 1, this.currentSort, this.currentType).subscribe({
      next: actionPage => {
        if (actionPage._embedded) {
          this.currentPageNum = actionPage.page.number;
          this.actions.push (...actionPage._embedded.actionDtoList);
          this.isLoading = false;
        } 
      }
    });
  }
}
