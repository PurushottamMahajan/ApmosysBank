import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { AccountService } from '../../core/services/account.service';

@Component({
  standalone: true,
  selector: 'app-balance',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './balance.component.html',
  styleUrls: ['./balance.component.css']
})
export class BalanceComponent {

  balance: number | null = null;
  errorMessage = '';

  balanceForm;

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService
  ) {
    // ✅ FIX: initialize form inside constructor
    this.balanceForm = this.fb.group({
      accountNumber: ['', Validators.required]
    });
  }

  checkBalance() {
    this.balance = null;
    this.errorMessage = '';

    if (this.balanceForm.invalid) return;

    const accountNumber = this.balanceForm.value.accountNumber;

    this.accountService.getBalance(accountNumber!)
      .subscribe({
        next: (res) => {
          this.balance = res.balance;
        },
        error: (err) => {
          this.errorMessage = err.error?.message || 'Unable to fetch balance';
        }
      });
  }
}
