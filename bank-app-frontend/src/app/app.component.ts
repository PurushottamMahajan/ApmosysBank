import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/header/header.component';
import { ErrorPopupComponent } from './shared/error-popup/error-popup.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    HeaderComponent,
    ErrorPopupComponent   // ✅ THIS FIXES THE ERROR
  ],
  templateUrl: './app.component.html'
})
export class AppComponent {}
