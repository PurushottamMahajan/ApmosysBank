import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { ErrorService } from '../services/error.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const errorService = inject(ErrorService);

  return next(req).pipe(
    catchError(error => {
      const message =
        error?.error?.message ||
        error?.error?.error ||
        'Unexpected server error';

      errorService.show(message);
      return throwError(() => error);
    })
  );
};
