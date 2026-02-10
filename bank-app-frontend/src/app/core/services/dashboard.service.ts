import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable({ providedIn: 'root' })
export class DashboardService {

  private baseUrl = 'http://localhost:8081/api/dashboard';

  constructor(private http: HttpClient) {}

  getDashboard(accountNumber: string) {
    return this.http.get<any>(`${this.baseUrl}/${accountNumber}`);
  }
}
