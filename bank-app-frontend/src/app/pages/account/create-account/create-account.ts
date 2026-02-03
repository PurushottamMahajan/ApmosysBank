import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-create-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-account.html',
  styleUrls: ['./create-account.css']
})
export class CreateAccountComponent {

  account = {
    customerId: '',
    accountType: '',
    initialBalance: 0
  };

  createAccount() {
    console.log('Account data:', this.account);
    alert('Account created (API integration next)');
  }
}
