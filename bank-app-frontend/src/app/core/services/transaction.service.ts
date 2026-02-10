import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TransactionService {

  // ✅ Backend runs on 8081
  private baseUrl = 'http://localhost:8081/api/transactions';

  constructor(private http: HttpClient) {}

  // 🔹 DEBIT / CREDIT
  performTransaction(data: any): Observable<any> {
    return this.http.post(this.baseUrl, data);
  }

  // 🔹 ALL TRANSACTIONS
  getAllTransactions(accountNumber: string): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.baseUrl}/account/${accountNumber}`
    );
  }

  // 🔹 STATEMENT BETWEEN DATES
  getStatement(
    accountNumber: string,
    fromDate: string,
    toDate: string
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.baseUrl}/account/${accountNumber}/statement`,
      {
        params: {
          fromDate,
          toDate
        }
      }
    );
  }
}
  