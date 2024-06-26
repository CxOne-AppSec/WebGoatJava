import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

const TOKEN_KEY = 'AuthToken';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  roles: Array<string> = [];

  constructor(
    private router: Router
  ) { }

  public setToken(token: string): void{
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string{
    return localStorage.getItem(TOKEN_KEY);
  }

  isLogged():boolean{
    if(this.getToken()){
      return true;
    }
    return false;
  }
  public getUserName(): string{
    //Verificar se esta logeado 
    if(!this.isLogged()){
      return null;
    }
    const token = this.getToken();
    const payload = token.split('.')[1];
    const payloadDecoded = atob(payload);
    const values = JSON.parse(payloadDecoded);
    const username = values.sub;
    return username;
  }
  public IsAdmin(): boolean{
    //Verificar se esta logeado 
    if(!this.isLogged()){
      return null;
    }
    const token = this.getToken();
    const payload = token.split('.')[1];
    const payloadDecoded = atob(payload);
    const values = JSON.parse(payloadDecoded);
    const roles = values.rol;
   // console.log("viendo index del rol: " + roles.indexOf('R'));
    if(roles.indexOf('ROL_ADMIN') < 0){
      return false;
    }
    return true;
  }
  public logOut(): void{
    window.localStorage.clear();
    this.router.navigate(['/login'])
  }
}