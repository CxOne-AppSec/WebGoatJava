export class Producto{
    id?: number;
    precio: number;
    servicio: string;
    categoria: string;
    descripcion: string;
    foto: string;
    url: string;

    constructor(precio: number, servicio: string, categoria: string, descripcion: string, foto: string, url: string){
        this.precio = precio;
        this.servicio = servicio;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.foto = foto;
        this.url = url;
    }
    
}