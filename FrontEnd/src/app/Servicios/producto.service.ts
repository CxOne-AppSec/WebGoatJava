import { HttpClient, HttpHeaders, HttpParamsOptions } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { enviroments } from 'src/environments/environments';
import { Producto } from '../Modelo/producto';
import { TokenService } from './token.service';
import { Usuario } from '../Modelo/user-admin';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  URL = enviroments.URL;
  constructor(private http: HttpClient, private tokenService: TokenService) {}
  
  public list(): Observable<Producto[]> {
    
    return this.http.get<Producto[]>(this.URL + 'lista'); //, {headers}
  }
  public users(): Observable<Usuario[]> {
    
    return this.http.get<Usuario[]>(this.URL + 'users'); //, {headers}
  }
  public detail(id: number): Observable<Producto> {
    return this.http.get<Producto>(this.URL + `detail/${id}`);
  }
  public save(producto: Producto): Observable<any> {

    const formData: FormData = new FormData();
    // Crea un objeto Blob a partir del archivo producto.foto
    let blobFile = new Blob([producto.foto]);
    // Adjunta el objeto Blob al FormData

    formData.append('categoria', producto.categoria);
    formData.append('descripcion', producto.descripcion);
    formData.append('file', blobFile, this.obtenerNombreArchivo(producto.foto));
    formData.append('servicio', producto.servicio);
    formData.append('url', "http://localhost:80");
    formData.append('precio', producto.precio.toString());

    return this.http.post<any>(this.URL + 'create ', formData);
  }
  public obtenerNombreArchivo(ruta: string): string {
    const ultimaBarraDiagonal = Math.max(ruta.lastIndexOf('\\'), ruta.lastIndexOf('/'));
    if (ultimaBarraDiagonal !== -1) {
      return ruta.substr(ultimaBarraDiagonal + 1);
    } else {
      return ruta;
    }
  }
  // public update(id: number, producto: Producto): Observable<any> {
  //     return this.http.put<any>(this.URL + `update/${id}`, producto);
  // }

  public update(id: number, producto: Producto, foto: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', foto);
    formData.append('precio', producto.precio.toString());
    formData.append('servicio', producto.servicio);
    formData.append('categoria', producto.categoria);
    formData.append('descripcion', producto.descripcion);
    formData.append('foto', producto.foto);

    return this.http.put<any>(this.URL + `update/${id}`, formData);
  }


  public delete(id: number): Observable<any> {
    return this.http.delete<any>(this.URL + `delete/${id}`);
  }
}