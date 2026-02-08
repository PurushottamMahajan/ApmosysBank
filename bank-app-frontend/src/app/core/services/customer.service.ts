import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../models/customer.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private baseUrl = 'http://localhost:8081/api/customers';

  constructor(private http: HttpClient) {}

  createCustomer(customer: Customer): Observable<any> {
    return this.http.post(this.baseUrl, customer);
  }
}
