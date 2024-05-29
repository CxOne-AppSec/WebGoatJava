import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Producto } from 'src/app/Modelo/producto';
import { ProductoService } from 'src/app/Servicios/producto.service';

@Component({
  selector: 'app-editarproducto',
  templateUrl: './editarproducto.component.html',
  styleUrls: ['./editarproducto.component.css']
})
export class EditarproductoComponent implements OnInit {
  producto: Producto = null;
  errMsj: string;
  @ViewChild('fileInput') fileInput!: ElementRef;

  constructor(private productoS: ProductoService, private router: Router, private activatedRouter: ActivatedRoute) { 
    this.producto = {} as Producto; 
  }

  ngOnInit(): void {
    const id = this.activatedRouter.snapshot.params['id'];
    this.productoS.detail(id).subscribe(
      data => {
        this.producto = data;
      }, err => {
        alert("Error al modificar la producto");
        this.router.navigate(['/servicios']);
      }
    )
  }

  async onUpdate(): Promise<void> {
    const id = this.activatedRouter.snapshot.params['id'];

    // Obtenemos la imagen seleccionada (si existe)
    const inputElement = this.fileInput.nativeElement;
    let file: File | null = null;
    if (inputElement.files && inputElement.files.length) {
      file = inputElement.files[0];
    }
    if (this.producto.precio <= 100) { alert('El precio debe ser mayor a $100.'); }
    else {
      // Modificamos los datos del producto (incluyendo la imagen)
      this.productoS.update(id, this.producto, file).subscribe(
        data => {
          alert("Modificación correcta");
          this.router.navigate(['/lista']);
        },
        err => {
          alert("No se logró modificar el producto, por favor cargue nuevamente la imagen");
        }
      );
    }

  }

  onFileSelected() {
    const inputElement = this.fileInput.nativeElement;
    if (inputElement.files && inputElement.files.length) {
      const file = inputElement.files[0];

      // Aquí puedes manejar el archivo seleccionado (puedes guardarlo en una propiedad del componente si lo necesitas)
    }
  }
}
