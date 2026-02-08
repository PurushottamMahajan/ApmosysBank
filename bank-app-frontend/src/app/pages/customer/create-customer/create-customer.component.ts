import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { CustomerService } from '../../../core/services/customer.service';

@Component({
  standalone: true,
  selector: 'app-create-customer',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-customer.component.html',
  styleUrls: ['./create-customer.component.css']
})
export class CreateCustomerComponent {

  customerForm!: FormGroup;

  successMessage = '';
  errorMessage = '';
  cifNumber = '';

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService
  ) {
    // ✅ SAFE INITIALIZATION
    this.customerForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      mobileNumber: ['', Validators.required],
      dob: ['', Validators.required],
      gender: ['', Validators.required],
      maritalStatus: ['', Validators.required],
      nationality: ['', Validators.required],
      aadharNumber: ['', Validators.required]
    });
  }

  submit() {
    console.log('FORM VALUE:', this.customerForm.value);
    this.successMessage = '';
    this.errorMessage = '';

    if (this.customerForm.invalid) return;

    this.customerService.createCustomer(this.customerForm.value)
      .subscribe({
        next: (res) => {
          this.cifNumber = res.cifNumber;
          this.successMessage = 'Customer Created Successfully';
          this.customerForm.reset();
        },
        error: (err) => {
          this.errorMessage = err.error?.message || 'Server Error';
        }
      });
  }
}



