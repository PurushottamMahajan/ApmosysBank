import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TransactionService } from '../../core/services/transaction.service';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-transaction-history',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.css']
})
export class TransactionHistoryComponent implements OnInit {

  historyForm!: FormGroup;   // ✅ only declaration
  transactions: any[] = [];
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private transactionService: TransactionService
  ) {}

  ngOnInit(): void {
    this.historyForm = this.fb.group({
      accountNumber: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required]
    });
  }

  fetchTransactions(): void {
    if (this.historyForm.invalid) return;

    const { accountNumber, fromDate, toDate } = this.historyForm.value;

    this.transactionService.getStatement(accountNumber, fromDate, toDate)
      .subscribe({
        next: data => this.transactions = data,
        error: err => this.errorMessage = err.error?.message || 'Failed to load'
      });
  }

  exportPDF(): void {
    const doc = new jsPDF();
    autoTable(doc, {
      head: [['Type', 'Amount', 'Balance', 'Date']],
      body: this.transactions.map(t => [
        t.transactionType,
        t.amount,
        t.balanceAfterTransaction,
        new Date(t.transactionDate).toLocaleString()
      ])
    });
    doc.save('transactions.pdf');
  }

  exportCSV(): void {
    const csv =
      'Type,Amount,Balance,Date\n' +
      this.transactions.map(t =>
        `${t.transactionType},${t.amount},${t.balanceAfterTransaction},${t.transactionDate}`
      ).join('\n');

    const blob = new Blob([csv], { type: 'text/csv' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'transactions.csv';
    link.click();
  }
}
