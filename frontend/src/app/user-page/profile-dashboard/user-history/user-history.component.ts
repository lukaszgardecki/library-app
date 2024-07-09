import { Component, inject } from '@angular/core';
import { ProfileSetting } from '../profile-dashboard.component';
import { Action } from '../../../models/action';
import { ActionService } from '../../../services/action.service';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'app-user-history',
  templateUrl: './user-history.component.html',
  styleUrl: './user-history.component.css'
})
export class UserHistoryComponent implements ProfileSetting {
  name: string = "PROFILE.USER_HISTORY.NAME";
  routerLink: string = "history";
  actions = new Array<Action>;
  actionService = inject(ActionService);
  authService = inject(AuthenticationService);
  isLoading = false;
  currentPageNum = 0;
  currentSort = "desc";
  currentType = "ALL"
  actionTypes = [
    {type: this.currentType, name: "PROFILE.USER_HISTORY.ACTION_TYPE.ALL"},
    {type: "LOGIN", name: "PROFILE.USER_HISTORY.ACTION_TYPE.LOGIN"},
    {type: "LOGOUT", name: "PROFILE.USER_HISTORY.ACTION_TYPE.LOGOUT"},
    {type: "LOGIN_FAILED", name: "PROFILE.USER_HISTORY.ACTION_TYPE.LOGIN_FAILED"},
    {type: "REGISTER", name: "PROFILE.USER_HISTORY.ACTION_TYPE.REGISTER"},
    {type: "REQUEST_NEW", name: "PROFILE.USER_HISTORY.ACTION_TYPE.REQUEST_NEW"},
    {type: "REQUEST_COMPLETED", name: "PROFILE.USER_HISTORY.ACTION_TYPE.REQUEST_COMPLETED"},
    {type: "REQUEST_CANCEL", name: "PROFILE.USER_HISTORY.ACTION_TYPE.REQUEST_CANCEL"},
    {type: "BOOK_BORROWED", name: "PROFILE.USER_HISTORY.ACTION_TYPE.BOOK_BORROWED"},
    {type: "BOOK_AVAILABLE_TO_BORROW", name: "PROFILE.USER_HISTORY.ACTION_TYPE.BOOK_AVAILABLE_TO_BORROW"},
    {type: "BOOK_RESERVED_FIRST", name: "PROFILE.USER_HISTORY.ACTION_TYPE.BOOK_RESERVED_FIRST"},
    {type: "BOOK_RESERVED_QUEUE", name: "PROFILE.USER_HISTORY.ACTION_TYPE.BOOK_RESERVED_QUEUE"},
    {type: "BOOK_RENEWED", name: "PROFILE.USER_HISTORY.ACTION_TYPE.BOOK_RENEWED"},
    {type: "BOOK_RETURNED", name: "PROFILE.USER_HISTORY.ACTION_TYPE.BOOK_RETURNED"},
    {type: "BOOK_LOST", name: "PROFILE.USER_HISTORY.ACTION_TYPE.BOOK_LOST"},
    {type: "NOTIFICATION_SYSTEM", name: "PROFILE.USER_HISTORY.ACTION_TYPE.NOTIFICATION_SYSTEM"},
    {type: "NOTIFICATION_EMAIL", name: "PROFILE.USER_HISTORY.ACTION_TYPE.NOTIFICATION_EMAIL"},
    {type: "NOTIFICATION_SMS", name: "PROFILE.USER_HISTORY.ACTION_TYPE.NOTIFICATION_SMS"}
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
