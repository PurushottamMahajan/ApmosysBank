import { Component, ChangeDetectorRef } from '@angular/core';
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

  transactionForm: FormGroup;
  successMessage = '';
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private transactionService: TransactionService,
    private cdr: ChangeDetectorRef
  ) {
    this.transactionForm = this.fb.group({
      accountNumber: ['', Validators.required],
      transactionType: ['CREDIT', Validators.required],
      amount: ['', [Validators.required, Validators.min(1)]]
    });
  }

  submit(): void {
    this.clearMessages();

    if (this.transactionForm.invalid) return;

    this.transactionService.performTransaction(this.transactionForm.value)
      .subscribe({
        next: () => {
          this.successMessage = 'Transaction completed successfully';
          this.transactionForm.reset({ transactionType: 'CREDIT' });

          this.autoHideMessages();
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = err.error?.message || 'Transaction failed';

          this.autoHideMessages();
          this.cdr.detectChanges();
        }
      });
  }

  // 🔹 Clear messages instantly
  private clearMessages(): void {
    this.successMessage = '';
    this.errorMessage = '';
  }

  // 🔹 Auto-hide after 4 seconds
  private autoHideMessages(): void {
    setTimeout(() => {
      this.clearMessages();
      this.cdr.detectChanges();
    }, 4000); // ⏱ 4 seconds
  }
}
