import { Injectable } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpInterceptor, HttpRequest,HttpHandler,HttpEvent,} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../Servicios/token.service';

@Injectable({
    providedIn: 'root'
  })
  export class AuthInterceptor implements HttpInterceptor {
  
    constructor(private tokenService: TokenService) { }
  
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      let intReq = req;
      const token = this.tokenService.getToken();
      if (token != null) {
        intReq = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + token)});
      }
      return next.handle(intReq);
    }
  }