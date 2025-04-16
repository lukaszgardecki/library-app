import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  private readonly ACCESS_TOKEN_NAME = "access_token";
  private readonly REFRESH_TOKEN_NAME = "refresh_token";
  // private readonly LOANED_IDS = "loaned_ids";
  // private readonly RESERVED_IDS = "reserved_ids";
  public static readonly SELECTED_LANG = "selected_language";

  constructor() { }

  saveTokens(accessToken: string, refreshToken: string) {
    sessionStorage.setItem(this.ACCESS_TOKEN_NAME, accessToken);
    sessionStorage.setItem(this.REFRESH_TOKEN_NAME, refreshToken);
  }

  // saveLoanedIds(ids: number[]) {
  //   sessionStorage.setItem(this.LOANED_IDS, JSON.stringify(ids));
  // }

  // saveReservedIds(ids: number[]) {
  //   sessionStorage.setItem(this.RESERVED_IDS, JSON.stringify(ids));
  // }

  saveUserLanguage(lang: string) {
    localStorage.setItem(StorageService.SELECTED_LANG, lang);
  }

  getAccessToken() {
    return this.getFromSessionStorage(this.ACCESS_TOKEN_NAME);
  }

  getRefreshToken() {
    return this.getFromSessionStorage(this.REFRESH_TOKEN_NAME);
  }

  // getLoanedIds() {
  //   const storedArray = this.getFromSessionStorage(this.LOANED_IDS);
  //   return storedArray ? JSON.parse(storedArray) : [];
  // }

  // getReservedIds() {
  //   const storedArray = this.getFromSessionStorage(this.RESERVED_IDS);
  //   return storedArray ? JSON.parse(storedArray) : [];
  // }

  getUserLanguage() {
    return this.getFromLocalStorage(StorageService.SELECTED_LANG);
  }

  clearStorage() {
    sessionStorage.removeItem(this.ACCESS_TOKEN_NAME);
    sessionStorage.removeItem(this.REFRESH_TOKEN_NAME);
    // sessionStorage.removeItem(this.LOANED_IDS);
    // sessionStorage.removeItem(this.RESERVED_IDS);
  }

  private getFromLocalStorage(name: string) {
    if (typeof window !== 'undefined') {
      return localStorage.getItem(name);
    }
    return null;
  }
  
  private getFromSessionStorage(name: string) {
    if (typeof window !== 'undefined') {
      return sessionStorage.getItem(name);
    }
    return null;
  }
}
