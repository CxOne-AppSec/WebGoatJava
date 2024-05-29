import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginUsuario } from '../Modelo/login-usuario';
import { NuevoUsuario } from '../Modelo/nuevo-usuario';
import { enviroments } from 'src/environments/environments';
import { RenovarPass } from '../Modelo/renovar-pass';
import { JwtDTOUsuario } from '../Modelo/jwt-dto-usuario';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authURL = enviroments.authURL;

  constructor(private httpClient: HttpClient) { }

  public nuevo(nuevoUsuario: NuevoUsuario): Observable<any> {
    return this.httpClient.post<any>(this.authURL + 'nuevo', nuevoUsuario);
  }
  public login(loginUsuario: LoginUsuario): Observable<JwtDTOUsuario> {
    return this.httpClient.post<JwtDTOUsuario>(this.authURL + 'login', loginUsuario);
  }
  public renovar(renovarPass: RenovarPass): Observable<any> {
   return this.httpClient.post<any>(this.authURL + 'renovar', renovarPass);
  }
  public nuevapass(password: string, nuevaPassword: string): Observable<any>{
    let data = {
      password: password,
      nuevaPassword: nuevaPassword
    };  
    return this.httpClient.post<any>(this.authURL + 'cambiarpass', data);
  }
  public perfil(): Observable<any>{
    return this.httpClient.get<any>(this.authURL + 'perfil');
  }
}

