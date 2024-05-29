import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/Modelo/producto';
import { ProductoService } from 'src/app/Servicios/producto.service';
import { TokenService } from 'src/app/Servicios/token.service';

@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.css']
})
export class ProductoComponent implements OnInit {
 producto: Producto[] = [];
 roles: string[]; 
 isAdmin = false;

 constructor(
  private productoS: ProductoService,
  private tokenService: TokenService){}

 ngOnInit(): void{
    this.cargarProducto();
    this.isAdmin = this.tokenService.IsAdmin();
  }

 cargarProducto(): void {
  this.productoS.list().subscribe(
    data => {
      this.producto = data;
    }
  )
 }

 delete(id?: number): void{
  if(id != undefined){
    this.productoS.delete(id).subscribe(
      data => {
        this.cargarProducto();
      }, err => {
        alert("No se puede eliminar el producto");
      }
    )
  }
 }
}
