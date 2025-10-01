import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { UserAuthService } from '../services/user-auth';

/**
 * HTTP interceptor that appends a Bearer token to outgoing requests
 */
export const authInterceptor = (request: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const _userAuthService = inject(UserAuthService);
  const HAS_TOKEN = _userAuthService.getUserToken();
  if(HAS_TOKEN) {
    const newReq = request.clone({
      headers: request.headers.append('Authorization', `Bearer ${HAS_TOKEN}`)
    });
    return next(newReq);
  }
  return next(request);
}
