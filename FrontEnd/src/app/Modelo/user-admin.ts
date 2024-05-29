export class Usuario {
    nombre: string;
    nombreUsuario: string;
    email: string;
    password: string;
    pregunta: string;
    respuesta: string;
    rol: string;
  
    
   constructor(nombre:string, nombreUsuario: string, email: string, password:string, pregunta:string, respuesta:string, rol:string){
     this.nombre = nombre;
     this.nombreUsuario = nombreUsuario;
     this.email= email;
     this.password = password;
     this.pregunta = pregunta;
     this.respuesta = respuesta;
     this.rol = rol;
   }
  }