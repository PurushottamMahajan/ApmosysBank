import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AccountService } from '../../core/services/account.service';

@Component({
  standalone: true,
  selector: 'app-balance',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './balance.component.html',
  styleUrls: ['./balance.component.css']
})
export class BalanceComponent {

  balanceForm: FormGroup;
  balance: number | null = null;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private cdr: ChangeDetectorRef   // ✅ IMPORTANT
  ) {
    this.balanceForm = this.fb.group({
      accountNumber: ['', Validators.required]
    });
  }

  checkBalance(): void {
    this.balance = null;
    this.errorMessage = '';

    if (this.balanceForm.invalid) return;

    const { accountNumber } = this.balanceForm.value;

    this.accountService.getBalance(accountNumber)
      .subscribe({
        next: (res) => {
          this.balance = res.balance;

          // 🔥 FORCE UI UPDATE
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage =
            err.error?.message || 'Unable to fetch balance';

          // 🔥 FORCE UI UPDATE
          this.cdr.detectChanges();
        }
      });
  }
}
