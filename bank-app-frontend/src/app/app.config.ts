import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { routes } from './app.routes';

export const appConfig = {
  providers: [
    provideHttpClient(),
    importProvidersFrom(FormsModule),
    provideRouter(routes)
  ]
};
