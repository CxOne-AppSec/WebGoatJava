import { Component, OnInit } from "@angular/core";
import { Usuario } from "src/app/Modelo/user-admin";
import { ProductoService } from "src/app/Servicios/producto.service";
import { TokenService } from "src/app/Servicios/token.service";

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.css']
  })
  export class UsuarioComponent implements OnInit {
   usuario: Usuario[] = [];
   roles: string[]; 
   isAdmin = false;
  
   constructor(
    private productoS: ProductoService,
    private tokenService: TokenService){}
  
   ngOnInit(): void{
      this.cargarUsuario();
      this.isAdmin = this.tokenService.IsAdmin();
    }
  
   cargarUsuario(): void {
    this.productoS.users().subscribe(
      data => {
        this.usuario = data;
      }
    )
   }
  }