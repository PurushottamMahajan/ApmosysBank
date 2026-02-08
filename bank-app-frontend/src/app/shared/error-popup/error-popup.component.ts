import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorService } from '../../core/services/error.service';
import { Observable } from 'rxjs';

@Component({
  standalone: true,
  selector: 'app-error-popup',
  imports: [CommonModule],
  templateUrl: './error-popup.component.html',
  styleUrls: ['./error-popup.component.css']
})
export class ErrorPopupComponent {

  error$!: Observable<string | null>;

  constructor(private errorService: ErrorService) {
    this.error$ = this.errorService.error$;
  }

  close() {
    this.errorService.clear();
  }
}
