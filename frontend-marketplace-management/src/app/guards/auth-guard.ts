import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { UserService } from '../services/user';
import { UserAuthService } from '../services/user-auth';
import { firstValueFrom } from 'rxjs';

export const authGuard: CanActivateFn = async (route, state) => {
  const _userService = inject(UserService);
  const _userAthService = inject(UserAuthService);
  const _router = inject(Router);
  // verify if has token on the local storage
  const HAS_TOKEN = _userAthService.getUserToken();
  if(!HAS_TOKEN) {
    return  _router.navigate(['/login']);
  }
  try {
    // try token validate from backend
    await firstValueFrom(_userService.validateUser());
    // if user's token is valid and the route isn't /login then allow
    return true;
  } catch (error) {
    // if the request fails, then redirect to /login
    return _router.navigate(['/login']);
  }
};
