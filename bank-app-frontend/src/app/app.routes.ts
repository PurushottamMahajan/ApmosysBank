import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/customer/create-customer/create-customer.component')
        .then(m => m.CreateCustomerComponent)
  }
];
