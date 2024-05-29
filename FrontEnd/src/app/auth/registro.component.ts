import { Component, OnInit } from '@angular/core';
import { AuthService } from '../Servicios/auth.service';
import { Router } from '@angular/router';
import { NuevoUsuario } from '../Modelo/nuevo-usuario';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CustomValidators } from './custom-validators';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit{
  isRegister = false;
  isRegisterfail = false;
  nuevoUsusario: NuevoUsuario;
  nombre: string;
  nombreUsuario: string;
  email: string;
  password: string;
  pregunta: string;
  respuesta: string;
 
 // roles: string[] = [];
  errMsj: string;

  frmSignup: FormGroup;

  constructor(
    private authService: AuthService,
    //Uso del ToastrService para evitar el texto claro en las vistas
    // private toast: ToastrService,
    private router: Router,
    private fb: FormBuilder
  ){
    this.frmSignup = this.validador();
   }
  //Comprueba si esta loggeado
  ngOnInit() {
  }

  onRegister(): void{
    //Se inicializa el login usuario
    this.nuevoUsusario = new NuevoUsuario(this.nombre,this.nombreUsuario, this.email, this.password, this.pregunta, this.respuesta);
    this.authService.nuevo(this.nuevoUsusario).subscribe(
      data => {
        this.isRegister = true; //Se quita con el toastr
        this.isRegisterfail = false; //Se quita con el toastr
        //Te redirecciona al login
        alert("Usuario registrado"),
        this.router.navigate(['/login']);
      },
      //en caso de error
      err => {
        this.isRegister = false; //Se quita con el toastr
        this.isRegisterfail = true; //Se quita con el toastr
        this.errMsj = err.error.mensaje;
        console.log(err.error.message);
      }
    );
  }

  validador(): FormGroup {
    return this.fb.group(
      {
        email: [
          null,
          Validators.compose([Validators.email, Validators.required])
        ],
        password: [
          null,
          Validators.compose([
            Validators.required,
            // check whether the entered password has a number
            CustomValidators.patternValidator(/\d/, {
              hasNumber: true
            }),
            // check whether the entered password has upper case letter
            CustomValidators.patternValidator(/[A-Z]/, {
              hasCapitalCase: true
            }),
            // check whether the entered password has a lower case letter
            CustomValidators.patternValidator(/[a-z]/, {
              hasSmallCase: true
            }),
            // check whether the entered password has a special character
            CustomValidators.patternValidator(
              /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/,
              {
                hasSpecialCharacters: true
              }
            ),
            Validators.minLength(8)
          ])
        ],
        confirmPassword: [null, Validators.compose([Validators.required])]
      },
      {
        // check whether our password and confirm password match
        validator: CustomValidators.passwordMatchValidator
      }
    );
  }

}
