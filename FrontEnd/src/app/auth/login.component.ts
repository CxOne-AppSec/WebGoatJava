import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginUsuario } from '../Modelo/login-usuario';
import { AuthService } from '../Servicios/auth.service';
import { TokenService } from '../Servicios/token.service';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginUsusario: LoginUsuario;
  nombreUsuario: string;
  password: string;
  errMsj: string;
  isLogged = false;
  isLoggedfail = false;

  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router
    
  ){ }
  //comprobar si esta loggeado
  ngOnInit() {
  }
  onLogin(): void{
    //Se inicializa el login usuario
    this.loginUsusario = new LoginUsuario(this.nombreUsuario, this.password);
    this.authService.login(this.loginUsusario).subscribe(
      data => {
       //alert("Login exitoso")
       this.tokenService.setToken(data.token);
       this.router.navigate(['/lista']);
       
      },
      //en caso de error
      (error: any) => {
        if (error instanceof HttpErrorResponse) {
          if (error.status === 400) {
            // Error de solicitud incorrecta, mostrar mensaje de error
            alert("Error: Campos incorrectos");
            console.log(error.error.mensaje);
          } else {
            // Otro tipo de error, mostrar mensaje de error
            alert("Ocurrio un error");
            console.log(error.error.mensaje);
          }
        }
      }
    );
  }

}
