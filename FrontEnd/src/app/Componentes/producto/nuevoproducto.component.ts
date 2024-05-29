import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Producto } from 'src/app/Modelo/producto';
import { ProductoService } from 'src/app/Servicios/producto.service';

@Component({
  selector: 'app-nuevoproducto',
  templateUrl: './nuevoproducto.component.html',
  styleUrls: ['./nuevoproducto.component.css']
})
export class NuevoproductoComponent implements OnInit{
   servicio: string;
   categoria: string;
   precio: number;
   descripcion: string;
   foto: string;
   url: string;

   constructor(
    private productoS: ProductoService, 
    private router: Router){ }
  ngOnInit(): void {
  }

onCreate(): void{
  const producto = new Producto( this.precio, this.servicio, this.categoria, this.descripcion, this.foto, this.url);
  this.productoS.save(producto).subscribe(
    data => {
      alert("Producto añadido");
      console.log(data);
      this.router.navigate(['/lista']);
    }, err => {
       alert("El producto ya existe");
       console.error(err);
    }
  )
}
  }
