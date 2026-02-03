import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from '../models/account.model';
import { API_BASE_URL } from './api.config';

@Injectable({ providedIn: 'root' })
export class AccountService {

  private url = `${API_BASE_URL}/accounts`;

  constructor(private http: HttpClient) {}

  createAccount(account: Account) {
    return this.http.post<Account>(this.url, account);
  }

  getAccountsByCustomer(customerId: number) {
    return this.http.get<Account[]>(`${this.url}/customer/${customerId}`);
  }
}
