import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transaction } from '../models/transaction.model'; // ✅ correct

@Injectable({ providedIn: 'root' })
export class TransactionService {

  private baseUrl = 'http://localhost:8080/api/transactions';

  constructor(private http: HttpClient) {}

  getAll(accountNumber: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(
      `${this.baseUrl}/account/${accountNumber}`
    );
  }
  performTransaction(data: any): Observable<any> {
    return this.http.post(this.baseUrl, data);
  }

  getAllTransactions(accountNumber: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(
      `${this.baseUrl}/account/${accountNumber}`
    );
  }

  getStatement(
    accountNumber: string,
    from: string,
    to: string
  ): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(
      `${this.baseUrl}/account/${accountNumber}/statement`,
      { params: { fromDate: from, toDate: to } }
    );
  }
}
