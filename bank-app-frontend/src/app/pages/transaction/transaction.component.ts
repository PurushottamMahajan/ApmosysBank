import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { TransactionService } from '../../core/services/transaction.service';

@Component({
  standalone: true,
  selector: 'app-transaction',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent {

  transactionForm!: FormGroup;
  successMessage = '';
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private transactionService: TransactionService
  ) {
    this.transactionForm = this.fb.group({
      accountNumber: ['', Validators.required],
      transactionType: ['CREDIT', Validators.required],
      amount: ['', [Validators.required, Validators.min(1)]]
    });
  }

  submit() {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.transactionForm.invalid) return;

    this.transactionService.performTransaction(this.transactionForm.value)
      .subscribe({
        next: () => {
          this.successMessage = 'Transaction Successful';
          this.transactionForm.reset({ transactionType: 'CREDIT' });
        },
        error: (err) => {
          this.errorMessage = err.error?.message || 'Transaction Failed';
        }
      });
  }
}
