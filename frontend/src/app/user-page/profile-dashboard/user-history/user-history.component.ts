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
  selectedSortTypeName: string = "Domyślnie";
  selectedPageSize: number;

  name: string = "User history";
  routerLink: string = "history";
  actions = new Array<Action>;
  actionService = inject(ActionService);
  authService = inject(AuthenticationService);
  isLoading = false;
  currentPageNum = 0;
  currentSort = "desc";
  currentType = "ALL"
  actionTypes = [
    {type: this.currentType, name: "All"},
    {type: "LOGIN", name: "Logins"},
    {type: "LOGOUT", name: "Logouts"},
    {type: "LOGIN_FAILED", name: "Failed logins"},
    {type: "REGISTER", name: "Register"},
    {type: "REQUEST_NEW", name: "New requests"},
    {type: "REQUEST_COMPLETED", name: "Completed requests"},
    {type: "REQUEST_CANCEL", name: "Canceled requests"},
    {type: "BOOK_BORROWED", name: "Borrowings"},
    {type: "BOOK_RESERVED_FIRST", name: "Reservations"},
    {type: "BOOK_RESERVED_QUEUE", name: "Reservations - queue"},
    {type: "BOOK_RENEWED", name: "Renewals"},
    {type: "BOOK_RETURNED", name: "Returns"},
    {type: "BOOK_LOST", name: "Lost Documents"},
    {type: "NOTIFICATION_SYSTEM", name: "System notifications"},
    {type: "NOTIFICATION_EMAIL", name: "E-mail notifications"},
    {type: "NOTIFICATION_SMS", name: "SMS notifications"}
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
