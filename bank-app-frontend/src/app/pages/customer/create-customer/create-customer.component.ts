import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../../core/services/customer.service';
import { Customer } from '../../../core/models/customer.model';

@Component({
  selector: 'app-create-customer',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './create-customer.component.html',
  styleUrls: ['./create-customer.component.css']
})
export class CreateCustomerComponent {

  customer: Customer = {
    firstName: '',
    lastName: '',
    dob: '',
    gender: '',
    maritalStatus: '',
    nationality: '',
    email: '',
    mobileNumber: '',
    aadharNumber: ''
  };

  constructor(private customerService: CustomerService) {}

  submit() {
    this.customerService.createCustomer(this.customer).subscribe({
      next: () => alert('Customer created successfully'),
      error: () => alert('Error creating customer')
    });
  }
}
