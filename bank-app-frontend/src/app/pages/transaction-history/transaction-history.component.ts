import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
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

  historyForm!: FormGroup;
  transactions: any[] = [];
  errorMessage = '';
  loading = false;

  constructor(
    private fb: FormBuilder,
    private transactionService: TransactionService,
    private cdr: ChangeDetectorRef   // ✅ IMPORTANT
  ) {}

  ngOnInit(): void {
    this.historyForm = this.fb.group({
      accountNumber: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required]
    });
  }

  // ==========================
  // FETCH STATEMENT
  // ==========================
  fetchTransactions(): void {
    this.errorMessage = '';
    this.transactions = [];
    this.loading = true;

    if (this.historyForm.invalid) {
      this.loading = false;
      return;
    }

    const { accountNumber, fromDate, toDate } = this.historyForm.value;

    this.transactionService
      .getStatement(accountNumber, fromDate, toDate)
      .subscribe({
        next: (data) => {
          this.transactions = data;
          this.loading = false;

          // 🔥 FORCE UI UPDATE
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage =
            err.error?.message || 'Failed to load transaction statement';
          this.loading = false;

          // 🔥 FORCE UI UPDATE
          this.cdr.detectChanges();
        }
      });
  }

  // ==========================
  // EXPORT PDF
  // ==========================
  exportPDF(): void {
  if (!this.transactions.length) return;

  const { accountNumber, fromDate, toDate } = this.historyForm.value;

  const today = this.formatDate(new Date());
  const fileName = `Statement_${accountNumber}_${today}.pdf`;

  const doc = new jsPDF();

  // ================= HEADER =================
  doc.setFontSize(14);
  doc.text('BANK TRANSACTION STATEMENT', 14, 15);

  doc.setFontSize(10);
  doc.text(`Account Number : ${accountNumber}`, 14, 25);
  doc.text(`Statement From : ${fromDate}`, 14, 32);
  doc.text(`Statement To     : ${toDate}`, 14, 39);
  doc.text(`Downloaded On : ${today}`, 14, 46);

  // ================= TABLE =================
  autoTable(doc, {
    startY: 55,
    head: [['Type', 'Amount', 'Balance', 'Date']],
    body: this.transactions.map(t => [
      t.transactionType,
      `₹ ${t.amount}`,
      `₹ ${t.balanceAfterTransaction}`,
      new Date(t.transactionDate).toLocaleString()
    ]),
    styles: {
      fontSize: 9
    },
    headStyles: {
      fillColor: [22, 160, 133]
    }
  });

  doc.save(fileName);
}


  // ==========================
  // EXPORT CSV
  // ==========================
  exportCSV(): void {
    if (!this.transactions.length) return;

    const headers = ['Type', 'Amount', 'Balance', 'Date'];
    const rows = this.transactions.map(t =>
      [
        t.transactionType,
        t.amount,
        t.balanceAfterTransaction,
        new Date(t.transactionDate).toLocaleString()
      ].join(',')
    );

    const csvContent =
      headers.join(',') + '\n' + rows.join('\n');

    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const url = window.URL.createObjectURL(blob);

    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'transaction-statement.csv');
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }
  private formatDate(date: Date): string {
  return date.toISOString().split('T')[0];
}

}
