import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthpService } from '../authp.service';

export const authpGuard: CanActivateFn = (route, state) => {
  const authpService = inject(AuthpService);
  const router = inject(Router);

  if (authpService.getAuthStatus()) {
    return true;
  } else {
    return router.parseUrl('/login');
  }
};
