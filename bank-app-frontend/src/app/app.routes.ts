import { Routes } from '@angular/router';
import { CreateCustomerComponent } from './pages/customer/create-customer/create-customer.component';
import { CreateAccountComponent } from './pages/account/create-account/create-account';

export const routes: Routes = [
  { path: '', redirectTo: 'create-customer', pathMatch: 'full' },
  { path: 'create-customer', component: CreateCustomerComponent },
  { path: 'create-account', component: CreateAccountComponent }
];
