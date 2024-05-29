import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { NuevoUsuario } from "src/app/Modelo/nuevo-usuario";
import { Usuario } from "src/app/Modelo/user-admin";
import { AuthService } from "src/app/Servicios/auth.service";
import { CustomValidators } from "src/app/auth/custom-validators";

@Component({
  selector: 'app-nuevouser',
  templateUrl: './nuevouser.component.html',
  styleUrls: ['./nuevouser.component.css']
})
export class NuevouserComponent implements OnInit {
  isRegister = false;
  isRegisterfail = false;
  nuevoUsusario: NuevoUsuario;
  nombre: string;
  nombreUsuario: string;
  email: string;
  password: string;
  pregunta: string = "";
  respuesta: string = "";
  rol: string;
  confirmacion: string;
  frmSignup: FormGroup;
  errMsj: string;

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.frmSignup = this.validador();
  }
  //Comprueba si esta loggeado
  ngOnInit() {
  }

  onRegister(): void {

    const confirmacion = this.confirmacion;
    //Se inicializa el login usuario
    this.nuevoUsusario = new Usuario(this.nombre, this.nombreUsuario, this.email, this.password, this.pregunta, this.respuesta, "ROL_ADMIN");
   
    if (this.password === confirmacion) {
      this.authService.nuevo(this.nuevoUsusario).subscribe(
        data => {
          this.isRegister = true; //Se quita con el toastr
          this.isRegisterfail = false; //Se quita con el toastr
          alert("Usuario creado correctamente");
          this.router.navigate(['/usuarios']);
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
    
  