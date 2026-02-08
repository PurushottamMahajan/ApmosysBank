export const API_BASE_URL = 'http://localhost:8080/api';
import { environment } from '../../../environments/environment';

export const API_ENDPOINTS = {
  CUSTOMER: `${environment.apiUrl}/customers`,
  ACCOUNT: `${environment.apiUrl}/accounts`,
  TRANSACTION: `${environment.apiUrl}/transactions`
};
