export class NuevoUsuario {
  nombre: string;
  nombreUsuario: string;
  email: string;
  password: string;
  pregunta: string;
  respuesta: string;

  
 constructor(nombre:string, nombreUsuario: string, email: string, password:string, pregunta:string, respuesta:string){
   this.nombre = nombre;
   this.nombreUsuario = nombreUsuario;
   this.email= email;
   this.password = password;
   this.pregunta = pregunta;
   this.respuesta = respuesta;
 }
}
