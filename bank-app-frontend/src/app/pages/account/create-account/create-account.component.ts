import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { AccountService } from '../../../core/services/account.service';

@Component({
  standalone: true,
  selector: 'app-create-account',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent {

  accountForm!: FormGroup;
  successMessage = '';
  errorMessage = '';
  accountNumber = '';

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService
  ) {
    this.accountForm = this.fb.group({
      cifNumber: ['', Validators.required],
      accountType: ['SAVINGS', Validators.required]
    });
  }

  submit() {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.accountForm.invalid) return;

    this.accountService.createAccount(this.accountForm.value)
      .subscribe({
        next: (res) => {
          this.accountNumber = res.accountNumber;
          this.successMessage = 'Account Created Successfully';
          this.accountForm.reset({ accountType: 'SAVINGS' });
        },
        error: (err) => {
          this.errorMessage = err.error?.message || 'Invalid CIF Number';
        }
      });
  }
}
