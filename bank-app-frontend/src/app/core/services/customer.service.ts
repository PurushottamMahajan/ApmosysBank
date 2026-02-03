import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Customer } from '../models/customer.model';
import { API_BASE_URL } from './api.config';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private url = `${API_BASE_URL}/customers`;

  constructor(private http: HttpClient) {}

  createCustomer(customer: Customer) {
    return this.http.post<Customer>(this.url, customer);
  }

  getAllCustomers() {
    return this.http.get<Customer[]>(this.url);
  }

  getCustomerById(id: number) {
    return this.http.get<Customer>(`${this.url}/${id}`);
  }
}
    