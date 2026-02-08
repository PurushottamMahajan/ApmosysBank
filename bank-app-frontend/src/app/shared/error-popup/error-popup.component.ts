import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorService } from '../../core/services/error.service';

@Component({
  selector: 'app-error-popup',
  standalone: true,                // ✅ REQUIRED
  imports: [CommonModule],          // ✅ REQUIRED
  templateUrl: './error-popup.component.html',
  styleUrls: ['./error-popup.component.css']
})
export class ErrorPopupComponent {

  constructor(public errorService: ErrorService) {}
}
