export interface Transaction {
  transactionId: number;
  transactionType: 'DEBIT' | 'CREDIT';
  amount: number;
  balanceAfterTransaction: number;
  transactionDate: string;
}
