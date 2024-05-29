import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Producto } from 'src/app/Modelo/producto';
import { ProductoService } from 'src/app/Servicios/producto.service';

@Component({
  selector: 'app-detallesproducto',
  templateUrl: './detallesproducto.component.html',
  styleUrls: ['./detallesproducto.component.css']
})
export class DetallesproductoComponent implements OnInit {
  producto: Producto = null;

  constructor(private productoS: ProductoService,private router: Router, private activatedRouter: ActivatedRoute){
    this.producto = {} as Producto; 
   }

  ngOnInit(): void {
    const id= this.activatedRouter.snapshot.params['id'];
    this.productoS.detail(id).subscribe(
      data => {
        this.producto = data;
      }, err => {
        alert("Error visualizar la producto");
        this.router.navigate(['/']);
      }
    )
  }
}
