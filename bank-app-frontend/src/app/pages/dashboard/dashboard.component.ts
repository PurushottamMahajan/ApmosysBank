import {
  Component,
  ViewChild,
  ElementRef
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from '../../core/services/account.service';
import { TransactionService } from '../../core/services/transaction.service';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  standalone: true,
  selector: 'app-dashboard',
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  @ViewChild('txnChart', { static: true })
  chartRef!: ElementRef<HTMLCanvasElement>;

  accountNumber = '';
  balance: number | null = null;
  lastTransaction: any = null;
  errorMessage = '';

  showChart = false;
  chart: Chart | null = null;

  // 🔐 prevents stale responses
  private requestToken = 0;

  constructor(
    private accountService: AccountService,
    private transactionService: TransactionService,
    private router: Router
  ) {}

  fetchDashboardData(): void {
    if (!this.accountNumber.trim()) return;

    const accNo = this.accountNumber.trim();
    const token = ++this.requestToken;

    // reset UI
    this.balance = null;
    this.lastTransaction = null;
    this.errorMessage = '';
    this.showChart = false;
    this.destroyChart();

    // ===== BALANCE =====
    this.accountService.getBalance(accNo).subscribe({
      next: res => {
        if (this.requestToken !== token) return;
        this.balance = res.balance;
      },
      error: () => {
        if (this.requestToken !== token) return;
        this.errorMessage = 'Account not found';
      }
    });

    // ===== TRANSACTIONS =====
    this.transactionService.getAllTransactions(accNo).subscribe({
      next: txns => {
        if (this.requestToken !== token) return;

        if (!txns || txns.length === 0) {
          this.showChart = false;
          return;
        }

        this.lastTransaction = txns[txns.length - 1];
        this.renderChart(txns);
      },
      error: () => {
        if (this.requestToken !== token) return;
        this.errorMessage = 'Failed to load transactions';
      }
    });
  }

private renderChart(transactions: any[]): void {

  const creditTxns = transactions.filter(
    t => t.transactionType === 'CREDIT'
  );

  const debitTxns = transactions.filter(
    t => t.transactionType === 'DEBIT'
  );

  // ✅ ONLY case where graph must NOT appear
  if (creditTxns.length === 0 && debitTxns.length === 0) {
    this.showChart = false;
    this.destroyChart();
    return;
  }

  const creditTotal = creditTxns.reduce(
    (sum, t) => sum + Number(t.amount), 0
  );

  const debitTotal = debitTxns.reduce(
    (sum, t) => sum + Number(t.amount), 0
  );

  const canvas = this.chartRef?.nativeElement;
  if (!canvas) return;

  this.destroyChart();
  this.showChart = true;

  this.chart = new Chart(canvas, {
    type: 'bar',
    data: {
      labels: ['Credit', 'Debit'],
      datasets: [{
        label: 'Amount ₹',
        data: [creditTotal, debitTotal], // ✅ one may be 0 — THAT IS OK
        backgroundColor: ['#198754', '#dc3545']
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: { beginAtZero: true }
      },
      plugins: {
        legend: { display: false }
      }
    }
  });
}


  private destroyChart(): void {
    if (this.chart) {
      this.chart.destroy();
      this.chart = null;
    }
  }

  navigate(path: string): void {
    this.router.navigate([path]);
  }
}
