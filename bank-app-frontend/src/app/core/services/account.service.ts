import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AccountService {

  private baseUrl = 'http://localhost:8080/api/accounts';

  constructor(private http: HttpClient) {}

  createAccount(data: any): Observable<any> {
    return this.http.post(this.baseUrl, data);
  }
  getBalance(accountNumber: string) {
  return this.http.get<any>(
    `${this.baseUrl}/${accountNumber}/balance`
  );
}

}
