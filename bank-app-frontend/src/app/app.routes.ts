import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [

  { path: '', redirectTo: 'login', pathMatch: 'full' },

  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login.component')
        .then(m => m.LoginComponent)
  },

  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/dashboard/dashboard.component')
        .then(m => m.DashboardComponent)
  },

  {
    path: 'customer',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/customer/create-customer/create-customer.component')
        .then(m => m.CreateCustomerComponent)
  },

  {
    path: 'account',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/account/create-account/create-account.component')
        .then(m => m.CreateAccountComponent)
  },

  {
    path: 'transaction',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/transaction/transaction.component')
        .then(m => m.TransactionComponent)
  },

  {
    path: 'balance',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/balance/balance.component')
        .then(m => m.BalanceComponent)
  },

  {
    path: 'transactions',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/transaction-history/transaction-history.component')
        .then(m => m.TransactionHistoryComponent)
  }
];
