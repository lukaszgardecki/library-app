import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  private readonly ACCESS_TOKEN_NAME = "access_token";
  private readonly REFRESH_TOKEN_NAME = "refresh_token";
  private readonly LOANED_IDS = "loaned_ids";
  private readonly RESERVED_IDS = "reserved_ids";

  constructor() { }

  saveTokens(accessToken: string, refreshToken: string) {
    sessionStorage.setItem(this.ACCESS_TOKEN_NAME, accessToken);
    sessionStorage.setItem(this.REFRESH_TOKEN_NAME, refreshToken);
  }

  saveLoanedIds(ids: number[]) {
    sessionStorage.setItem(this.LOANED_IDS, JSON.stringify(ids));
  }

  saveReservedIds(ids: number[]) {
    sessionStorage.setItem(this.RESERVED_IDS, JSON.stringify(ids));
  }

  getAccessToken() {
    return this.getFromStorage(this.ACCESS_TOKEN_NAME);
  }

  getRefreshToken() {
    return this.getFromStorage(this.REFRESH_TOKEN_NAME);
  }

  getLoanedIds() {
    const storedArray = this.getFromStorage(this.LOANED_IDS);
    return storedArray ? JSON.parse(storedArray) : [];
  }

  getReservedIds() {
    const storedArray = this.getFromStorage(this.RESERVED_IDS);
    return storedArray ? JSON.parse(storedArray) : [];
  }

  clearStorage() {
    sessionStorage.removeItem(this.ACCESS_TOKEN_NAME);
    sessionStorage.removeItem(this.REFRESH_TOKEN_NAME);
    sessionStorage.removeItem(this.LOANED_IDS);
    sessionStorage.removeItem(this.RESERVED_IDS);
  }
  
  private getFromStorage(tokenName: string) {
    if (typeof window !== 'undefined') {
      return sessionStorage.getItem(tokenName);
    }
    return null;
  }
}
